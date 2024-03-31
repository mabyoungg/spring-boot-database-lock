package org.example.springbootdatabaselock.domain.post.post.repository;

import jakarta.persistence.LockModeType;
import org.example.springbootdatabaselock.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Post> findWithShareLockById(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Post> findWithWriteLockById(long id);
}
