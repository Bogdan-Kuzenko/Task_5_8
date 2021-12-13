package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.SubcategoryRequest;
import bogdan.kuzenko.shop.dto.response.SubcategoryResponse;
import bogdan.kuzenko.shop.entity.Subcategory;
import bogdan.kuzenko.shop.repository.SubcategoryRepository;
import bogdan.kuzenko.shop.tool.FileTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileTool fileTool;

    @Value("${subcategories.img.directory}")
    private String imgDirectory;

    public void create(SubcategoryRequest request) throws IOException {
        subcategoryRepository.save(subcategoryRequestToSubcategory(null, request));
    }

    public List<SubcategoryResponse> findAll() {
        return subcategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "category.name")).stream().map(SubcategoryResponse::new).collect(Collectors.toList());
    }

    public Subcategory findOne(Long id) {
        return subcategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subcategory with id " + id + " not exist"));
    }

    @Transactional
    public List<SubcategoryResponse> findAllByCategory(Long categoryId) {
        return subcategoryRepository.findAllByCategoryId(categoryId).map(SubcategoryResponse::new).collect(Collectors.toList());
    }

    public void update(Long id, SubcategoryRequest request) throws IOException {
        subcategoryRepository.save(subcategoryRequestToSubcategory(findOne(id), request));
    }

    public void delete(Long id) {
        subcategoryRepository.delete(findOne(id));
    }

    private Subcategory subcategoryRequestToSubcategory(Subcategory subcategory, SubcategoryRequest request) throws IOException {
        if (subcategory == null) {
            subcategory = new Subcategory();
        }
        subcategory.setName(request.getName());
        subcategory.setHideSubcategory(request.getHideSubcategory());
        subcategory.setCategory(categoryService.findOne(request.getCategoryId()));
        if (request.getImage() != null) {
            subcategory.setImage(fileTool.saveFile(request.getImage(), imgDirectory));
        }
        return subcategory;
    }
}
