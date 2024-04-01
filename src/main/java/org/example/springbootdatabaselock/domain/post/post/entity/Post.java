package org.example.springbootdatabaselock.domain.post.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.*;
import org.example.springbootdatabaselock.global.jpa.entity.BaseTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
public class Post extends BaseTime {
    private String title;

    @Version
    private Long version;
}
