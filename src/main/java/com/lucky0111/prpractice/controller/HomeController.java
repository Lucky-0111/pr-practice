package com.lucky0111.prpractice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<?> home() {
        // 테스트용 주석입니다ㅇㅇㅇ
        // 코드
        return ResponseEntity.notFound().build();
    }
}
