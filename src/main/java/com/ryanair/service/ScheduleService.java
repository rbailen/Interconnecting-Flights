package com.ryanair.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ryanair.domain.Interconnection;
import com.ryanair.domain.Route;
import com.ryanair.domain.Schedule;

public interface ScheduleService {
	Schedule getSchedule(String departure, String arrival, int year, int month);

	Interconnection getFlightsDirect(String departure, String arrival,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime);
	
	Interconnection getFlightsOneStop(String departure, String arrival,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
			List<Route> routes);
}
