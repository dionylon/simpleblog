package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.PassToken;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final static int SIZE = 100;
    @Autowired
    private TagService tagService;

    /**
     * 因为tag很少，所以不做排序、分页
     * @return
     */
    @PassToken
    @GetMapping()
    public ResponseEntity<Page<Tag>> findAllTags(){
        Pageable pageable = PageRequest.of(0, SIZE);
        Page<Tag> tagList = tagService.findAll(pageable);
        return ResponseEntity.ok(tagList);
    }

}
