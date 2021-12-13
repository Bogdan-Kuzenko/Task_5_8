package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class OrderSearchRequest {
    private String value;
    private Long minSum;
    private Long userId;
    private Long maxSum;
    private Date dateFrom;
    private Date dateTo;

    private PaginationRequest pagination;
}
