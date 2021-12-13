package bogdan.kuzenko.shop.controller;

import bogdan.kuzenko.shop.dto.request.CommentRequest;
import bogdan.kuzenko.shop.dto.response.CommentResponse;
import bogdan.kuzenko.shop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static bogdan.kuzenko.shop.tool.Constants.COMMENT_URL;

@CrossOrigin
@RestController
@RequestMapping(COMMENT_URL)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public void create(@Valid @RequestBody CommentRequest request) {
        commentService.save(request);
    }

    @GetMapping()
    public List<CommentResponse> findAll() {
        return commentService.findAll();
    }

    @GetMapping("/byProduct")
    public List<CommentResponse> findAllByProductId(Long productId) {
        return commentService.findAllByProductId(productId);
    }

    @GetMapping("/showByProduct")
    public List<CommentResponse> findAllToShowByProductId(Long productId) {
        return commentService.findAllToShowByProductId(productId);
    }

    @GetMapping("/byUser")
    public List<CommentResponse> findAllByUserId(Long userId) {
        return commentService.findAllByUserId(userId);
    }

    @PutMapping
    public void update(Long id, @Valid @RequestBody CommentRequest request) {
        commentService.update(id, request);
    }

    @PutMapping ("/show")
    public void setHiddenFalse(Long id){
        commentService.setHiddenFalse(id);
    }

    @PutMapping ("/hidden")
    public void setHiddenTrue(Long id){
        commentService.setHiddenTrue(id);
    }

    @DeleteMapping
    public void delete(Long id) {
        commentService.delete(id);
    }

}
