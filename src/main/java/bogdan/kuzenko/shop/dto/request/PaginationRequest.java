package bogdan.kuzenko.shop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class PaginationRequest {

    @NotNull
    private Integer size;
    @NotNull
    private Integer page;
    private Sort.Direction direction;
    private String field;


    public Pageable toPageable() {
        if (field != null && direction != null) {
            return PageRequest.of(page, size, direction, field);
        } else if (field != null) {
            return PageRequest.of(page, size, Sort.Direction.ASC, field);
        } else {
            return PageRequest.of(page, size);
        }
    }
}
