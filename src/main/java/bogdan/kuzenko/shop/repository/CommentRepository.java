package bogdan.kuzenko.shop.repository;

import bogdan.kuzenko.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByProductId(Long productId);

    List<Comment> findAllByProductIdAndHiddenIsFalse(Long productId);

    List<Comment> findAllByUserId(Long userId);
}
