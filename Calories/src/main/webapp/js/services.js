var services = angular.module('caloriesServices', ['ngResource']);

services.factory('userFactory', ['$resource', function($resource) {
	return $resource('rest/users/:login');
}]);

services.factory('mealFactory', ['$resource', function($resource) {
	return $resource('rest/meals/:id');
}]);