package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private String image;
    private Boolean hideCategory;


    public CategoryResponse(Category category) {
        id = category.getId();
        name = category.getName();
        image = category.getImage();
        hideCategory = category.getHideCategory();
    }
}
