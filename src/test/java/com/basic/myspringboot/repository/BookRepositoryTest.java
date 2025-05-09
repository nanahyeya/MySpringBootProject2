package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Rollback(value = false)
    void testCreateBook() {
        //Given (준비 단계)
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setTitle("스프링 부트 입문");
        book1.setAuthor("홍길동");
        book1.setIsbn("9788956746425");
        book1.setPrice(30000);
        book1.setPublishDate(LocalDate.parse("2025-05-07"));

        book2.setTitle("JPA 프로그래밍");
        book2.setAuthor("박둘리");
        book2.setIsbn("9788956746432");
        book2.setPrice(35000);
        book2.setPublishDate(LocalDate.parse("2025-04-30"));

        //When (실행 단계)
        Book addBook1 = bookRepository.save(book1);
        Book addBook2 = bookRepository.save(book2);

        //Then (검증 단계)
        assertThat(addBook1).isNotNull();
        assertThat(addBook2).isNotNull();
        Optional<Book> optionalBook1 = bookRepository.findByIsbn("9788956746425");
        Book found = optionalBook1.orElseGet(() -> new Book());
        assertThat(found.getTitle()).isEqualTo("스프링 부트 입문");

    }

    @Test
    void testFindByIsbn() {
        Optional<Book> optionalBook1 = bookRepository.findByIsbn("9788956746425");
        Book book1 = optionalBook1.orElseGet(() -> new Book());
        assertThat(book1.getTitle()).isEqualTo("스프링 부트 입문");

        Optional<Book> optionalBook2 = bookRepository.findByIsbn("9788956746432");
        Book book2 = optionalBook1.orElseGet(() -> new Book());
        assertThat(book1.getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    void testFindByAuthor() {
        Optional<Book> optionalBook1 = bookRepository.findByAuthor("홍길동");
        Book book1 = optionalBook1.orElseGet(() -> new Book());
        assertThat(book1.getTitle()).isEqualTo("스프링 부트 입문");

        Optional<Book> optionalBook2 = bookRepository.findByAuthor("박둘리");
        Book book2 = optionalBook1.orElseGet(() -> new Book());
        assertThat(book1.getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    @Rollback(value = false)
    void testUpdateBook() {
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow(() -> new RuntimeException("Book Not Found"));
        book.setTitle("김나혜");
        assertThat(book.getTitle()).isEqualTo("김나혜");
    }

    @Test
    @Rollback(value = false)
    void testDeleteBook() {
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow(() -> new RuntimeException("Book Not Found"));
        bookRepository.delete(book);
    }
}
