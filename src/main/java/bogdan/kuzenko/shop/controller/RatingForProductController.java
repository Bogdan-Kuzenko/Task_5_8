package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.RatingForProductRequest;
import bogdan.kuzenko.shop.dto.response.RatingForProductResponse;
import bogdan.kuzenko.shop.service.RatingForProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.RATING_URL;

@CrossOrigin
@RestController
@RequestMapping(RATING_URL)
public class RatingForProductController {

    @Autowired
    private RatingForProductService ratingForProductService;

    @PostMapping
    public void create(RatingForProductRequest request) {
        ratingForProductService.create(request);
    }

    @GetMapping
    public List<RatingForProductResponse> findAll() {
        return ratingForProductService.findAll();
    }

    @GetMapping("/byUser")
    public List<RatingForProductResponse> findAllByUserId(Long userId) {
        return ratingForProductService.findAllByUserId(userId);
    }

    @GetMapping("/byProduct")
    public Double findAverageRatingProductId(Long productId) {
        return ratingForProductService.averageRatingByProduct(productId);
    }


    @DeleteMapping
    public void delete(Long id) {
        ratingForProductService.delete(id);
    }

}
