package bogdan.kuzenko.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long value;


    @OneToMany(mappedBy = "discount")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(mappedBy = "discounts")
    private List<User> users = new ArrayList<>();
}
