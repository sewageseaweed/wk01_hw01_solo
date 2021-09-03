package com.example.wk01_hw01_solo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class LoginUnitTest {

    @Test
    public void incorrectUser() {
        ArrayList users = new ArrayList<>();
        User newUser = new User("Samantha", "Clementine Bauch", 3);
        users.add(newUser);
        assertEquals(false, LoginActivity.checkUsername(users, "Samantha1"));
    }

    @Test
    public void incorrectPass(){
        ArrayList users = new ArrayList<>();
        User newUser = new User("Samantha", "Clementine Bauch", 3);
        users.add(newUser);
        assertEquals(false, LoginActivity.checkPassword("Samantha1"));
    }

    @Test
    public void correctUser() {
        ArrayList users = new ArrayList<>();
        User newUser = new User("Samantha", "Clementine Bauch", 3);
        users.add(newUser);
        assertEquals(true, LoginActivity.checkUsername(users, "Samantha"));
    }

    @Test
    public void correctPass() {
        ArrayList users = new ArrayList<>();
        User newUser = new User("Samantha", "Clementine Bauch", 3);
        users.add(newUser);
        assertEquals(true, LoginActivity.checkPassword("Password123"));
    }
}
