package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.OrderRequest;
import bogdan.kuzenko.shop.dto.request.OrderSearchRequest;
import bogdan.kuzenko.shop.dto.response.OrderResponse;
import bogdan.kuzenko.shop.dto.response.PageResponse;
import bogdan.kuzenko.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bogdan.kuzenko.shop.tool.Constants.ORDER_URL;

@CrossOrigin
@RestController
@RequestMapping(ORDER_URL)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void create(@Valid @RequestBody OrderRequest request) {
        orderService.create(request);
    }


    @PostMapping("/page")
    public PageResponse<OrderResponse> findAll(OrderSearchRequest request) {
        return orderService.findAll(request);
    }

    @PutMapping
    public void update(Long id,@Valid @RequestBody OrderRequest request) {
        orderService.update(id, request);
    }


    @PutMapping("/archive")
    public void archive(Long id) {
        orderService.archive(id);
    }

    @PutMapping("/unarchive")
    public void unarchive(Long id) {
        orderService.unarchive(id);
    }

    @DeleteMapping
    public void delete(Long id) {
        orderService.delete(id);
    }
}
