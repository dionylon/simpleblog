package com.dionysun.simpleblog.dao;

import com.dionysun.simpleblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findTagByNameEquals(String name);
}
