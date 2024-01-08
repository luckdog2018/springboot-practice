package com.lyon;

import com.lyon.ws.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @title WebsocketApplication
 * @author lyon
 * @createTime 2022年09月29日 10:53
 * @updateTime
 * @version 1.0.0
 */
@SpringBootApplication
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);

        WebSocketServer server = new WebSocketServer();
        server.start();
    }
}
