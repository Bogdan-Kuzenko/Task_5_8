package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter

public class CategoryRequest {
    @NotNull
    private String name;
    private String image;
    private Boolean hideCategory;
}
