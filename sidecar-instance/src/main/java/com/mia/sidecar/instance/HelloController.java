package com.mia.sidecar.instance;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @RequestMapping(value="/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public String health(){
        return "{\"status\":\"UP\"}";
    }

    @RequestMapping("/actuator/health")
    public void health(HttpServletResponse response) throws Exception{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String,String> map = new HashMap<>();
        map.put("status","UP");
        JSONObject jsonObject = new JSONObject();
        try{
            response.getWriter().write(jsonObject.toJSONString(map));
        }finally {
            response.getWriter().close();
        }
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello world,sidecar instance!";
    }

    @RequestMapping(value = "sidecarRequest")
    public String sidecarRequest(){
        String url = "http://localhost:9898/single-provider/hello";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(null,MediaType.APPLICATION_JSON_VALUE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        String result = null;
        try {
            Response response = call.execute();
            result = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
