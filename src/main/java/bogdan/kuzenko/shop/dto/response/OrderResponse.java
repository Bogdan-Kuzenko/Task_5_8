package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class OrderResponse {

    private Long id;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOrder;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDelivery;

    private Long sum;
    private Long discountSum;

    @JsonProperty("products")
    private List<ProductCountResponse> productCuntResponses;
    private Long userId;
    private Long deliveryId;

    public OrderResponse(Order order) {
        id = order.getId();
        comment = order.getComment();
        dateOrder = order.getDateOrder();
        dateDelivery = order.getDateDone();
        sum = order.getSum();
        discountSum = order.getDiscountSum();
        productCuntResponses = order.getProductCounts().stream().map(ProductCountResponse::new).collect(Collectors.toList());
        if (order.getDelivery() != null) {
            deliveryId = order.getDelivery().getId();
        }
        if (order.getUser() != null) {
            userId = order.getUser().getId();
        }
    }
}
