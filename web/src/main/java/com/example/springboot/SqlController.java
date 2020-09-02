package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.dao.DataAccessException;
import java.sql.Timestamp;


@Controller
@RequestMapping(path="/demo")
public class SqlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlController.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public SqlController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewMessage (@RequestParam String message) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Message m = new Message();
        m.setMessage(message);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        m.setCreatedAt(timestamp);
        String result = null;
        try {
            messageRepository.save(m);
            LOGGER.info("Messaged '" + m.getMessage() + "'saved!");
            result = "Saved";
        } catch (DataAccessException de) {
            LOGGER.info("Messaged '" + m.getMessage() + "' NOT saved! " + de.toString());
            result = "NOT Saved";
        }
        return result;
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Message> getAllMessages() {
        // This returns a JSON or XML with the message
        return messageRepository.findAll();
    }
}