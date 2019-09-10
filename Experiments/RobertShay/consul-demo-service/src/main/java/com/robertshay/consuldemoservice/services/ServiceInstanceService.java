package com.robertshay.consuldemoservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

@Service
public class ServiceInstanceService {

    private DiscoveryClient discoveryClient;

    @Autowired
    public ServiceInstanceService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public String getServiceInstanceUri(String serviceId, String path) {
        return getServiceInstance(serviceId).getUri().toString() + path;
    }

    public ServiceInstance getServiceInstance(String serviceId) {
        return discoveryClient.getInstances(serviceId).get(0);
    }
}
