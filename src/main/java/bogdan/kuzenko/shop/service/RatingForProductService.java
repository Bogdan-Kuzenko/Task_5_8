package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.RatingForProductRequest;
import bogdan.kuzenko.shop.dto.response.RatingForProductResponse;
import bogdan.kuzenko.shop.entity.RatingForProduct;
import bogdan.kuzenko.shop.repository.RatingForProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static bogdan.kuzenko.shop.tool.Constants.DEFAULT_RATING;

@Service
public class RatingForProductService {

    @Autowired
    private RatingForProductRepository ratingForProductRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public RatingForProduct create(RatingForProductRequest request) {
        return ratingForProductRepository.save(ratingForProductRequestToRatingForProduct(null, request));
    }

    public List<RatingForProductResponse> findAll() {
        return ratingForProductRepository.findAll().stream().map(RatingForProductResponse::new).collect(Collectors.toList());
    }

    public RatingForProduct findOne(Long id) {
        return ratingForProductRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subcategory with id " + id + " not exist"));
    }

    public void delete(Long id) {
        ratingForProductRepository.delete(findOne(id));
    }

    public List<RatingForProductResponse> findAllByUserId(Long userId) {
        return ratingForProductRepository.findAllByUserId(userId).stream().map(RatingForProductResponse::new).collect(Collectors.toList());
    }

    private List<RatingForProductResponse> findAllByProductId(Long productId) {
        return ratingForProductRepository.findAllByProductId(productId).stream().map(RatingForProductResponse::new).collect(Collectors.toList());
    }

    public Double averageRatingByProduct(Long productId) {
        return ratingForProductRepository.findAllByProductId(productId).stream().mapToDouble(RatingForProduct::getRating).average().orElse(DEFAULT_RATING);
    }

    private RatingForProduct ratingForProductRequestToRatingForProduct(RatingForProduct ratingForProduct, RatingForProductRequest request) {
        if (ratingForProduct == null) {
            ratingForProduct = new RatingForProduct();
        }
        ratingForProduct.setRating(request.getRating());
        ratingForProduct.setProduct(productService.findOne(request.getProductId()));
        ratingForProduct.setUser(userService.findOne(request.getUserId()));
        return ratingForProduct;
    }
}
