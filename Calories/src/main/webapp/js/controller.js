var caloriesControllers = angular.module('caloriesControllers', []);

caloriesControllers.controller('userController', ['$scope', 'userFactory', function ($scope, User) {
    $scope.add = function(user) {
    	User.save(user, function() { $scope.users = User.query(); });
    };

    $scope.remove = function(login) {
    	User.remove(login, function() { $scope.users = User.query(); });
    }

    $scope.users = User.query();
}]);

caloriesControllers.controller('mealController', ['$scope', 'mealFactory', function ($scope, Meal) {
    $scope.save = function(meal) {
    	Meal.save(meal, function() { $scope.meal.id = null; $scope.title = "New Meal"; $scope.meals = Meal.query(); });
    };

    $scope.edit = function(id) {
    	Meal.get({id: id}, function(meal) { $scope.meal = meal; $scope.title = "Edit Meal"; });
    };

    $scope.remove = function(id) {
    	Meal.remove(id, function() { $scope.meals = Meal.query(); });
    };

    $scope.title = "New Meal";
    $scope.meals = Meal.query();
}]);