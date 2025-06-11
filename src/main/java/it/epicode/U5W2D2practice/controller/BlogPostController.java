package it.epicode.U5W2D2practice.controller;

import it.epicode.U5W2D2practice.dto.BlogPostDto;
import it.epicode.U5W2D2practice.exception.BlogPostNotFoundException;
import it.epicode.U5W2D2practice.model.BlogPost;
import it.epicode.U5W2D2practice.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/blogPosts")
public class BlogPostController {
        @Autowired
        private BlogPostService blogPostService;

        @PostMapping
        public BlogPost creaBlogPost(@RequestBody BlogPostDto blogPostDto) {
            return blogPostService.saveBlogPost(blogPostDto);
        }

        @GetMapping
        public List<BlogPost> getAllBlogPosts() {
            return blogPostService.getAllBlogPosts();
        }

        @GetMapping("/{id}")
        public BlogPost getBlogPost(@PathVariable int id) throws BlogPostNotFoundException {
            return blogPostService.getBlogPost(id);
        }

        @PutMapping("/{id}")
        public BlogPost aggiornaBlogPost(@PathVariable int id, @RequestBody BlogPost blogPost) throws BlogPostNotFoundException {
            return blogPostService.updateBlogPost(id, blogPost);
        }

        @DeleteMapping("/{id}")
        public void eliminaBlogPost(@PathVariable int id) throws BlogPostNotFoundException {
            blogPostService.deleteBlogPost(id);
        }

    @GetMapping
    public Page<BlogPost> getBlogPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titolo") String sortBy
    ) {
        return blogPostService.getBlogPosts(page, size, sortBy);
    }


    }

