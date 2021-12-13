package bogdan.kuzenko.shop.dto.response;

import bogdan.kuzenko.shop.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String surname;
    private String address;
    private String email;
    private String login;
    private String password;
    private String phoneNumber;

    @JsonProperty("discount")
    private List<DiscountResponse> discountResponses;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public UserResponse(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        birthday = user.getBirthday();
        address = user.getAddress();
        email = user.getEmail();
        login = user.getLogin();
        password = user.getPassword();
        phoneNumber = user.getPhoneNumber();
        discountResponses = user.getDiscounts().stream().map(DiscountResponse::new).collect(Collectors.toList());
    }
}
