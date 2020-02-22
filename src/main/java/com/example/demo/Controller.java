package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    boolean appKilled = false;

    @Autowired
    RestClient restClient;

    @GetMapping("/")
    public String index() {
        return "Hello Java :)";
    }

    @GetMapping("/hello")
    public String hello() throws Exception {
        if (appKilled) {
            throw new Exception("App was killed");
        }
        return "hello";
    }

    @GetMapping("/healthcheck")
    public Map<String, String> health() throws Exception {
        if (appKilled) {
            throw new Exception("App was killed");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("healthy", "true");
        map.put("ip", InetAddress.getLoopbackAddress().getHostAddress());
        return map;
    }

    @GetMapping("/kill")
    public String killApp() throws Exception {
        if (appKilled) {
            throw new Exception("App was killed");
        }
        appKilled = true;
        return "App is killed, healthcheck will throw 500 errors";
    }

    @GetMapping("/checkService")
    public String checkService() {
        return restClient.checkService();
    }

}
