package com.example.springboot;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class ActionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionController.class);

    private SqlController sqlController;

    private String queueKey = null;
    private Jedis jedis = null;
    private String hostname = null;
    private int port = 6379;

    @Autowired
    public ActionController(SqlController sqlController) {
        this.sqlController = sqlController;
    }

    public void setConnectionRedis(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.jedis = new Jedis(hostname, port, 0);
    }


    public void listenToRedisAndSaveToDb(String queueKey) {
        boolean firstFlag = true;
        while(true){
            if (jedis != null) {
                if (firstFlag) {
                    LOGGER.info("Redis connected.");
                    firstFlag = false;
                }
                String jsonStr = listenToRedis(queueKey);
                if (jsonStr != null) {
                    String msg = parseMessage(jsonStr);
                    pushToDb(msg);
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                LOGGER.info("Reconnecting redis.");
                setConnectionRedis(this.hostname, this.port);
            }
        }
    }

    private String listenToRedis(String queueKey) {
        this.queueKey = queueKey;
        this.jedis.watch(this.queueKey);
        Transaction tx = this.jedis.multi();
        tx.lpop(queueKey);
        List<Object> j_str = tx.exec();
        if (j_str == null || j_str.isEmpty()) {
            return null;
        } else {
            return (String) j_str.get(0);
        }
    }

    private String parseMessage(String s) {
        JSONParser parser = new JSONParser();
        String message = null;

        try{
            Object obj = parser.parse(s);
            JSONObject jo = (JSONObject) obj;
            message = (String) jo.get("message");
        }catch(ParseException e) {
            System.out.println(e);
        }
        LOGGER.info("Received message: " + message);
        return message;
    }

    private void pushToDb(String msg) {

        this.sqlController.addNewMessage(msg);
    }
}
