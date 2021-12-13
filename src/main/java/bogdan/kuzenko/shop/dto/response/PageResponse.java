package bogdan.kuzenko.shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PageResponse<T> {

    private Integer totalPages;
    private Long totalElements;
    private List<T> content;

}
