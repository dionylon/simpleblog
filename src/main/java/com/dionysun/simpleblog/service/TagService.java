package com.dionysun.simpleblog.service;


import com.dionysun.simpleblog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    /**
     * 分页查询所有的tag
     */
    Page<Tag> findAll(Pageable pageable);

    /**
     * 删除一个文章的tag
     */
    void deleteOne(Tag tag);

}
