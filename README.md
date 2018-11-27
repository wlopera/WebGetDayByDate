# WebGetDayByDate
Spring - MVC - Spring Boot - Gradle Angular

Crear Aplicación con Spring MVC (ModelAndView), Spring Boot, uso de plantillas Thymeleaf y AngularJS

- Spring MVC - MVC
- Spring Boot
- Gradle 
- Plantillas thymeleaf
- AngularJS

@wlopera/William Lopera - Nov 2018

## Fuente

![captura](https://user-images.githubusercontent.com/7141537/49055142-8dd17a80-f1c4-11e8-9db2-cc083c9a996d.PNG)

**1. build.gradle: Configuración de las tareas del proyecto**

***
```
apply plugin: 'java'
apply plugin: 'maven'

dependencies {
 	compile group: 'org.springframework', name: 'spring-context', version: '5.1.0.RELEASE'  
	
	compile group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: '2.1.0.RELEASE'
	 
	compile group: 'org.springframework', name: 'spring-web', version: '5.1.0.RELEASE'

	compile group: 'org.springframework.cloud', name: 'spring-cloud-starter-config', version: '2.0.1.RELEASE'
    	
	compile group: 'org.springframework.boot', name: 'spring-boot-starter-thymeleaf', version: '2.1.0.RELEASE'
	
	compile group: 'com.fasterxml.jackson.core', name: 'jackson-annotations', version: '2.5.4' 
}

repositories {
	mavenCentral()
}
```
***

**2. application.yml: Archivo de configutración de Spring Boot**

***
```
# Spring properties
spring:
  freemarker:
    enabled: false     # Ignore Eureka dashboard FreeMarker templates
  thymeleaf:
    cache: false       # Allow Thymeleaf templates to be reloaded at runtime
    prefix: classpath:/templates/    # Trailing / mandatory
                       # Template location for this application only

# Map the error path to error template (for Thymeleaf)
error:
  path=/error

# HTTP Server
server:
  port: 8585   # HTTP (Tomcat) port
```
***


**3. ServiceDateApi.java: Servicio espuesto para 'calcular' el día de la semana dado dia/mes/annio**

***
```
package com.wlopera.spring.service;

public interface ServiceDateApi {
	
	String getNameDayByDate(int day, int month, int year);

}
```
***


**4. ServiceDateApiImpl.java: Implementación de servicios**

***
```
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
```
***

**5. Wrapper.java: Clase que empaqueta la respuesta en un Json**

***
```
package com.wlopera.spring.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Wrapper")
public class Wrapper implements Serializable {
	
	private static final long serialVersionUID = -2156793465167257122L;

	private String nameDay;

	public String getNameDay() {
		return nameDay;
	}

	public void setNameDay(String nameDay) {
		this.nameDay = nameDay;
	}

}
```
***

**6. DateController.java: Controlador uso de RestFull y Spring MVC y plantillas HTML**

***
```
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
```
***

**7. MainApp.java: Aplicación principal Spring BOOT**

***
```
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
```
***

**8. service.js: AngularJS para consumo de servicios - Conexión HTTP**

***
```
MyApp.factory('service', ['$http', function($http) {
	
	function getHttp(uri) {
		return $http.get(uri)      	// URI de llamada	 
	};

	return {
		getHttp : getHttp
	};
	
}]);
```
***

**9. App.js: AngularJS para consumo de servicios y control de datos - modelo MVC**

***
```
// Modulo angular
// scope: Alcance de variables 
// hhtp: libreria HTPP consulta de servicios REST
var MyApp = angular.module("MyApp", []);

MyApp.controller("MyController",["$scope", "service", function($scope, service){
    	
	$scope.day = "";	 // día
	$scope.month = "";   // Mes;
	$scope.year = "";    // annio
	$scope.nameDay = ""; // Dia de la semana

	// Consultar dia de la semana [LUNES/MARTES...]
	$scope.getNameDayByDate = function(){
	  getHttp("/date/nameDay/"+$scope.day+"/"+$scope.month+"/"+$scope.year);
	};
	
	function getHttp(uri) {
    	service.getHttp(uri)
    	.then(function(response) {
    		console.log(response);
    		$scope.nameDay = response.data.nameDay;
        }).catch(function(err) {
        	$scope.nameDay = "";
            console.error("Error del servicio consulta http: ", err);
        });
	};  	
}]);
```
***

**10. index.html: HTML principal uso de AngularJS - Vista**

***
```
<!DOCTYPE html>
<html ng-app="MyApp">
<head>
<meta charset="UTF-8"></meta>

<!-- Libreria CCS bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></link>

<!-- Libreria CCS propia del aplicativo -->
<link rel="stylesheet" href="../../css/style.css"></link>

<!-- Libreria Jquery - requerida por boopstrap -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Libreria bootstrap -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- Libreria angularJS -->
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"></script>

<!-- Libreria propia del aplicativo -->
<script src="../../js/app.js"></script>
<script src="../../js/service.js"></script>

<title>Fechas</title>
</head>
<body ng-controller="MyController">
	<h1>Dia de la semana - fecha</h1>
	<hr></hr>
	
  <div class="row">
     <div class="col-sm-4">
     		Día: <input type="text" ng-model="day"><br>
				Mes: <input type="text" ng-model="month"><br>
				Año: <input type="text" ng-model="year"><br>
				<input type="button" value="Que día es?" ng-click="getNameDayByDate()">
   		</div>
   		<div class="col-sm-4">
   			Día de la semana: {{nameDay}}
   		</div>
	</div>
    
</body>
</html>
```
***

**11. date.html: Uso de Thymeleaf - HTML 5**

***
```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8"></meta>
<title>Fecha Actual</title>
</head>

<body>
	<p>
		Fecha actual: <span th:text="${date}"></span>
	</p>
</body>
</html>
```
***

### Salida

![captura1](https://user-images.githubusercontent.com/7141537/48982893-31803500-f0b6-11e8-9182-70b440984c00.PNG)

### En construcción 

![data](https://user-images.githubusercontent.com/7141537/48297627-294fb500-e47b-11e8-9d9c-4b184aefd012.png)


