package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.UserRequest;
import bogdan.kuzenko.shop.dto.response.UserResponse;
import bogdan.kuzenko.shop.entity.User;
import bogdan.kuzenko.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.USER_URL;

@CrossOrigin
@RestController
@RequestMapping(USER_URL)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void create(@Valid @RequestBody UserRequest request) {
        userService.create(request);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PutMapping
    public void update(Long id,@Valid @RequestBody UserRequest request) {
        userService.update(id, request);
    }

    @PutMapping ("setDiscount")
    public void setDiscount(Long discountId, Long userId){
        userService.setDiscount(discountId,userId);
    }
    @DeleteMapping
    public void delete(Long id) {
        userService.delete(id);
    }

}
