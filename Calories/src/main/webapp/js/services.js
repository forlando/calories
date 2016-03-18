var services = angular.module('caloriesServices', ['ngResource']);

services.factory('loginFactory', ['$resource', function($resource) {
	return $resource('rest/login/:param');
}]);

services.factory('userFactory', ['$resource', function($resource) {
	return $resource('rest/users/:token/:email');
}]);

services.factory('mealFactory', ['$resource', function($resource) {
	return $resource('rest/meals/:token/:id');
}]);