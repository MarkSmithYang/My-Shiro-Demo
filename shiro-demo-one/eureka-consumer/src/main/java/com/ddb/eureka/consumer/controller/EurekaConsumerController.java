package com.ddb.eureka.consumer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class EurekaConsumerController implements SecurityContextRepository{

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@GetMapping("consumer")
	public String es() {
		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
		String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/ec";
		String object = restTemplate.getForObject(url, String.class);
		System.err.println(object);
		return "Ok";
	}

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
