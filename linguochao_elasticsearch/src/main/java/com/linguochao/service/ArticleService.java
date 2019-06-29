package com.linguochao.service;

import com.linguochao.dao.ArticleDao;
import com.linguochao.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 搜索
     */
    public Page<Article> search(String keyworkd, int page, int size){
        /**
         * 命名查询+分页
         */
        return articleDao.findByTitleOrContentLike(keyworkd,keyworkd, PageRequest.of(page-1,size));
    }

}

