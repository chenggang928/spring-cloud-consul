package com.neo.consul.web;

import com.neo.consul.feignClient.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CallHelloController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private IHelloService iHelloService;

    private final static String SERVICE_NAME = "service-producer";

    @RequestMapping("/call")
    public String call() {
        ServiceInstance serviceInstance = loadBalancer.choose(SERVICE_NAME);
        System.out.println("服务地址：" + serviceInstance.getUri());
        System.out.println("服务名称：" + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() +SERVICE_NAME+ "/hello",
                String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }

    @RequestMapping("/feign")
    public Object feign(){
        String s = iHelloService.hello();
        return s;
    }



}