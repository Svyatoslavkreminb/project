package com.example.service;

import com.example.converter.AuthorConverter;
import com.example.dto.AuthorDTO;
import com.example.exception.EntityNotFoundException;
import com.example.model.Author;
import com.example.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private  AuthorRepository authorRepository;
    private  AuthorConverter authorConverter;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.authorConverter = authorConverter;
    }



    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(authorConverter::toDTO)
                .collect(Collectors.toList());
    }


    public AuthorDTO findById(Long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        return authorConverter.toDTO(author);
    }

    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = authorConverter.toEntity(authorDTO);
        Author savedAuthor = authorRepository.saveAndFlush(author);
        return authorConverter.toDTO(savedAuthor);
    }


    @Transactional
    public AuthorDTO update(Long id, AuthorDTO authorDTO) throws EntityNotFoundException {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));


        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setBio(authorDTO.getBio());

        Author updatedAuthor = authorRepository.saveAndFlush(existingAuthor);
        return authorConverter.toDTO(updatedAuthor);
    }


    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        if (!authorRepository.existsById(id)) {
            throw new EntityNotFoundException("Author not found");
        }
        authorRepository.deleteById(id);
    }
}
