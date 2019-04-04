package com.ddb.eureka.client.ec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EcController {

	@Autowired
	 private DiscoveryClient discoveryClient;
	
	@GetMapping("ec")
	public String ec() {
		String services = "services:"+discoveryClient.getServices();
		System.err.println(services);
		return services;
		
	}
	
}
