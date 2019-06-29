package com.linguochao.service;

import com.linguochao.dao.GatheringDao;
import com.linguochao.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * description
 *
 * @author linguochao
 * @date 2019\6\29 0029
 */
@Service
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    /**
     *  key: 给每个对象一个唯一标记key，以便查询时可以识别    # ： 引用方法的参数值
     *  value: 给所有活动缓存对象，起个别名。以便在删除缓存的时候能找到缓存
     */
    @Cacheable(key = "#id",value = "gathering")
    public Gathering findById(String id) {
        return gatheringDao.findById(id).get();
    }

    /**
     * 修改
     * @param gathering
     */
    @CacheEvict(key = "#gathering.id",value = "gathering")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    /**
     * 删除
     * @param id
     */
    @CacheEvict(key = "#id",value = "gathering")
    public void deleteById(String id) {
        gatheringDao.deleteById(id);
    }
}
