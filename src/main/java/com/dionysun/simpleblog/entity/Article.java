package com.dionysun.simpleblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_time")
    @CreationTimestamp
    private Date createdTime;

    @ManyToMany
    @JsonIgnoreProperties(value = {"articleList", "id"})
    @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tagList;
}
