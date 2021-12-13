package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.DiscountRequest;
import bogdan.kuzenko.shop.dto.response.DiscountResponse;
import bogdan.kuzenko.shop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.DISCOUNT_URL;

@CrossOrigin
@RestController
@RequestMapping(DISCOUNT_URL)
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping
    public void create(DiscountRequest request) {
        discountService.create(request);
    }

    @GetMapping
    public List<DiscountResponse> findAll() {
        return discountService.findAll();
    }

    @PutMapping
    public void update(Long id, DiscountRequest request) {
        discountService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        discountService.delete(id);
    }
}
