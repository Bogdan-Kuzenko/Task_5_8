package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Subcategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubcategoryResponse {

    private Long id;
    private String name;
    private String image;
    private Boolean hideSubcategory;
    @JsonProperty("category")
    private CategoryResponse categoryResponse;

    public SubcategoryResponse(Subcategory subcategory) {
        id = subcategory.getId();
        name = subcategory.getName();
        image = subcategory.getImage();
        hideSubcategory = subcategory.getHideSubcategory();
        categoryResponse = new CategoryResponse(subcategory.getCategory());

    }
}
