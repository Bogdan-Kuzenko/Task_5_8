package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.ProductCount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCountResponse {
    private Long id;
    private Long count;
    @JsonProperty("product")
    private ProductResponse productResponse;

    public ProductCountResponse(ProductCount productCount) {
        id = productCount.getId();
        count = productCount.getCount();
        productResponse = new ProductResponse(productCount.getProduct());
    }
}

