package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.PassToken;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     *  分页查询所有的tag
     */
    @PassToken
    @GetMapping()
    public ResponseEntity<Page<Tag>> findTags(Pageable pageable){
        Page<Tag> tagList = tagService.findAll(pageable);
        return ResponseEntity.ok(tagList);
    }

    /**
     * 分页查询tag下的所有文章
     */
    @PassToken
    @GetMapping("/{tagName}")
    public ResponseEntity<Page<Article>> findArticleByTags(@PathVariable("tagName")String tagName,
                                                       Pageable pageable){
        Page<Article> articlePage = tagService.findArticlesByTag(tagName, pageable);
        return ResponseEntity.ok(articlePage);
    }

}
