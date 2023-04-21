package com.mia.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "single-provider")
public interface IFeignService {

    @RequestMapping(value = "/feignPost", method = RequestMethod.POST)
    User post(@RequestBody User user);
}