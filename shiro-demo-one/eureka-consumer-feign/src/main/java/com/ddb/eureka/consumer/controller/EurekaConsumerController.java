package com.ddb.eureka.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ddb.eureka.consumer.esclient.EsClient;


@RestController
public class EurekaConsumerController {

	@Autowired
	private EsClient esClient;
	
	@GetMapping("consumer")
	public String es() {
		return esClient.consumer();
	}
	
}
