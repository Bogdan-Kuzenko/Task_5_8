package bogdan.kuzenko.shop.repository;

import bogdan.kuzenko.shop.entity.ProductCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCountRepository extends JpaRepository<ProductCount, Long> {
}