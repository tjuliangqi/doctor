package cn.tju.doctor.controller;

import cn.tju.doctor.dao.MessageMapper;
import cn.tju.doctor.daomain.Message;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController

@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageMapper messageMapper;
    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    public RetResult<String> addMessage(@RequestBody Message message) {
        //RetResult retResult = new RetResult();
        String uuid = UUID.randomUUID().toString();
        message.setUuid(uuid);
        message.setCreateuser("admin");
        messageMapper.insertMessage(message);
        return RetResponse.makeOKRsp("ok");
    }

    @RequestMapping(value = "/get5Message", method = RequestMethod.POST)
    public RetResult<List<Message>> get5Message() {
        //RetResult retResult = new RetResult();
        List<Message> list = messageMapper.get5Message("admin");
        return RetResponse.makeOKRsp(list);
    }

    @RequestMapping(value = "/getAllMessage", method = RequestMethod.POST)
    public RetResult<List<Message>> getAllMessage() {
        //RetResult retResult = new RetResult();
        List<Message> list = messageMapper.getAllMessage("admin");
        return RetResponse.makeOKRsp(list);
    }
}
