package bogdan.kuzenko.shop.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderRequest {

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDone;

    private Long userId;
    private UserRequest userRequest;

    @NotEmpty
    @JsonProperty("products")
    private List<ProductCountRequest> products;

}
