package com.ryanair.service;

import java.util.List;

import com.ryanair.domain.Interconnection;

public interface InterconnectionService {
	List<Interconnection> getInterconnections(
			String departure, 
			String arrival, 
			String departureDateTime,
			String arrivalDateTime);
}
