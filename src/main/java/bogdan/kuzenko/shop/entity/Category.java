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

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private Boolean hideCategory;

    @OneToMany(mappedBy = "category")
    private List<Subcategory> subcategories = new ArrayList<>();

}
