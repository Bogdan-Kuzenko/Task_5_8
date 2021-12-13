package bogdan.kuzenko.shop.specification;

import bogdan.kuzenko.shop.dto.request.ProductSearchRequest;
import bogdan.kuzenko.shop.entity.Category;
import bogdan.kuzenko.shop.entity.Maker;
import bogdan.kuzenko.shop.entity.Product;
import bogdan.kuzenko.shop.entity.Subcategory;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.*;

@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private String value;
    private Integer minPrice;
    private Integer maxPrice;
    private Long makerId;
    private Long categoryId;
    private Long subcategoryId;

    public ProductSpecification(ProductSearchRequest request) {
        value = request.getValue();
        minPrice = request.getMinPrice();
        maxPrice = request.getMaxPrice();
        makerId = request.getMakerId();
        categoryId = request.getCategoryId();
        subcategoryId = request.getSubcategoryId();
    }


    @Override
    public Predicate toPredicate(Root<Product> r, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(findByNameLike(r, cb));
        predicates.add(findByPrice(r, cb));
        predicates.add(findByCategoryAndSubcategory(r, cb));
        predicates.add(findByMakers(r, cb));
        return cb.and(predicates.toArray(new Predicate[]{}));
    }

    private Predicate findByCategoryAndSubcategory(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        final Join<Product, Subcategory> subcategory = r.join(FIELD_SUBCATEGORY);
        final Join<Subcategory, Category> category = subcategory.join(FIELD_CATEGORY);
        if (subcategoryId != null) {
            predicate = cb.equal(subcategory.get("id"), subcategoryId);
        } else if (categoryId != null) {
            predicate = cb.equal(category.get("id"), categoryId);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByMakers(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        final Join<Product, Maker> maker = r.join(FIELD_MAKER);
        if (makerId != null) {
            predicate = cb.equal(maker.get("id"), makerId);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByPrice(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (minPrice != null && maxPrice != null) {
            predicate = cb.between(r.get(FIELD_PRICE), minPrice, maxPrice);
        } else if (minPrice != null) {
            predicate = cb.greaterThanOrEqualTo(r.get(FIELD_PRICE), minPrice);
        } else if (maxPrice != null) {
            predicate = cb.lessThanOrEqualTo(r.get(FIELD_PRICE), maxPrice);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }




    private Predicate findByNameLike(Root<Product> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (value != null) {
            predicate = cb.like(r.get(FIELD_NAME), PERCENT + value + PERCENT);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }



}
