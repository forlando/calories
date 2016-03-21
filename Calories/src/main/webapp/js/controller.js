var caloriesControllers = angular.module('caloriesControllers', []);

caloriesControllers.controller('mainController', ['$scope', '$rootScope', 'loginFactory', function ($scope, $rootScope, Login) {
	$scope.login = function(email) {
		Login.get({param: email}, function(loggedUser) {
			$rootScope.logged = true;
			$rootScope.loggedUser = loggedUser;
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	    $rootScope.showMeals();
    	}, function(error) {
			$scope.logged = false;
			$scope.loggedUser = null;
    	    $scope.error = true;
    	    $scope.errorMessage = (error)?(error.statusText + ": " + error.data):"User not found!";
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	});
    };
    $rootScope.login = $scope.login;

	$scope.exit = function() {
		if ($scope.logged) {
			Login.remove({param: $scope.loggedUser.token}, function() {
				$rootScope.logged = false;
				$rootScope.loggedUser = null;
	    	    $scope.error = false;
	    	    $scope.errorMessage = null;
	    	    $scope.listError = false;
	    	    $scope.listErrorMessage = null;
	    	}, function(error) {
	    	});
		}

		$scope.main();
    };
    $rootScope.exit = $scope.exit;

    $scope.main = function() {
		$scope.loginShow = true;
		$scope.mealsShow = false;
		$scope.usersShow = false;
	    $scope.mealsActive = "";
	    $scope.usersActive = "";
	    $scope.error = false;
	    $scope.errorMessage = null;
	    $scope.listError = false;
	    $scope.listErrorMessage = null;
    };
    $rootScope.main = $scope.main;

	$scope.showMeals = function() {
		$rootScope.loadMeals();

		$scope.loginShow = false;
		$scope.mealsShow = true;
		$scope.usersShow = false;
	    $scope.mealsActive = "active";
	    $scope.usersActive = "";
	    $scope.error = false;
	    $scope.errorMessage = null;
	    $scope.listError = false;
	    $scope.listErrorMessage = null;
    };
    $rootScope.showMeals = $scope.showMeals;

	$scope.showUsers = function() {	    
	    if ($rootScope.logged && $rootScope.loggedUser.role == "Regular") {
	    	$rootScope.editUser($rootScope.loggedUser.email);
		} else if ($rootScope.logged) {
			$rootScope.loadUsers();
		}

		$scope.loginShow = false;
		$scope.mealsShow = false;
		$scope.usersShow = true;
	    $scope.mealsActive = "";
	    $scope.usersActive = "active";
	    $scope.error = false;
	    $scope.errorMessage = null;
	    $scope.listError = false;
	    $scope.listErrorMessage = null;
    };
    $rootScope.showUsers = $scope.showUsers;

    $scope.exit();
}]);

caloriesControllers.controller('mealController', ['$scope', '$rootScope', 'mealFactory', function ($scope, $rootScope, Meal) {
    $scope.saveMeal = function(meal) {
    	if ($rootScope.loggedUser.role != 'Administrator') {
    		meal.email = $rootScope.loggedUser.email;
    	}
    	
    	Meal.save({token: $rootScope.loggedUser.token}, meal, function(meal) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.titleMeal = "New Meal";
    		$scope.meal = undefined;
    		$scope.loadMeals();
    	}, function(error) {
    	    $scope.error = true;
    	    $scope.errorMessage = (error)?(error.statusText + ": " + error.data):"Error saving!";
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	});
    };
    $rootScope.saveMeal = $scope.saveMeal;

    $scope.removeMeal = function(id) {
    	Meal.remove({token: $rootScope.loggedUser.token, id: id}, function() {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.titleMeal = "New Meal";
    		$scope.meal = undefined;
    		$scope.loadMeals();
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = (error)?(error.statusText + ": " + error.data):"Error removing!";
    	});
    };
    $rootScope.removeMeal = $scope.removeMeal;

	$scope.editMeal = function(id) {
		Meal.get({token: $rootScope.loggedUser.token, id: id}, function(meal) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.titleMeal = "Edit Meal";
    		$scope.meal = meal;
    		$scope.meal.date = new Date($scope.meal.date);
    		$scope.meal.time = new Date($scope.meal.time);
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = (error)?(error.statusText + ": " + error.data):"Error getting!";
    	});
	};
    $rootScope.editMeal = $scope.editMeal;

	$scope.loadMeals = function(mealFilter) {
		if (!mealFilter) {
			mealFilter = {};
		}
		
		Meal.query({token: $rootScope.loggedUser.token, fromDate: mealFilter.fromDate, toDate: mealFilter.toDate, fromTime: mealFilter.fromTime, toTime: mealFilter.toTime}, function(meals) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
        	$scope.titleMeal = "New Meal";
    		$scope.meal = undefined;
    		$scope.meals = meals;
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = (error)?(error.statusText + ": " + error.data):"Error filtering!";
    	});
	};
    $rootScope.loadMeals = $scope.loadMeals;
}]);

caloriesControllers.controller('userController', ['$scope', '$rootScope', 'userFactory', function ($scope, $rootScope, User) {
    $scope.saveUser = function(user) {
    	var token = "NEW";
    	
    	if ($rootScope.logged) {
    		toke = $rootScope.loggedUser.token;
    	}

    	User.save({token: token}, user, function(user) {
        	if (!$rootScope.logged) {
        		$rootScope.login(user.email);
        	} else if ($rootScope.logged && $rootScope.loggedUser.role != "Regular") {
        		$rootScope.loadUsers();
    		} else if ($scope.logged) {
    			$rootScope.editUser(user.email);
    		}

    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	}, function(error) {
    	    $scope.error = true;
    	    $scope.errorMessage = (error)?(error.statusText + ": " + error.data):"Error saving!";
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	});
    };
    $rootScope.saveUser = $scope.saveUser;

    $scope.removeUser = function(email) {
    	User.remove({token: $rootScope.loggedUser.token, email: email}, function() {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.titleUser = "New User";
			$scope.user = undefined;
    		$scope.loadUsers();
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = (error)?(error.statusText + ": " + error.data):"Error removing!";
    	});
    };
    $rootScope.removeUser = $scope.removeUser;

	$scope.editUser = function(email) {
		User.get({token: $rootScope.loggedUser.token, email: email}, function(user) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.titleUser = "Edit User";
    		$scope.user = user;
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = (error)?(error.statusText + ": " + error.data):"Error getting!";
    	});
	}
    $rootScope.editUser = $scope.editUser;

	$scope.loadUsers = function() {
		User.query({token: $rootScope.loggedUser.token}, function(users) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.titleUser = "New User";
			$scope.user = undefined;
    		$scope.users = users;
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = (error)?(error.statusText + ": " + error.data):"Error filtering!";
    	});
	}
    $rootScope.loadUsers = $scope.loadUsers;
}]);