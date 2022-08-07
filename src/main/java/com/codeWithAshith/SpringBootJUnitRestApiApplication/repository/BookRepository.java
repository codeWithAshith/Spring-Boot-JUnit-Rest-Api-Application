package com.codeWithAshith.SpringBootJUnitRestApiApplication.repository;

import com.codeWithAshith.SpringBootJUnitRestApiApplication.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
