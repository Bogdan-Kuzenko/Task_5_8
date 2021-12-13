package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CommentRequest {

    @NotNull
    private String text;
    @NotNull
    private Long productId;
    private Boolean hidden;
    @NotNull
    private Long userId;

}
