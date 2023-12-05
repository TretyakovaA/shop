package io.shop.repository;

import io.shop.model.Ad;
import io.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Integer> {
    List<Ad> findByAuthor(User author);
}
