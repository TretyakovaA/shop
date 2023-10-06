package io.swagger.exceptions;

public class AdNotFoundException extends RuntimeException {
    private final Integer id;

    public AdNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
