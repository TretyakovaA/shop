package io.swagger.exceptions;

public class UserNotFoundException extends RuntimeException{
    private final Integer id;

    public UserNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
