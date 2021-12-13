package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.PaginationRequest;
import bogdan.kuzenko.shop.dto.request.ProductRequest;
import bogdan.kuzenko.shop.dto.request.ProductSearchRequest;
import bogdan.kuzenko.shop.dto.response.PageResponse;
import bogdan.kuzenko.shop.dto.response.ProductFullResponse;
import bogdan.kuzenko.shop.dto.response.ProductResponse;
import bogdan.kuzenko.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static bogdan.kuzenko.shop.tool.Constants.PRODUCT_URL;

@CrossOrigin
@RestController
@RequestMapping(PRODUCT_URL)
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public void create(@RequestBody ProductRequest request) {
        productService.create(request);
    }

    @GetMapping("/page")
    public PageResponse<ProductResponse> findAllByCriteria(@Valid ProductSearchRequest request) {
        return productService.findAllByCriteria(request);
    }

    @GetMapping
    public PageResponse<ProductResponse> findAll(@Valid PaginationRequest paginationRequest){
        return productService.findAll(paginationRequest);
    }

    @GetMapping("/one")
    public ProductFullResponse findOneFull(Long id) {
        return productService.findOneFull(id);
    }

    @PutMapping
    public void update(Long id, @Valid @RequestBody ProductRequest request) {
        productService.update(id, request);
    }

    @PutMapping("/selectImage")
    public void selectImage(Long id, String image) {
        productService.selectImage(id, image);
    }

    @DeleteMapping
    public void delete(Long id) {
        productService.delete(id);
    }
}
