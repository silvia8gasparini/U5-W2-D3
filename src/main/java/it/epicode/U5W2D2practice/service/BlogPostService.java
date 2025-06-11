package it.epicode.U5W2D2practice.service;

import it.epicode.U5W2D2practice.dto.BlogPostDto;
import it.epicode.U5W2D2practice.exception.AutoreNotFoundException;
import it.epicode.U5W2D2practice.exception.BlogPostNotFoundException;
import it.epicode.U5W2D2practice.model.Autore;
import it.epicode.U5W2D2practice.model.BlogPost;
import it.epicode.U5W2D2practice.repository.AutoreRepository;
import it.epicode.U5W2D2practice.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private AutoreRepository autoreRepository;

    public BlogPost saveBlogPost(BlogPostDto blogPostDto) {
        Autore autore = autoreRepository.findById(blogPostDto.getAutoreId()).orElseThrow(() -> new AutoreNotFoundException("Autore con id " + blogPostDto.getAutoreId() + " non trovato" ));
        BlogPost blogPost = new BlogPost();
        blogPost.setTitolo(blogPostDto.getTitolo());
        blogPost.setCategoria(blogPost.getCategoria());
        blogPost.setContenuto(blogPost.getContenuto());
        blogPost.setCover("https://picsum.photos/200/300");
        blogPost.setTempoDiLettura(calcolaTempoDiLettura(blogPostDto.getContenuto()));
        blogPost.setAutore(autore);
        return blogPostRepository.save(blogPost);
    }

    public BlogPost getBlogPost(int id) throws BlogPostNotFoundException {
        return blogPostRepository.findById(id)
             .orElseThrow(() -> new BlogPostNotFoundException("Post con id " + id + " non trovato"));
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public BlogPost updateBlogPost(int id, BlogPost blogPost) throws BlogPostNotFoundException {
        BlogPost postToUpdate = getBlogPost(id);

        postToUpdate.setTitolo(blogPost.getTitolo());
        postToUpdate.setCategoria(blogPost.getCategoria());
        postToUpdate.setContenuto(blogPost.getContenuto());
        postToUpdate.setTempoDiLettura(calcolaTempoDiLettura(blogPost.getContenuto()));

        return postToUpdate;
    }

    public void deleteBlogPost(int id) throws BlogPostNotFoundException {
        BlogPost blogPostToRemove = getBlogPost(id);
        blogPostRepository.delete(blogPostToRemove);
    }

    private int calcolaTempoDiLettura(String contenuto) {
        if (contenuto == null || contenuto.isBlank()) {
            return 1;
        }

        int conteggioParole = 0;
        Scanner scanner = new Scanner(contenuto);

        while (scanner.hasNext()) {
            scanner.next();
            conteggioParole++;
        }
        scanner.close();
        return Math.max(1, conteggioParole / 200);
    }

    public Page<BlogPost> getBlogPosts(int page, int size, String sortBy) {
        return blogPostRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }


}
