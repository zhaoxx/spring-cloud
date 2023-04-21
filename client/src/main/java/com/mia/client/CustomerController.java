package com.mia.client;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
public class CustomerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IFeignService feignService;

    private static final String applicationName = "single-provider";

    @RequestMapping(value = "commonRequest")
    public String commonRequest(){
        String url = "http://"+ applicationName +"/hello";
        return restTemplate.getForObject(url,String.class);
    }

    @RequestMapping(value = "gatewayForCommonRequest")
    public String gatewayForCommonRequest(){
        String url = "http://172.16.96.197:9999/single-provider/hello";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create("",JSON);
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

    @RequestMapping(value = "feignRequest")
    public String feignRequest(){
        User user = new User(1,"mia",3);
        User result = feignService.post(user);
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @RequestMapping(value = "objRequest")
    public String objRequest(){
        String url = "http://"+ applicationName +"/objRequest";
        User user = new User(1,"mia",3);

        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user", user);

        CommonParam commonParam = new CommonParam();
        commonParam.setRequestId(1111);
        commonParam.setVersion("1.1");
        paramMap.put("commonParam", commonParam);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(paramMap, requestHeaders);
        user = restTemplate.postForObject(url,requestEntity, User.class);
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static final okhttp3.MediaType JSON = okhttp3.MediaType.parse("application/json; charset=utf-8");

    @RequestMapping(value = "gatewayRequest")
    public String gatewayRequest(){
        String url = "http://172.16.96.197:9999/single-provider/objRequest";
        OkHttpClient client = new OkHttpClient();
        User user = new User(1,"mia",3);

        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user", user);

        CommonParam commonParam = new CommonParam();
        commonParam.setRequestId(1111);
        commonParam.setVersion("1.1");
        paramMap.put("commonParam", commonParam);

        Gson gson = new Gson();
        RequestBody body = RequestBody.create(gson.toJson(paramMap),JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        String result = null;
        try {
            Response response = call.execute();
            String responseBody = response.body().string();

            user = gson.fromJson(responseBody,User.class);
            result = gson.toJson(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "sidecar")
    public String sidecar(){
        String url = "http://sidecar/hello";
        return restTemplate.getForObject(url,String.class);
    }
}
