package org.example.springbootdatabaselock.domain.post.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.springbootdatabaselock.domain.post.post.entity.Post;
import org.example.springbootdatabaselock.domain.post.post.service.PostService;
import org.example.springbootdatabaselock.standard.dto.retryOnOptimisticLock.RetryOnOptimisticLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PostController {
    private final PostService postService;
    @Autowired
    @Lazy
    private ApiV1PostController self;

    @GetMapping("/{id}")
    public Post getPost(
            @PathVariable long id
    ) {
        return postService.findById(id).get();
    }

    @SneakyThrows
    @GetMapping("/{id}/withShareLock")
    public Post getWithShareLockPost(
            @PathVariable long id
    ) {
        Post post = postService.findWithShareLockById(id).get();

        Thread.sleep(10_000L);

        return post;
    }

    @SneakyThrows
    @GetMapping("/{id}/withWriteLock")
    @Transactional
    public Post getWithWriteLockPost(
            @PathVariable long id
    ) {
        Post post = postService.findWithWriteLockById(id).get();
        post.setTitle(post.getTitle() + "!");

        Thread.sleep(10_000L);

        return post;
    }

    @GetMapping("/{id}/putWithPessimistic")
    @Transactional
    public Post modifyWithPessimistic(
            @PathVariable long id,
            String title
    ) {
        Post post = postService.modifyWithPessimistic(id, title);

        return post;
    }

    @GetMapping("/{id}/putWithOptimistic")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Post modifyWithOptimistic(
            @PathVariable long id,
            String title
    ) {
        return self._modifyWithOptimistic(id, title);
    }

    @Transactional
    // 더티체킹이 제대로 먹으려면 아래 어노테이션 붙은 메서드는 꼭 호출자 메서드가 트랜잭션이 아니어야 한다.
    @RetryOnOptimisticLock(attempts = 2, backoff = 500L)
    public Post _modifyWithOptimistic(
            long id,
            String title
    ) {
        Post post = postService.modifyWithOptimistic(id, title);

        return post;
    }
}
