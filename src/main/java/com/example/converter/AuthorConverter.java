package com.example.converter;

import com.example.dto.AuthorDTO;
import com.example.model.Author;
import com.example.model.Book;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {
    public AuthorDTO toDTO(Author author) {
        List<Book> books = author.getBooks();
//        List<String> bookTitles;
//        if (books == null) {
//            bookTitles = Collections.emptyList();
//        }
//        else{
//            bookTitles = books.stream()
//                    .map(Book::getTitle)
//                    .collect(Collectors.toList());
//        }
        List<String> bookTitles = books == null ? Collections.emptyList() : books.stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBio(author.getBio());
        dto.setBookTitles(bookTitles);
        return dto;
    }


    public Author toEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setBio(dto.getBio());
        return author;
    }
}
