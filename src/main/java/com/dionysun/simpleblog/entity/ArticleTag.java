package com.dionysun.simpleblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "article_tag")
@NoArgsConstructor
@IdClass(ArticleTagId.class)
public class ArticleTag {
    @Id
    @Column(name = "article_id", nullable = false)
    @JsonIgnoreProperties(value = {"articleList", "tagList"})
    private Integer articleId;
    @Id
    @Column(name = "tag_id", nullable = false)
    @JsonIgnoreProperties(value = {"articleList", "tagList"})
    private  Integer tagId;

    public ArticleTag(Integer articleId, Integer tagId){
        this.articleId = articleId;
        this.tagId = tagId;
    }

}
