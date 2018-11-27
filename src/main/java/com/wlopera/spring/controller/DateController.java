package com.wlopera.spring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wlopera.spring.service.ServiceDateApi;

@Controller
public class DateController {

	@Autowired
	ServiceDateApi service;

//	public DateController(ServiceDateApi service) {
//		System.out.println("DateController -> DateController");
//		this.service = service;
//	}

	// http://localhost:8585
	@RequestMapping("/")
	public String home() {
		System.out.println("DateController -> home");
		return "index";
	}

	// http://localhost:8585/date/nameDay
	@RequestMapping(value = "/date/nameDay/{day}/{month}/{year}", method = RequestMethod.GET)
	@ResponseBody
	public Wrapper getNameDayByDate(@PathVariable("day") String day, @PathVariable("month") String month,
			@PathVariable("year") String year) {

		System.out.println("DateController -> getNameDayByDate");

		Wrapper wrapper = new Wrapper();

		wrapper.setNameDay(
				service.getNameDayByDate(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year)));

		return wrapper;

	}

	// http://localhost:8585/date/now
	@RequestMapping(value = "/date/now", method = RequestMethod.GET)
	public ModelAndView getDateNow() {

		System.out.println("DateController -> getDateNow");

		ModelAndView mav = new ModelAndView("date");
		
		//Fecha actual - formateada
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
		mav.addObject("date", now.format(formatter));
		
		return mav;

	}

}
