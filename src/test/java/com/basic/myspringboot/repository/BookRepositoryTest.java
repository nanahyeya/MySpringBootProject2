package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
        book1.setBookTitle("스프링 부트 입문");
        book1.setBookAuthor("홍길동");
        book1.setBookIsbn("9788956746425");
        book1.setBookPrice("30000");
        book1.setBookPublishDate("2025-05-07");

        book2.setBookTitle("JPA 프로그래밍");
        book2.setBookAuthor("박둘리");
        book2.setBookIsbn("9788956746432");
        book2.setBookPrice("35000");
        book2.setBookPublishDate("2025-04-30");

        //When (실행 단계)
        Book addBook1 = bookRepository.save(book1);
        Book addBook2 = bookRepository.save(book2);

        //Then (검증 단계)
        assertThat(addBook1).isNotNull();
        assertThat(addBook1.getCustomerName()).isEqualTo("스프링2");

    }

    @Test
    void testFindByIsbn() {


    }
}
