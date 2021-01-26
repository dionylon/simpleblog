package com.dionysun.simpleblog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleTagId implements Serializable {
    private Integer articleId;
    private Integer tagId;
}
