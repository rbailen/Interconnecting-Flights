package com.ryanair.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ryanair.domain.Interconnection;
import com.ryanair.domain.Route;
import com.ryanair.service.InterconnectionService;
import com.ryanair.service.RouteService;
import com.ryanair.service.ScheduleService;
import com.ryanair.utils.DateUtils;

@Service
public class InterconnectionServiceImpl implements InterconnectionService{

	@Autowired
	private InterconnectionService interconnectionService;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private ScheduleService scheduleService;

	@Override
	public List<Interconnection> getInterconnections(String departure,
			String arrival, String departureDateTime, String arrivalDateTime) {
		
		List<Interconnection> interconnections = new ArrayList<Interconnection>();
		
		LocalDateTime localDepartureDateTime = DateUtils.deserialize(departureDateTime);
		LocalDateTime localArrivalDateTime = DateUtils.deserialize(arrivalDateTime);
		
		if(localDepartureDateTime.isBefore(localArrivalDateTime)){
		
			/* Get flights direct */
			Interconnection flightsDirect = scheduleService.getFlightsDirect(departure, arrival, localDepartureDateTime, localArrivalDateTime);
			flightsDirect.setStops(0);
			interconnections.add(flightsDirect);
			
			/* Get flights one stop */
			List<Route> routes = routeService.getRoutes();
			Interconnection flightsOneStop = scheduleService.getFlightsOneStop(departure,arrival, localDepartureDateTime, 
					localArrivalDateTime, routes);
			flightsOneStop.setStops(1);
			interconnections.add(flightsOneStop);

		}
		
		return interconnections;
	}
	
	
}
