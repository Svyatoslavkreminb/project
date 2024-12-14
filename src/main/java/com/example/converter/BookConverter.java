package com.example.converter;

import com.example.dto.BookDTO;
import com.example.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {
    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setAuthorId(book.getAuthor());

        return bookDTO;
    }


    public Book toEntity(BookDTO bookDTO) {


        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setAuthor(bookDTO.getAuthorId());
        return book;
    }


}
