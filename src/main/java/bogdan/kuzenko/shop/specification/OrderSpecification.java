package bogdan.kuzenko.shop.specification;

import bogdan.kuzenko.shop.dto.request.OrderSearchRequest;
import bogdan.kuzenko.shop.entity.Order;
import bogdan.kuzenko.shop.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.FIELD_USER;
import static bogdan.kuzenko.shop.tool.Constants.PERCENT;

@AllArgsConstructor
public class OrderSpecification implements Specification<Order> {

    private Long minSum;
    private Long maxSum;

    private Long userId;

    private Date dateFrom;
    private Date dateTo;

    private Boolean done;
    private String value;


    public OrderSpecification(OrderSearchRequest request) {
        minSum = request.getMinSum();
        maxSum = request.getMaxSum();
        dateFrom = request.getDateFrom();
        dateTo = request.getDateTo();
        value = request.getValue();
        userId = request.getUserId();
    }

    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(findBySum(root, criteriaBuilder));
        predicates.add(findByDate(root, criteriaBuilder));
        predicates.add(findByUser(root, criteriaBuilder));
        predicates.add(findByDone(root,criteriaBuilder));
        predicates.add(findByNameOrPhoneNumberOrAddressOrEmail(root, criteriaBuilder));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }


    private Predicate findBySum(Root<Order> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (maxSum == null && minSum == null) {
            predicate = cb.conjunction();
        } else if (maxSum == null) {
            predicate = cb.greaterThanOrEqualTo(r.get("sum"), minSum);
        } else if (minSum == null) {
            predicate = cb.lessThanOrEqualTo(r.get("sum"), maxSum);
        } else {
            predicate = cb.between(r.get("sum"), minSum, maxSum);
        }
        return predicate;
    }

    private Predicate findByDate(Root<Order> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (dateFrom == null && dateTo == null) {
            predicate = cb.conjunction();
        } else if (dateTo == null) {
            predicate = cb.greaterThanOrEqualTo(r.get("date"), dateFrom);
        } else if (dateFrom == null) {
            predicate = cb.lessThanOrEqualTo(r.get("date"), dateTo);
        } else {
            predicate = cb.between(r.get("date"), dateFrom, dateTo);
        }
        return predicate;
    }

    private Predicate findByDone(Root<Order> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (done != null) {
            return done ? cb.isNotNull(r.get("done")) : cb.isNull(r.get("done"));
        }else {
            predicate = cb.conjunction();
        }
        return predicate;
    }

    private Predicate findByUser(Root<Order> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (userId != null) {
            final Join<Order, User> userJoin = r.join(FIELD_USER);
            predicate = cb.equal(userJoin.get("id"), userId);
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }


    private Predicate findByNameOrPhoneNumberOrAddressOrEmail(Root<Order> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (value != null && !value.trim().isEmpty()) {
            final Join<Order, User> userJoin = r.join(FIELD_USER);
            return cb.or(
                    cb.equal(userJoin.get("name"), PERCENT + value.trim() + PERCENT),
                    cb.equal(userJoin.get("surname"), PERCENT + value.trim() + PERCENT),
                    cb.equal(userJoin.get("phoneNumber"), PERCENT + value.trim() + PERCENT),
                    cb.equal(userJoin.get("email"), PERCENT + value.trim() + PERCENT),
                    cb.equal(userJoin.get("address"), PERCENT + value.trim() + PERCENT)
            );
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }


}