package io.shop.exceptions;

public class UserNotFoundException extends RuntimeException {
    private final Integer id;

    public UserNotFoundException(Integer id) {
        this.id = id;
    }

    public UserNotFoundException(String info) {
        super(info);
        id = -1;
    }

    public Integer getId() {
        return id;
    }
}
