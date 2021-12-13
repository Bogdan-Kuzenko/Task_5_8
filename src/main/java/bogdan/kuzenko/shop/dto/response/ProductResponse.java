package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductResponse {
    private Long id;
    private Long price;
    private Long balance;
    private String name;
    private String description;
    private Boolean availableProduct;
    private List<String> images;
    private String mainImage;
    @JsonProperty("subcategory")
    private SubcategoryResponse subcategoryResponse;
    @JsonProperty("maker")
    private MakerResponse makerResponse;

    public ProductResponse(Product product) {
        id = product.getId();
        price = product.getPrice();
        name = product.getName();
        description = product.getDescription();
        mainImage = product.getMainImage();
        availableProduct = product.getAvailableProduct();
        images = product.getImages();
        balance = product.getBalance();
        subcategoryResponse = new SubcategoryResponse(product.getSubcategory());
        makerResponse = new MakerResponse(product.getMaker());
    }
}
