var caloriesControllers = angular.module('caloriesControllers', []);

caloriesControllers.controller('userController', ['$scope', 'userFactory', function ($scope, User) {
    $scope.users = User.query();
}]);

caloriesControllers.controller('mealController', ['$scope', 'mealFactory', function ($scope, Meal) {
    $scope.clear = function() {
        $scope.user = [];
    };

    $scope.save = function(meal) {
    	Meal.save(meal);
    };

    $scope.meals = Meal.query();
}]);