package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductSearchRequest {

    private String value;
    private Integer minPrice;
    private Integer maxPrice;
    private Long makerId;
    private Long categoryId;
    private Long subcategoryId;

    private PaginationRequest pagination;

}
