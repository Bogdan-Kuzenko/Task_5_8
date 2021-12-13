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

public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deliveryComment;
    private String deliveryAddress;

    @OneToMany(mappedBy = "delivery")
    private List<Order> orders = new ArrayList<>();

}
