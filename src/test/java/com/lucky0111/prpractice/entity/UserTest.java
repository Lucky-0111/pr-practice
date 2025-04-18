package com.lucky0111.prpractice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
    }

    @Test
    void testParameterizedConstructor() {
        String username = "testUser";
        String email = "test@example.com";
        User user = new User(username, email);

        assertNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testGettersAndSetters() {
        User user = new User();

        Long id = 1L;
        String username = "testUser";
        String email = "test@example.com";

        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User("user1", "user1@example.com");
        user1.setId(1L);

        User user2 = new User("user1", "user1@example.com");
        user2.setId(1L);

        User user3 = new User("user2", "user2@example.com");
        user3.setId(2L);

        // Test equals
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        // Test hashCode
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        User user = new User("testUser", "test@example.com");
        user.setId(1L);

        String toString = user.toString();

        // Verify toString contains all field values
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("username=testUser"));
        assertTrue(toString.contains("email=test@example.com"));
    }
}
