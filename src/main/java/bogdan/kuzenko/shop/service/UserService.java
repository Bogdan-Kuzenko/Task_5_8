package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.UserRequest;
import bogdan.kuzenko.shop.dto.response.UserResponse;
import bogdan.kuzenko.shop.entity.User;
import bogdan.kuzenko.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscountService discountService;

    public User create(UserRequest request) {
       return userRepository.save(userRequestToUser(null, request));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not exist"));
    }

    public void update(Long id, UserRequest request) {
        userRepository.save(userRequestToUser(findOne(id), request));
    }

    public void delete(Long id) {
        userRepository.delete(findOne(id));
    }

    public void setDiscount(Long discountId, Long userId){
        User one = findOne(userId);
        one.getDiscounts().add(discountService.findOne(discountId));
        userRepository.save(one);
    }

    private User userRequestToUser(User user, UserRequest request) {
        if (user == null) {
            user = new User();
        }
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setBirthday(request.getBirthday());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        return user;
    }
}
