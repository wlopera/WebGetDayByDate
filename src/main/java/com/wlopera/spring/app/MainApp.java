package com.wlopera.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.wlopera.spring.controller.DateController;
import com.wlopera.spring.service.ServiceDateApi;
import com.wlopera.spring.service.ServiceDateApiImpl;

@SpringBootApplication
@ComponentScan(useDefaultFilters = false)
public class MainApp {

	
	public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
	
	@Bean
	public ServiceDateApi getServiceDateApi() {
		System.out.println("MainApp -> getServiceDateApi");
		return new ServiceDateApiImpl();
	}

	@Bean
	public DateController getDateController() {
		System.out.println("MainApp -> DateController");
		return new DateController();
	}
	
//	@Bean
//	public DateController getDateController() {
//		System.out.println("MainApp -> DateController - sobrecarga ");
//		return new DateController(getServiceDateApi());
//	}
}