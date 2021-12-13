package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponse {

    private Long id;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datePosted;
    private Boolean hidden;

    @JsonProperty("user")
    private UserResponse userResponse;

    public CommentResponse(Comment comment) {
        id = comment.getId();
        userResponse = new UserResponse(comment.getUser());
        text = comment.getText();
        datePosted = comment.getDatePosted();
        hidden = comment.getHidden();
    }
}
