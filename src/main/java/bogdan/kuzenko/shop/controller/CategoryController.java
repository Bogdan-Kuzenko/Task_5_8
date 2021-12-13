package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.CategoryRequest;
import bogdan.kuzenko.shop.dto.response.CategoryResponse;
import bogdan.kuzenko.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.CATEGORY_URL;

@CrossOrigin
@RestController
@RequestMapping(CATEGORY_URL)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public void create(@Valid @RequestBody CategoryRequest request) throws IOException {
        categoryService.create(request);
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @PutMapping
    public void update(Long id, @Valid @RequestBody CategoryRequest request) throws IOException {
        categoryService.update(id, request);
    }

    @DeleteMapping
    public void delete(Long id) {
        categoryService.delete(id);
    }

}
