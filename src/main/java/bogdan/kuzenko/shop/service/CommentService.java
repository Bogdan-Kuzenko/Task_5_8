package bogdan.kuzenko.shop.service;

import bogdan.kuzenko.shop.dto.request.CommentRequest;
import bogdan.kuzenko.shop.dto.response.CommentResponse;
import bogdan.kuzenko.shop.entity.Comment;
import bogdan.kuzenko.shop.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bogdan.kuzenko.shop.tool.Constants.KIEV_ZONE;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    public void save(CommentRequest request) {
        commentRepository.save(commentRequestToComment(null, request));
    }

    public void delete(Long id) {
        commentRepository.delete(findOne(id));
    }

    public List<CommentResponse> findAll() {
        return commentRepository.findAll().stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    public List<CommentResponse> findAllByProductId(Long productID) {
        return commentRepository.findAllByProductId(productID).stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    public List<CommentResponse> findAllToShowByProductId(Long productId) {
        return commentRepository.findAllByProductIdAndHiddenIsFalse(productId).stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    public List<CommentResponse> findAllByUserId(Long userId) {
        return commentRepository.findAllByUserId(userId).stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    private Comment findOne(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment whit id " + id + " not exist"));
    }

    public void update(Long id, CommentRequest request) {
        commentRepository.save(commentRequestToComment(findOne(id), request));
    }

    public void setHiddenFalse(Long id){
        Comment comment = findOne(id);
        comment.setHidden(false);
        commentRepository.save(comment);
    }

    public void setHiddenTrue(Long id){
        Comment comment = findOne(id);
        comment.setHidden(true);
        commentRepository.save(comment);
    }

    private Comment commentRequestToComment(Comment comment, CommentRequest request) {
        if (comment == null) {
            comment = new Comment();
            comment.setHidden(true);
            comment.setDatePosted(LocalDateTime.now(ZoneId.of(KIEV_ZONE)));
        } else {
            comment.setHidden(request.getHidden());
        }
        comment.setText(request.getText());
        comment.setProduct(productService.findOne(request.getProductId()));
        comment.setUser(userService.findOne(request.getUserId()));
        return comment;
    }


}