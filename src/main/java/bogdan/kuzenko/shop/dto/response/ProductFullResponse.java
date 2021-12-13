package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class ProductFullResponse {
    private Long id;
    private Long price;
    private Long balance;
    private String name;
    private String description;
    private Boolean availableProduct;
    private String mainImage;
    private List<String> images;
    @JsonProperty("subcategory")
    private SubcategoryResponse subcategoryResponse;
    @JsonProperty("maker")
    private MakerResponse makerResponse;
    @JsonProperty("comments")
    private List<CommentResponse> commentResponses;

    public ProductFullResponse(Product product) {
        id = product.getId();
        price = product.getPrice();
        balance = product.getBalance();
        name = product.getName();
        description = product.getDescription();
        availableProduct = product.getBalance() > 0;
        mainImage = product.getMainImage();
        images = product.getImages();
        subcategoryResponse = new SubcategoryResponse(product.getSubcategory());
        makerResponse = new MakerResponse(product.getMaker());
        commentResponses = product.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
