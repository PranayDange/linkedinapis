package com.codingshuttle.linkedin.posts_service.reposiotry;

import com.codingshuttle.linkedin.posts_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {


    List<Post> findByUserId(Long userId);
}

