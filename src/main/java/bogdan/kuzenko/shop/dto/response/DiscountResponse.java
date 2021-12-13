package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Discount;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class DiscountResponse {

    private Long id;
    private Long value;


    public DiscountResponse(Discount discount) {
        id = discount.getId();
        value = discount.getValue();
    }
}
