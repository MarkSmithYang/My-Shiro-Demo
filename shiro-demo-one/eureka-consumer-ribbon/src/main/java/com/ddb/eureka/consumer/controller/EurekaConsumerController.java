package com.ddb.eureka.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class EurekaConsumerController {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/consumer")
	public String es() {
		String object = restTemplate.getForObject("http://eureka-client/ec", String.class);
		System.err.println(object);
		return "Ok";
	}
	
}
