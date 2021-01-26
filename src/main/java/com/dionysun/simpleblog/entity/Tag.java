package com.dionysun.simpleblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

// 使用lombok生成的toString造成stack overflow
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 40, unique = true)
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<Article> articleList;

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ",name:" + name +
                "}";
    }
}
