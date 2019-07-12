package com.linguochao.feign.dao;

import com.linguochao.feign.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article,String> {

    /**
     * 搜索
     */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}

