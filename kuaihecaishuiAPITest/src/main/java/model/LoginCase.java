package model;

import lombok.Data;

@Data
public class LoginCase {
    private int id;
    private String username;
    private String password;
    private int expected;
}
