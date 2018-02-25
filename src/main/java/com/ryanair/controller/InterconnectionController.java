package com.ryanair.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryanair.domain.Interconnection;
import com.ryanair.service.InterconnectionService;

@RestController
public class InterconnectionController {

	@Autowired
	private InterconnectionService service;
	
	@GetMapping("/interconnections")
	public List<Interconnection> getInterconnections(
			@RequestParam(value = "departure") String departure,
			@RequestParam(value = "arrival") String arrival,
			@RequestParam(value = "departureDateTime") String departureDateTime,
			@RequestParam(value = "arrivalDateTime") String arrivalDateTime){
		
		return this.service.getInterconnections(departure, arrival, departureDateTime, arrivalDateTime);
	}
}
