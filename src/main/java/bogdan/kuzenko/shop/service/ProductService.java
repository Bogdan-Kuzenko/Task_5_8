package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.PaginationRequest;
import bogdan.kuzenko.shop.dto.request.ProductRequest;
import bogdan.kuzenko.shop.dto.request.ProductSearchRequest;
import bogdan.kuzenko.shop.dto.response.PageResponse;
import bogdan.kuzenko.shop.dto.response.ProductFullResponse;
import bogdan.kuzenko.shop.dto.response.ProductResponse;
import bogdan.kuzenko.shop.entity.Product;
import bogdan.kuzenko.shop.repository.ProductRepository;
import bogdan.kuzenko.shop.specification.ProductSpecification;
import bogdan.kuzenko.shop.tool.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private MakerService makerService;

    @Autowired
    private FileTool fileTool;

    @Value("${products.img.directory}")
    private String imgDirectory;

    public void create(ProductRequest request) {
        productRepository.save(productRequestToProduct(null, request));
    }

    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    public PageResponse<ProductResponse> findAll(PaginationRequest paginationRequest){
        Page<Product> page = productRepository
                .findAll(paginationRequest.toPageable());
        return new PageResponse<>(
                page.getTotalPages(),
                page.getTotalElements(),page.get()
                .map(ProductResponse::new)
                .collect(Collectors.toList()));
    }

    public PageResponse<ProductResponse> findAllByCriteria(ProductSearchRequest request) {
        Page<Product> page = productRepository.findAll(new ProductSpecification(request), request.getPagination().toPageable());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(),page.get().map(ProductResponse::new).collect(Collectors.toList()));
    }


    public Product findOne(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not exist"));
    }

    @Transactional
    public ProductFullResponse findOneFull(Long id) {
        return new ProductFullResponse(findOne(id));
    }

    public void update(Long id, ProductRequest request) {
        productRepository.save(productRequestToProduct(findOne(id), request));
    }

    public void delete(Long id) {
        productRepository.delete(findOne(id));
    }

    public void selectImage(Long id, String image) {
        Product product = findOne(id);
        product.setMainImage(image);
        productRepository.save(product);
    }

    private Product productRequestToProduct(Product product, ProductRequest request) {
        if (product == null) {
            product = new Product();
        }
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBalance(request.getBalance());
        product.setMaker(makerService.findOne(request.getMakerId()));
        product.setSubcategory(subcategoryService.findOne(request.getSubcategoryId()));
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            product.setImages(request.getImages().stream().map(this::saveImage).collect(Collectors.toList()));
        }
        return product;
    }

    private String saveImage(String image) {
        try {
            return fileTool.saveFile(image, imgDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
