package com.lucky0111.prpractice.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    void testUserEntity() {
        // 유저 엔티티 생성
        User user = new User("john_doe", "john.doe@example.com");

        // 유저 값 검증
        assertThat(user.getUsername()).isEqualTo("john_doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testSettersAndGetters() {
        // 유저 엔티티 생성
        User user = new User();

        // 값을 설정
        user.setUsername("jane_doe");
        user.setEmail("jane.doe@example.com");

        // 설정된 값 검증
        assertThat(user.getUsername()).isEqualTo("jane_doe");
        assertThat(user.getEmail()).isEqualTo("jane.doe@example.com");
    }

    @Test
    void testEmptyUser() {
        // 빈 유저 엔티티 생성
        User user = new User();

        // 기본값 검증
        assertThat(user.getUsername()).isNull();
        assertThat(user.getEmail()).isNull();
    }

    @Test
    void testUserConstructor() {
        // 생성자에서 직접 값을 설정하여 유저 객체 생성
        User user = new User("alice_smith", "alice.smith@example.com");

        // 생성자에서 설정한 값 검증
        assertThat(user.getUsername()).isEqualTo("alice_smith");
        assertThat(user.getEmail()).isEqualTo("alice.smith@example.com");
    }
}
