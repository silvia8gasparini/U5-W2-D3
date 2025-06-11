package it.epicode.U5W2D2practice.repository;

import it.epicode.U5W2D2practice.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost,Integer> {
}
