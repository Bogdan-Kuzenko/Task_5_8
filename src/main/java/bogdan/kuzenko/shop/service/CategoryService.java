package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.CategoryRequest;
import bogdan.kuzenko.shop.dto.response.CategoryResponse;
import bogdan.kuzenko.shop.entity.Category;
import bogdan.kuzenko.shop.repository.CategoryRepository;
import bogdan.kuzenko.shop.tool.Constants;
import bogdan.kuzenko.shop.tool.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileTool fileTool;

    @Value("${categories.img.directory}")
    private String imgDirectory;

    public void create(CategoryRequest request) throws IOException {
        categoryRepository.save(categoryRequestToCategory(null, request));
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll(Sort.by(Constants.FIELD_NAME)).stream().map(CategoryResponse::new).collect(Collectors.toList());
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " not exist"));
    }

    public void update(Long id, CategoryRequest request) throws IOException {
        categoryRepository.save(categoryRequestToCategory(findOne(id), request));
    }

    public void delete(Long id) {
        categoryRepository.delete(findOne(id));
    }

    private Category categoryRequestToCategory(Category category, CategoryRequest request) throws IOException {
        if (category == null) {
            category = new Category();
        }
            category.setHideCategory(request.getHideCategory());
            category.setName(request.getName());
        if (request.getImage() != null) {
            category.setImage(fileTool.saveFile(request.getImage(), imgDirectory));
        }
        return category;
    }


}
