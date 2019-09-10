package com.robertshay.consuldemoservice.resources;

import com.robertshay.consuldemoservice.models.Model;
import com.robertshay.consuldemoservice.services.ServiceInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestResource {

    RestTemplate restTemplate;
    ServiceInstanceService serviceInstanceService;

    @Autowired
    public TestResource(RestTemplate restTemplate, ServiceInstanceService serviceInstanceService) {
        this.restTemplate = restTemplate;
        this.serviceInstanceService = serviceInstanceService;
    }

    @RequestMapping("/test")
    public Model test() {
        String url = serviceInstanceService.getServiceInstanceUri("consul-demo", "/hello");

        return restTemplate.getForObject(url, Model.class);
    }

}
