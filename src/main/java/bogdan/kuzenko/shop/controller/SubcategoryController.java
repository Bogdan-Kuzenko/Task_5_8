package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.SubcategoryRequest;
import bogdan.kuzenko.shop.dto.response.SubcategoryResponse;
import bogdan.kuzenko.shop.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.SUBCATEGORY_URL;

@CrossOrigin
@RestController
@RequestMapping(SUBCATEGORY_URL)
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;

    @Value("${subcategories.img.directory}")
    private String imgDirectory;

    @PostMapping
    public void create(@Valid @RequestBody SubcategoryRequest request) throws IOException {
        subcategoryService.create(request);
    }

    @GetMapping
    public List<SubcategoryResponse> findAll() {
        return subcategoryService.findAll();
    }

    @GetMapping("/byCategory")
    public List<SubcategoryResponse> findAllByCategory(Long categoryId) {
        return subcategoryService.findAllByCategory(categoryId);
    }

    @PutMapping
    public void update(Long id, @Valid @RequestBody SubcategoryRequest request) throws IOException {
        subcategoryService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        subcategoryService.delete(id);
    }

}
