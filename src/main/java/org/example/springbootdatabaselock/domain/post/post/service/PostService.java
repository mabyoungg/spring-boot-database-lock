package org.example.springbootdatabaselock.domain.post.post.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.springbootdatabaselock.domain.post.post.entity.Post;
import org.example.springbootdatabaselock.domain.post.post.repository.PostRepository;
import org.example.springbootdatabaselock.global.rsData.RsData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public RsData<Post> write(String title) {
        Post post = postRepository.save(
                Post.builder()
                        .title(title)
                        .build()
        );

        return RsData.of(post);
    }

    public long count() {
        return postRepository.count();
    }
}
