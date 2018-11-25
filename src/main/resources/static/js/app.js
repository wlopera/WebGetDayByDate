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