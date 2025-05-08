package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
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
@RequestMapping("/api/users")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
    private final UserRepository userRepository;

    //Constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        System.out.println(">>> UserController " + userRepository.getClass().getName());
//        this.userRepository = userRepository;
//    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        // public <U> Optional <U> map(Function<? super T, ?  extends U> mapper)
        // Function의 추상메서드 R apply(T t)
        // Optional<ResponseEntity>
        ResponseEntity<User> responseEntity = optionalUser
                .map(user -> ResponseEntity.ok(user)) //User가 있는 경우 200 status code
//                .orElse(ResponseEntity.notFound().build()); // User가 없는 경우 404 status code
                .orElse(new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND)); // Error 메세지 출력
        return responseEntity;

        // lambda 형식으로 작성
//        return optionalUser.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        Optional<User> optionalUser = userRepository.findByEmail((email));
        User existUser = getExistUser(optionalUser);
        return existUser;
    }

    // 부분 수정
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetail) {
        User existUser = getExistUser(userRepository.findById(id));

        // setter method 호출
        existUser.setName(userDetail.getName());
        User updatedUser = userRepository.save(existUser);
        return  ResponseEntity.ok(updatedUser);
    }

    private User getExistUser(Optional<User> userRepository) {
        User existUser = userRepository
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        return existUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = getExistUser(userRepository.findById(id));
        userRepository.delete(user);
        // return ResponseEntity.ok("User가 삭제되었습니다."); // status code 200
        return ResponseEntity.ok().build(); // status code 204
    }
    


}