package org.example.springbootdatabaselock.domain.post.post.repository;

import org.example.springbootdatabaselock.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
