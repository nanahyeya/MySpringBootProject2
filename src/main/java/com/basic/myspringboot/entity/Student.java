package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String studentNumber;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StudentDetail studentDetail;

    private Department department;
}