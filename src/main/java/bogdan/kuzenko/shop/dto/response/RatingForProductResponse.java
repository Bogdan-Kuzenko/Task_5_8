package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.RatingForProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RatingForProductResponse {

    private Long id;
    private Double rating;

    @JsonProperty ("product")
    private ProductResponse productResponse;

    public RatingForProductResponse(RatingForProduct ratingForProduct) {
        id = ratingForProduct.getId();
        rating = ratingForProduct.getRating();
        productResponse = new ProductResponse(ratingForProduct.getProduct());
    }
}
