package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RatingForProductRequest {

    private Double rating;
    @NotNull
    private Long productId;
    @NotNull
    private Long userId;
}
