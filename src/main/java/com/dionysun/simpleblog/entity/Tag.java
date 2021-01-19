package com.dionysun.simpleblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 40)
    private String name;

    @JsonIgnoreProperties(value = {"tagList"})
    @ManyToMany(mappedBy = "tagList")
    private List<Article> articleList;

    public Tag(String name) {
        this.name = name;
    }
}
