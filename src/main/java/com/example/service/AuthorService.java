package com.example.service;

import com.example.exception.EntityNotFoundException;
import com.example.model.Author;
import com.example.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long id) throws EntityNotFoundException {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
    }

    @Transactional
    public Author save(Author author) {
        return authorRepository.saveAndFlush(author);
    }

    @Transactional
    public Author update(Long id, Author authorDetails) {
        Author author = findById(id);
        author.setName(authorDetails.getName());
        author.setBio(authorDetails.getBio());
        return authorRepository.saveAndFlush(author);
    }

    @Transactional
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
