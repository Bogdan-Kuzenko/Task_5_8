package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class DiscountRequest {

    @NotNull
    private Long value;
    private Long userId;
}
