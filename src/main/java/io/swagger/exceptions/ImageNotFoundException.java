package io.swagger.exceptions;

public class ImageNotFoundException extends RuntimeException {
    private final Integer id;

    public ImageNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
