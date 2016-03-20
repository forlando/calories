var caloriesControllers = angular.module('caloriesControllers', []);

caloriesControllers.controller('mainController', ['$scope', 'loginFactory', function ($scope, Login) {
	$scope.login = function(email) {
		Login.get({param: email}, function(loggedUser) {
    		$scope.logged = true;
    		$scope.loggedUser = loggedUser;
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.meals();
    	}, function(error) {
			$scope.logged = false;
			$scope.loggedUser = null;
    	    $scope.error = true;
    	    $scope.errorMessage = "User not found!";
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	});
    };

	$scope.exit = function() {
		if ($scope.logged) {
			Login.remove({param: $scope.loggedUser.token}, function() {
				$scope.logged = false;
				$scope.loggedUser = null;
	    	    $scope.error = false;
	    	    $scope.errorMessage = null;
	    	    $scope.listError = false;
	    	    $scope.listErrorMessage = null;
	    	}, function(error) {
	    	});
		}

		$scope.main();
    };

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
    }

	$scope.meals = function() {
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

	$scope.users = function(newUser) {
		$scope.newUser = newUser;
		$scope.loginShow = false;
		$scope.mealsShow = false;
		$scope.usersShow = true;
	    $scope.mealsActive = "";
	    $scope.usersActive = "active";
	    $scope.error = false;
	    $scope.errorMessage = null;
	    $scope.listError = false;
	    $scope.listErrorMessage = null;

	    if ($scope.logged && $scope.loggedUser.role == "Regular") {
	    	$scope.user = $scope.loggedUser;
			$scope.titleUser = "Edit User";
		}
    };

    $scope.exit();
}]);

caloriesControllers.controller('mealController', ['$scope', 'mealFactory', function ($scope, Meal) {
    $scope.saveMeal = function(meal) {
    	if ($scope.loggedUser.role != 'Administrator') {
    		meal.email = $scope.loggedUser.email;
    	}
    	
    	Meal.save({token: $scope.loggedUser.token}, meal, function(meal) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.meal = undefined;
    		$scope.titleMeal = "New Meal";
    		$scope.queryMeal();
    	}, function(error) {
    	    $scope.error = true;
    	    $scope.errorMessage = "Error saving!";
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	});
    };

    $scope.removeMeal = function(id) {
    	Meal.remove({token: $scope.loggedUser.token, id: id}, function() {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.queryMeal();
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = "Error removing!";
    	});
    };

	$scope.getMeal = function(id) {
		Meal.get({token: $scope.loggedUser.token, id: id}, function(meal) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.meal = meal;
    		$scope.meal.date = new Date($scope.meal.date);
    		$scope.meal.time = new Date($scope.meal.time);
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = "Error getting!";
    	});
	}

	$scope.queryMeal = function(mealFilter) {
		if (!mealFilter) {
			mealFilter = {};
		}
		
		Meal.query({token: $scope.loggedUser.token, fromDate: mealFilter.fromDate, toDate: mealFilter.toDate, fromTime: mealFilter.fromTime, toTime: mealFilter.toTime}, function(meals) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.meals = meals;
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = "Error filtering!";
    	});
	}

    $scope.editMeal = function(id) {
    	$scope.titleMeal = "Edit Meal";
    	$scope.getMeal(id);
    };

    $scope.loadMeals = function(mealFilter) {
    	$scope.titleMeal = "New Meal";
    	$scope.queryMeal(mealFilter);
    };
}]);

caloriesControllers.controller('userController', ['$scope', 'userFactory', function ($scope, User) {
    $scope.saveUser = function(user) {
    	if (!$scope.logged) {
    		$scope.loggedUser = user;
    		$scope.loggedUser.token = "NEW";
    	}

    	User.save({token: $scope.loggedUser.token}, user, function(user) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;

        	if (!$scope.logged) {
        		$scope.login(user.email);
        	}

    		if ($scope.logged && $scope.loggedUser.role != "Regular") {
    			$scope.user = undefined;
        		$scope.titleUser = "New User";
        		$scope.queryUser();
    		} else {
        		$scope.user = user;
    			$scope.titleUser = "Edit User";
    		}
    	}, function(error) {
    	    $scope.error = true;
    	    $scope.errorMessage = "Error saving!";
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    	});
    };

    $scope.removeUser = function(email) {
    	User.remove({token: $scope.loggedUser.token, email: email}, function() {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.queryUser();
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = "Error removing!";
    	});
    };

	$scope.getUser = function(email) {
		User.get({token: $scope.loggedUser.token, email: email}, function(user) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.user = user;
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = "Error getting!";
    	});
	}

	$scope.queryUser = function() {
		User.query({token: $scope.loggedUser.token}, function(users) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = false;
    	    $scope.listErrorMessage = null;
    		$scope.users = users;
    	}, function(error) {
    	    $scope.error = false;
    	    $scope.errorMessage = null;
    	    $scope.listError = true;
    	    $scope.listErrorMessage = "Error filtering!";
    	});
	}

    $scope.editUser = function(email) {
    	$scope.titleUser = "Edit User";
    	$scope.getUser(email);
    };

    $scope.loadUsers = function() {
    	$scope.titleUser = "New User";
    	$scope.queryUser();
    };
}]);