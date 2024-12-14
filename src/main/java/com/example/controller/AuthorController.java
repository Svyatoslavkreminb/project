package com.example.controller;

import com.example.dto.AuthorDTO;
import com.example.exception.EntityNotFoundException;
import com.example.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {


    private AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        try {
            AuthorDTO author = authorService.findById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO savedAuthor = authorService.save(authorDTO);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        try {
            AuthorDTO updatedAuthor = authorService.update(id, authorDTO);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
