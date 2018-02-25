package com.ryanair.domain;

import java.io.Serializable;
import java.util.List;

public class Day implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int day;
	private List<Flight> flights;
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
}
