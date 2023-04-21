package com.mia.server.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() throws Exception {
        int result = RandomUtils.nextInt(3);
        if(1==result){
            System.out.println("error");
            throw new Exception("错误");
        }else{
            return "Hello world,server!";
        }
    }

    @RequestMapping("/api/hello")
    public String apiHello(){
        return "{/api/hello},Hello world,server!";
    }

    @RequestMapping(value = "/feignPost")
    public User post(@RequestBody User user) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(user));

        user.setId(2);
        user.setName("pop");
        return user;
    }


    @RequestMapping("/objRequest")
    public User objRequest(@RequestBody Map<String,Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        User user = gson.fromJson(paramMap.get("user").toString(),User.class);
        CommonParam commonParam = gson.fromJson(paramMap.get("commonParam").toString(),CommonParam.class);
        System.out.println(gson.toJson(user));
        System.out.println(gson.toJson(commonParam));
        user.setId(2);
        user.setName("pop");
        return user;
    }
}
