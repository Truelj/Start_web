angular.module('maintenance', ['ngRoute'])
  .controller('adminCtrl', AdminCtrl)
  .controller('mainCtrl', MainCtrl)
  .controller('locationsCtrl', LocationsCtrl)
  .controller('sitesCtrl', SitesCtrl)
  .factory('currentSpot', currentSpot)//a funciton of Angular module. For registering a service in the module
  .config(function ($routeProvider) {//$routeProvider maps a URL to a view(route)
    $routeProvider.when('/locations', { //route name, and route table entry
      templateUrl: 'views/locations.html',
      controller: 'locationsCtrl'
    });
    $routeProvider.when('/sites', {
      templateUrl: 'views/sites.html',
      controller: 'sitesCtrl'
    });
    $routeProvider.otherwise({
      templateUrl: 'views/main.html',
      controller: 'mainCtrl'
    });
  });

// implement the currentSpot service
function currentSpot() {
  var activeMenuId = '';
  var titleText = '';

  return {
    setCurrentSpot: function (menuId, title) {
      activeMenuId = menuId;
      titleText = title;
    },
    getActiveMenu: function () {
      return activeMenuId;
    },
    getTitle: function () {
      return titleText;
    }
  }
}
//AdminCtrl controls  the title bar and the view content
function AdminCtrl($scope, currentSpot) {
  $scope.isActive = isActive;
  $scope.getTitle = getTitle;

  function isActive(menuId) {
    return currentSpot.getActiveMenu() == menuId;
  }

  function getTitle() {
    return currentSpot.getTitle();
  }
}


function MainCtrl(currentSpot) {
  currentSpot.setCurrentSpot('', '') // By default
}

function LocationsCtrl(currentSpot) {
  currentSpot.setCurrentSpot('Locations',
    'Manage the list of diving locations')
}

function SitesCtrl(currentSpot) {
  currentSpot.setCurrentSpot('Sites', 
    'Manage the list of dive sites')
}
