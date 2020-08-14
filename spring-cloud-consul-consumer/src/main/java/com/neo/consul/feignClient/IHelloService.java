package com.neo.consul.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "service-producer")
@Repository
public interface IHelloService {

    @RequestMapping(value = "/service-producer/hello")
    String hello();
}
