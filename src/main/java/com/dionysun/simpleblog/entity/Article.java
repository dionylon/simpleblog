package com.dionysun.simpleblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "is_hidden")
    private boolean isHidden;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_time")
    @CreationTimestamp
    private Date createdTime;

    @ManyToMany(mappedBy = "articleList")
    @JsonIgnoreProperties(value = {"articleList", "id"})
    private Set<Tag> tagList;
}
