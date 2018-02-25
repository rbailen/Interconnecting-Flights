package com.ryanair.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ryanair.config.Config;
import com.ryanair.domain.Route;
import com.ryanair.service.RouteService;

@Service
public class RouteServiceImpl implements RouteService{
	
	@Autowired
	private Config config;

	@Override
	public List<Route> getRoutes() {
		RestTemplate restTemplate = new RestTemplate();
		
		final String uri = config.getRouteServiceURL();
	     
	    ResponseEntity<List<Route>> responseEntity = restTemplate.exchange(
	    		uri, 
	    		HttpMethod.GET, 
	    		null, 
	    		new ParameterizedTypeReference<List<Route>>() {});
		
		List<Route> routes = responseEntity.getBody();
		
		return routes;
	}

}
