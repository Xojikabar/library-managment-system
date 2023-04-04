package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findFirstByNameAndAuthor(String name, String author);

    List<Book> findAllByReleaseDateAfterAndReleaseDateLessThanEqual(Date releaseDate, Date releaseDate2);

    List<Book> findAllByGenre(String genre);
}