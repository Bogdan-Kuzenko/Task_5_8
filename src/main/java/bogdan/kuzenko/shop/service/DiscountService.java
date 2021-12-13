package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.DiscountRequest;
import bogdan.kuzenko.shop.dto.response.DiscountResponse;
import bogdan.kuzenko.shop.entity.Discount;
import bogdan.kuzenko.shop.entity.User;
import bogdan.kuzenko.shop.repository.DiscountRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private UserService userService;

    public void create(@RequestBody DiscountRequest request) {
        discountRepository.save(discountRequestToDiscount(null, request));
    }

    public List<DiscountResponse> findAll() {
        return discountRepository.findAll().stream().map(DiscountResponse::new).collect(Collectors.toList());
    }

    public Discount findOne(Long id) {
        return discountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Discount with id " + id + " not exist"));
    }

    public void update(Long id, DiscountRequest request) {
        discountRepository.save(discountRequestToDiscount(findOne(id), request));
    }

    public void delete(Long id) {
        discountRepository.delete(findOne(id));
    }


    private Discount discountRequestToDiscount(Discount discount, DiscountRequest request) {
        if (discount == null) {
            discount = new Discount();
        }
        discount.setValue(request.getValue());
        return discount;

    }
}
