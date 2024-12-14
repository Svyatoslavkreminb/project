package com.example.service;

import com.example.converter.BookConverter;
import com.example.dto.BookDTO;
import com.example.exception.EntityNotFoundException;
import com.example.model.Book;
import com.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private  BookRepository bookRepository;
    private  BookConverter bookConverter;

    @Autowired
    public BookService(BookRepository bookRepository, BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }


    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookConverter::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id) throws EntityNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        return bookConverter.toDTO(book);
    }

    @Transactional
    public BookDTO save(BookDTO bookDTO) {
        Book book = bookConverter.toEntity(bookDTO);
        Book savedBook = bookRepository.saveAndFlush(book);
        return bookConverter.toDTO(savedBook);
    }

    @Transactional
    public BookDTO update(Long id, BookDTO bookDTO) throws EntityNotFoundException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setDescription(bookDTO.getDescription());


        Book updatedBook = bookRepository.saveAndFlush(existingBook);
        return bookConverter.toDTO(updatedBook);
    }

    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
