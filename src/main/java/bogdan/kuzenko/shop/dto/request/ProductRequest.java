package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProductRequest {

    @NotNull
    private String name;
    @NotNull
    private Long price;
    private Long balance;
    private String description;
    private Boolean availableProduct;
    private List<String> images = new ArrayList<>();
    private String mainImage;
    @NotNull
    private Long subcategoryId;
    private Long makerId;
}
