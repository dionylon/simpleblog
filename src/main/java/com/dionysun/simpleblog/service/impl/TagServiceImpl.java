package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.dao.TagRepository;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("TagServiceImpl")
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public void deleteOne(Tag tag) {
        tagRepository.delete(tag);
    }
}
