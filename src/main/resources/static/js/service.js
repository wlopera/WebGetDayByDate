MyApp.factory('service', ['$http', function($http) {
	
	function getHttp(uri) {
		return $http.get(uri)      	// URI de llamada	 
	};

	return {
		getHttp : getHttp
	};
	
}]);