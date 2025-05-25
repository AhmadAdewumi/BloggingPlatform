package com.ahmad.BloggingPlatform.repository;

import com.ahmad.BloggingPlatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@EnableJpaRepositories
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t IN :tags")
    Optional<List<Post>> findPostsByTags(@Param("tags") Set<String> tags);
//    Optional<Collection<Post>> findPostsByTags(Set<String> tags);
}
