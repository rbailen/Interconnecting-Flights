package com.ryanair.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils{
	
	public static final DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter
			.ofPattern("yyyy-MM-dd'T'HH:mm");

	public static LocalDateTime deserialize(String date) {
		return LocalDateTime.parse(date, ISO_LOCAL_DATE_TIME);
	}
	
	public static LocalDateTime formatLocalDateTime(LocalDateTime localDateTime, int day, String time){
		String pattern = "%02d";
		String dayString = String.format(pattern, day);
		String monthString = String.format(pattern, localDateTime.getMonthValue());
		String date = localDateTime.getYear() + "-" + monthString + "-" + dayString + "T" + time;
		return LocalDateTime.parse(date, ISO_LOCAL_DATE_TIME);
	}

}
