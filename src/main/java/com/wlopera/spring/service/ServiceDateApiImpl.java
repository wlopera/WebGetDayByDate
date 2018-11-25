package com.wlopera.spring.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class ServiceDateApiImpl implements ServiceDateApi {

	@Override
	public String getNameDayByDate(int day, int month, int year) {
		
		System.out.println("ServiceDateApiImpl -> getNameDayByDate");
		
		LocalDate date = LocalDate.of(year,month,day);
			
		Locale l = new Locale("es","ES"); 
	    System.out.println(date+ " ==> " + date.getDayOfWeek().getDisplayName(TextStyle.FULL, l).toUpperCase()); 
		
		return date.getDayOfWeek().getDisplayName(TextStyle.FULL, l).toUpperCase();
	}
	
	// Para probar 
	public static void main(String[] arg) {
		new ServiceDateApiImpl().getNameDayByDate(25,11,2018);
	}

}
