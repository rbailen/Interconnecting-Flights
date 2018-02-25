package com.ryanair.config.imp;

import org.springframework.beans.factory.annotation.Value;

import com.ryanair.config.Config;

public class ConfigImpl implements Config{
	
	@Value("${route.service.url}")
    private String routeServiceURL;
	
	@Value("${timetable.service.url}")
	private String timetableServiceURL;

	@Override
	public String getRouteServiceURL() {
		return routeServiceURL;
	}

	@Override
	public String getTimetableServiceURL() {
		return timetableServiceURL;
	}

}
