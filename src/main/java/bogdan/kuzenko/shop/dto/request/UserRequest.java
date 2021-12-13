package bogdan.kuzenko.shop.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Setter
@Getter
public class UserRequest {

    @NotNull
    private String name;

    private String surname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private String address;

    @Email(message = "Email should be valid")
    private String email;


    private String login;


    private String password;

    @Pattern(regexp = "\\+\\d{12}")
    private String phoneNumber;

    private Long discountId;
}
