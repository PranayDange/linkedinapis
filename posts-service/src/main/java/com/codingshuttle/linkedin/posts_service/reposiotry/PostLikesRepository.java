package com.codingshuttle.linkedin.posts_service.reposiotry;

import com.codingshuttle.linkedin.posts_service.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PostLikesRepository extends JpaRepository<PostLike, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Transactional
    void deleteByUserIdAndPostId(Long userId, Long postId);

}
