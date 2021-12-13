package bogdan.kuzenko.shop.repository;

import bogdan.kuzenko.shop.entity.RatingForProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingForProductRepository extends JpaRepository<RatingForProduct, Long> {

    List<RatingForProduct> findAllByUserId(Long userId);

    List<RatingForProduct> findAllByProductId(Long productId);

}
