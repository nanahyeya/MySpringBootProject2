package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.Book;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
//final 인 변수를 초기화하는 생성자를 자동으로 생성해주는 역할을 하는 롬복 어노테이션
@RequestMapping("/api/books")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;

    @PostMapping
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        ResponseEntity<Book> responseEntity = optionalBook
                .map(book -> ResponseEntity.ok(book)) //Book가 있는 경우 200 status code
//                .orElse(ResponseEntity.notFound().build()); // Book가 없는 경우 404 status code
                .orElse(new ResponseEntity("Book Not Found", HttpStatus.NOT_FOUND)); // Error 메세지 출력
        return responseEntity;
    }

    @GetMapping("/isbn/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> optionalBook = bookRepository.findByIsbn((isbn));
        Book existBook = getExistBook(optionalBook);
        return existBook;
    }
    private Book getExistBook(Optional<Book> bookRepository) {
        Book existBook = bookRepository
                .orElseThrow(() -> new BusinessException("Book Not Found", HttpStatus.NOT_FOUND));
        return existBook;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetail) {
        Book existBook = getExistBook(bookRepository.findById(id));
        //setter method 호출
        existBook.setTitle(bookDetail.getTitle());
        Book updatedBook = bookRepository.save(existBook);
        return ResponseEntity.ok(updatedBook);
//        return ResponseEntity.ok(bookRepository.save(existBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book book = getExistBook(bookRepository.findById(id));
        bookRepository.delete(book);
        return ResponseEntity.ok("Book이 삭제 되었습니다!"); //status code 200
        //return ResponseEntity.noContent().build();  //status code 204
    }
}
