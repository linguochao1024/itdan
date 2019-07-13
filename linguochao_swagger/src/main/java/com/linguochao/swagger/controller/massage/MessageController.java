package com.linguochao.swagger.controller.massage;

import com.linguochao.swagger.config.BaseResult;
import com.linguochao.swagger.model.Message;
import com.linguochao.swagger.repository.MessageRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 消息操作 API
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */
@Api(value = "消息", description = "消息操作 API", position = 100, protocols = "http")
@RestController
@RequestMapping("/")
public class MessageController extends BaseResult {

    @Autowired
    private MessageRepository messageRepository;

    @ApiOperation(value = "消息列表", notes = "完整的消息内容列表")
    @GetMapping(value = "messages")
    public BaseResult<List<Message>> list() {
        List<Message> messages = this.messageRepository.findAll();
        return success(messages);
    }

    @ApiOperation(value = "添加消息", notes = "根据参数创建消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息 ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "text", value = "正文", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "summary", value = "摘要", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "message")
    public BaseResult<Message> create(Message message) {
       System.out.println("message====" + message.toString());
        message = this.messageRepository.save(message);
        return success(message);
    }

    @ApiOperation(value = "修改消息", notes = "根据参数修改消息")
    @PutMapping(value = "message")
    @ApiResponses({
            @ApiResponse(code = 100, message = "请求参数有误"),
            @ApiResponse(code = 101, message = "未授权"),
            @ApiResponse(code = 103, message = "禁止访问"),
            @ApiResponse(code = 104, message = "请求路径不存在"),
            @ApiResponse(code = 200, message = "服务器内部错误")
    })
    public Message modify(Message message) {
        Message messageResult = this.messageRepository.update(message);
        return messageResult;
    }

    @ApiOperation(value = "修改消息", notes = "根据参数修改消息")
    @PatchMapping(value = "/message/text")
    public BaseResult<Message> patch(Message message) {
        Message messageResult = this.messageRepository.updateText(message);
        return success(messageResult);
    }
    @ApiOperation(value = "获取消息", notes = "根据id获取消息")
    @GetMapping(value = "message/{id}")
    public BaseResult<Message> get(@PathVariable Long id) {
        Message message = this.messageRepository.findMessage(id);
        return success(message);
    }

    @ApiOperation(value = "删除消息", notes = "根据id删除消息")
    @DeleteMapping(value = "message/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.messageRepository.deleteMessage(id);
    }

}

