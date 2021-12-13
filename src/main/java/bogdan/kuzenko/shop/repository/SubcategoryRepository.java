package bogdan.kuzenko.shop.repository;

import bogdan.kuzenko.shop.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Stream<Subcategory> findAllByCategoryId(Long categoryId);
}