package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.OrderRequest;
import bogdan.kuzenko.shop.dto.request.OrderSearchRequest;
import bogdan.kuzenko.shop.dto.request.ProductCountRequest;
import bogdan.kuzenko.shop.dto.response.OrderResponse;
import bogdan.kuzenko.shop.dto.response.PageResponse;
import bogdan.kuzenko.shop.entity.*;
import bogdan.kuzenko.shop.repository.OrderRepository;
import bogdan.kuzenko.shop.repository.ProductCountRepository;
import bogdan.kuzenko.shop.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static bogdan.kuzenko.shop.tool.Constants.DEFAULT_DISCOUNT;
import static bogdan.kuzenko.shop.tool.Constants.KIEV_ZONE;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductCountRepository productCuntRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public void create(OrderRequest request) {
        User user = userService.create(request.getUserRequest());
        final Order order = orderRepository.save(orderRequestToOrder(null, request));
        order.setUser(user);
        order.setSum(saveProductToOrder(order, request).stream().mapToLong(this::calculatePrice).sum());
        order.setDiscountSum(maxDiscount(request) / 100 * order.getSum());
        orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.delete(findOne(id));
    }

    private Order findOne(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalAccessError("Order with id " + id + " not exist"));
    }

    public PageResponse<OrderResponse> findAll(OrderSearchRequest request) {
        final Page<Order> page = orderRepository.findAll(new OrderSpecification(request), request.getPagination().toPageable());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), page.get().map(OrderResponse::new).collect(Collectors.toList()));
    }

    public void archive(Long id) {
        final Order order = findOne(id);
        order.setDateDone(LocalDateTime.now(ZoneId.of(KIEV_ZONE)));
        orderRepository.save(order);
    }

    public void unarchive(Long id) {
        final Order order = findOne(id);
        order.setDateDone(null);
        orderRepository.save(order);
    }

    public void update(Long id, OrderRequest request) {
        final Order order = orderRepository.save(orderRequestToOrder(findOne(id), request));
        order.setSum(saveProductToOrder(order, request).stream().mapToLong(this::calculatePrice).sum());
        orderRepository.save(order);
    }

    private List<ProductCount> saveProductToOrder(Order order, OrderRequest request) {
        productCuntRepository.deleteAll(order.getProductCounts());
        order.setProductCounts(new ArrayList<>());
        List<ProductCount> productsCunt = request.getProducts().stream().map(p -> productCuntForOrderRequestToProductCunt(order, p)).collect(Collectors.toList());
        productCuntRepository.saveAll(productsCunt);
        List<Product> products = productsCunt.stream().map(this::productCountToProductWithDecrementBalance).collect(Collectors.toList());
        productService.saveAll(products);
        return productsCunt;
    }


    private Long calculatePrice(ProductCount productCount) {
        return productCount.getCount() * productCount.getProduct().getPrice();
    }

    private Long maxDiscount(OrderRequest request) {
        if (request.getUserId() != null) {
            if (userService.findOne(request.getUserId()).getDiscounts() != null) {
                return userService.findOne(request.getUserId()).getDiscounts().stream().mapToLong(Discount::getValue).max().orElse(DEFAULT_DISCOUNT);
            }
            return DEFAULT_DISCOUNT;
        }
        return DEFAULT_DISCOUNT;

    }


    private ProductCount productCuntForOrderRequestToProductCunt(Order order, ProductCountRequest request) {
        return ProductCount.builder()
                .count(request.getCount())
                .order(order)
                .product(productService.findOne(request.getProductId()))
                .build();
    }

    private Order orderRequestToOrder(Order order, OrderRequest request) {
        if (order == null) {
            order = new Order();
            order.setDateOrder(LocalDateTime.now(ZoneId.of(KIEV_ZONE)));
        }
        order.setComment(request.getComment());
        if (request.getUserId() != null) {
            order.setUser(userService.findOne(request.getUserId()));
        } else {
            order.setUser(null);
        }
        return order;
    }

    private Product productCountToProductWithDecrementBalance(ProductCount productCount) {
        Product product = productCount.getProduct();
        product.setBalance(product.getBalance() - productCount.getCount());
        return product;
    }
}
