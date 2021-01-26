package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.PassToken;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 分页查询文章
     * @param pageable {
     *     "page" : 0,
     *      "size" : 10,
     *      "sort" : "createdTime,...,title, desc | asc"
     * }
     * @return 分页结果
     */
    @PassToken
    @GetMapping()
    public ResponseEntity<Page<Article>> findArticleByPage(Pageable pageable) {
        Page<Article> articlePage = articleService.findAllArticles(pageable);
        return ResponseEntity.ok(articlePage);
    }

    /**
     * 查询{articleId}对应的文章
     */
    @PassToken
    @GetMapping("/{articleId}")
    public ResponseEntity<Article> findArticleById(@PathVariable Integer articleId){
        Optional<Article> article = articleService.findOne(articleId);
        return ResponseEntity.of(article);
    }

    /**
     * 上传文章
     */
    @PostMapping("")
    public ResponseEntity<Article> postArticle(@RequestBody Article article){
        Article save = articleService.addOne(article);
        return ResponseEntity.ok(save);
    }

    /**
     * 更新id为{articleId}文章
     */
    @PostMapping("/{articleId}")
    public ResponseEntity<String> updateArticle(@PathVariable Integer articleId, @RequestBody Article article){
        article.setId(articleId);
        articleService.updateOne(article);
        return ResponseEntity.ok("修改成功");
    }

    /**
     * 删除id为{articleId}的文章
     */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteById(@PathVariable Integer articleId){
        articleService.deleteOne(articleId);
        return ResponseEntity.ok("删除成功");
    }

}
