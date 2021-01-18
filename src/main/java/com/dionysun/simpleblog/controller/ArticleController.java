package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.dao.ArticleRepository;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.service.ArticleService;
import com.dionysun.simpleblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @GetMapping("/all/{page}/{size}")
    public ResponseEntity<Page<Article>> findArticleByPage(@PathVariable("page")int page,
                                                           @PathVariable("size")int size,
                                                           @RequestParam(value = "direction", required = false)String dir,
                                                           @RequestParam(value = "sort", required = false) String sort) {
        Sort.Direction direction = Objects.equals(dir, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        sort = ObjectUtils.isEmpty(sort) ? "createdTime" : sort;
        Pageable pageable = PageRequest.of(page, size, direction, sort);
        Page<Article> articlePage = articleService.findAllArticles(pageable);
        return ResponseEntity.ok(articlePage);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Article> findArticleById(@PathVariable Integer articleId){
        Optional<Article> article = articleService.findOne(articleId);
        return ResponseEntity.of(article);
    }

    @PostMapping()
    public ResponseEntity<Article> postArticle(@RequestBody Article article){
        Article save = articleService.addOne(article);
        return ResponseEntity.ok(save);
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<String> updateArticle(@PathVariable Integer articleId, @RequestBody Article article){
        articleService.updateOne(article);
        return ResponseEntity.ok("修改成功");
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer articleId){
        articleService.deleteOne(articleId);
        return ResponseEntity.ok("删除成功");
    }

}
