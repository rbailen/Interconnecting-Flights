package com.ryanair.domain;

import java.io.Serializable;
import java.util.List;

public class Interconnection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int stops;
	private List<Leg> legs;
	
	public int getStops() {
		return stops;
	}
	public void setStops(int stops) {
		this.stops = stops;
	}
	public List<Leg> getLegs() {
		return legs;
	}
	public void setLegs(List<Leg> legs) {
		this.legs = legs;
	}
	
}
