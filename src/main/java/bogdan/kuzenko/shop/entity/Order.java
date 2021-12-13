package bogdan.kuzenko.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "_Order")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOrder;

    private String comment;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDone;

    private Long sum;

    private Long discountSum;


    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<ProductCount> productCounts = new ArrayList<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private Discount discount;

    @ManyToOne
    private Delivery delivery;



}
