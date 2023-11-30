package io.shop.exceptions;

public class ImageNotFoundException extends RuntimeException {
    private final Integer id;

    public ImageNotFoundException(Integer id) {
        this.id = id;
    }

    public ImageNotFoundException(String info, Integer id) {
        super(info);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
