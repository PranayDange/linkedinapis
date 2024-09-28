package com.codingshuttle.linkedin.posts_service.service;


import com.codingshuttle.linkedin.posts_service.entity.PostLike;
import com.codingshuttle.linkedin.posts_service.exception.BadRequestException;
import com.codingshuttle.linkedin.posts_service.exception.ResourceNotFoundException;
import com.codingshuttle.linkedin.posts_service.reposiotry.PostLikesRepository;
import com.codingshuttle.linkedin.posts_service.reposiotry.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
    private final PostLikesRepository postLikesRepository;
    private final PostsRepository postsRepository;

    public void likePost(Long postId, Long userId) {
        log.info("Attempting to like the post with id: {}", postId);
        boolean exists = postsRepository.existsById(postId);
        if (!exists) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        boolean alreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId, postId);
        if (alreadyLiked) {
            throw new BadRequestException("Cannot like the same post again");
        }
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikesRepository.save(postLike);
        log.info("post with id: {} liked successfully", postId);
    }

    public void unLikePost(Long postId, Long userId) {
        log.info("Attempting to unlike the post with id: {}", postId);
        boolean exists = postsRepository.existsById(postId);
        if (!exists) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        boolean alreadyLiked = postLikesRepository.existsByUserIdAndPostId(userId, postId);
        if (!alreadyLiked) {
            throw new BadRequestException("Cannot unLike the post which is not liked");
        }

        postLikesRepository.deleteByUserIdAndPostId(userId, postId);
        log.info("post with id: {} unLiked successfully", postId);
    }
}
