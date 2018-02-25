package com.ryanair.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ryanair.config.Config;
import com.ryanair.domain.Day;
import com.ryanair.domain.Flight;
import com.ryanair.domain.Interconnection;
import com.ryanair.domain.Leg;
import com.ryanair.domain.Route;
import com.ryanair.domain.Schedule;
import com.ryanair.service.ScheduleService;
import com.ryanair.utils.DateUtils;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	@Autowired
	private Config config;

	@Override
	public Schedule getSchedule(String departure, String arrival, int year,
			int month) {
		RestTemplate restTemplate = new RestTemplate();
		
		final String uri = config.getTimetableServiceURL();
	     
	    ResponseEntity<Schedule> responseEntity = restTemplate.exchange(
	    		uri, 
	    		HttpMethod.GET, 
	    		null, 
	    		new ParameterizedTypeReference<Schedule>() {},
	    		departure,
	    		arrival,
	    		year,
	    		month);
		
		Schedule schedule = responseEntity.getBody();
		
		return schedule;
	}

	@Override
	public Interconnection getFlightsDirect(String departure, String arrival,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
		
		Interconnection interconnection = new Interconnection();
		List<Leg> legs = new ArrayList<Leg>();

		if(departureDateTime.isBefore(arrivalDateTime)){
			try{
				Schedule schedule = this.getSchedule(departure, arrival,
						departureDateTime.getYear(), departureDateTime.getMonthValue());
		
					if (!schedule.getDays().isEmpty()) {
						for (Day day : schedule.getDays()) {
							for (Flight flight : day.getFlights()) {
								
								LocalDateTime flightDepartureDateTime = DateUtils.formatLocalDateTime(departureDateTime, day.getDay(), flight.getDepartureTime());
								LocalDateTime flightArrivalDateTime = DateUtils.formatLocalDateTime(arrivalDateTime, day.getDay(), flight.getArrivalTime());
								
								if (departureDateTime.isBefore(flightDepartureDateTime)
										&& arrivalDateTime.isAfter(flightArrivalDateTime)) {
										Leg leg = new Leg();
										leg.setArrivalAirport(arrival);
										leg.setArrivalDateTime(flightArrivalDateTime.toString());
										leg.setDepartureAirport(departure);
										leg.setDepartureDateTime(flightDepartureDateTime.toString());
										
										legs.add(leg);
										
										interconnection.setLegs(legs);
								}
							}
						}
					}
				
			}catch(Exception e){
				log.debug("No schedule for " + departure + " to " + arrival);
			}
		}

		return interconnection;
	}

	@Override
	public Interconnection getFlightsOneStop(String departure, String arrival,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
			List<Route> routes) {
		Interconnection interconnection = new Interconnection();
		List<Leg> legs = new ArrayList<Leg>();
		List<Interconnection> departureConnections = new ArrayList<Interconnection>();
		List<Interconnection> connectionArrivals = new ArrayList<Interconnection>();
		
		List<String> connectedAirports = getConnectedAirports(routes, departure, arrival).get(departure);
		
		for(String airportCandidate: connectedAirports){
			Interconnection departureConnection = getFlightsDirect(departure, airportCandidate, departureDateTime, arrivalDateTime);
			departureConnections.add(departureConnection);
				
			if(departureConnection != null && departureConnection.getLegs() != null && !departureConnection.getLegs().isEmpty()){
				legs.addAll(departureConnection.getLegs());
			}
				
			Interconnection connectionArrival = getFlightsDirect(airportCandidate, arrival, departureDateTime.plusHours(2), arrivalDateTime);
			connectionArrivals.add(connectionArrival);
			
			if(connectionArrival != null && connectionArrival.getLegs() != null && !connectionArrival.getLegs().isEmpty()){
				legs.addAll(connectionArrival.getLegs());
			}
		}
		
		interconnection.setLegs(legs);
		
		return interconnection;
	}
	
	private Map<String, List<String>> getConnectedAirports(List<Route> routes, String departure, String arrival){

		Map<String, Set<String>> airportsConnections = new HashMap<>();
		Set<String> connections = new HashSet<String>();
		Map<String, List<String>> airportsConnectionsReturn = new HashMap<>();
		List<String> listArrivals  = new ArrayList<String>();
		for(Route route: routes){
			connections = airportsConnections.get(route.getAirportFrom());

			if (connections == null) {
				connections = new HashSet<>();
			}

			connections.add(route.getAirportTo());
			airportsConnections.put(route.getAirportFrom(), connections);
		}
		
		connections = new HashSet<>();
		
		for(Map.Entry<String, Set<String>> connection: airportsConnections.entrySet()){
			if(connection.getKey().equals(departure)){
				connections.addAll(connection.getValue());
				
				for(String newArrival: connections){
					Set<String> arrivals = airportsConnections.get(newArrival);
					if(arrivals.contains(arrival)){
						listArrivals.add(newArrival);
					}
					airportsConnectionsReturn.put(connection.getKey(), listArrivals);
				}
			}
		}
		
		return airportsConnectionsReturn;
	}

}
