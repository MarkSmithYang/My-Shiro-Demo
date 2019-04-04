package com.ddb.eureka.consumer.esclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("eureka-client")
public interface EsClient {

	@GetMapping("es")
	public String consumer();
	
}
