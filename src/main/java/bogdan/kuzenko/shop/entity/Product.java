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

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    private String mainImage;

    private Long price;

    private Long balance;

    private Boolean availableProduct;



    @OneToMany(mappedBy = "product")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private Maker maker;

    @ManyToOne
    private Subcategory subcategory;

    @OneToMany(mappedBy = "product")
    private List<ProductCount> productCounts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<RatingForProduct> ratingForProducts = new ArrayList<>();


}
