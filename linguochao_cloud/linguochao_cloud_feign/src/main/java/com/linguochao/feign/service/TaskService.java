package com.linguochao.feign.service;

import com.linguochao.feign.dao.XcTaskHisRepository;
import com.linguochao.feign.dao.XcTaskRepository;
import com.linguochao.feign.pojo.XcTask;
import com.linguochao.feign.pojo.XcTaskHis;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * description
 *
 * @author linguochao
 * @date 2019\7\6 0006
 */
@Service
public class TaskService {

    @Autowired
    XcTaskRepository xcTaskRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    XcTaskHisRepository xcTaskHisRepository;

    //查询前n条任务
    public List<XcTask> findXcTaskList(Date updateTime, int size){
        //设置分页参数
        Pageable pageable = new PageRequest(0,size);
        //查询前n条任务
        Page<XcTask> all = xcTaskRepository.findByUpdateTimeBefore(pageable, updateTime);
        List<XcTask> list = all.getContent();
        return list;
    }

    //发布消息
    public void publish(XcTask xcTask,String ex,String routingKey){
        Optional<XcTask> optional = xcTaskRepository.findById(xcTask.getId());
        if(optional.isPresent()){
            rabbitTemplate.convertAndSend(ex,routingKey,xcTask);
            //更新任务时间
            XcTask one = optional.get();
            one.setUpdateTime(new Date());
            xcTaskRepository.save(one);
        }
    }

    //获取任务
    @Transactional
    public int getTask(String id,int version){
        //通过乐观锁的方式来更新数据表，如果结果大于0说明取到任务
        int count = xcTaskRepository.updateTaskVersion(id, version);
        return count;
    }

    //完成任务
    @Transactional
    public void finishTask(String taskId){
        Optional<XcTask> optionalXcTask = xcTaskRepository.findById(taskId);
        if(optionalXcTask.isPresent()){
            //当前任务
            XcTask xcTask = optionalXcTask.get();
            //历史任务
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
            xcTaskRepository.delete(xcTask);
        }
    }
}

