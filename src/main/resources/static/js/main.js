var app = angular.module("WorkFormApp", ['lodash', 'chart.js']);

app.controller("WorkFormStatisticCtrl", function($scope, $http, _) {

	  $scope.getAllStatistic = function() {
		  $http.get('/statistic/').
		    success(function(statisticRecords, status, headers, config) {
		    	labels = [];
		    	data = [];
		    	totalData = [];
		    	finishedData = [];
		    	_.forEach(statisticRecords, function(statisticRecord) {
		    		labels.push(statisticRecord.date);
		    		totalData.push(statisticRecord.total);
		    		finishedData.push(statisticRecord.finished);
		    	});
		    	data.push(totalData);
		    	data.push(finishedData);
		    	$scope.labels = labels;
		    	$scope.data = data;
		    }).
		    error(function(data, status, headers, config) {
		      console.error(data);
		    });
	  }
	  
	  $scope.getAllStatistic();
	
//	  $scope.labels = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
//	  $scope.data = [
//	    [65, 59, 80, 81, 56, 55, 40],
//	    [28, 48, 40, 19, 86, 27, 90]
//	  ];
	  
});

app.controller("WorkFormCtrl", function($scope, $http, _) {
	
  handleData = function(data) {
      var workformsByDay = {};
      _.forEach(data, function(workform) {
    	  var date = workform.date.split(" ")[0];
    	  if(!workformsByDay[date]) {
    		  workformsByDay[date] = [];
    	  }
    	  workformsByDay[date].push(workform);
      });
      $scope.workformsByDay = workformsByDay;
  };
  
  $scope.getAll = function() {
	  $http.get('/workform/').
	    success(function(data, status, headers, config) {
	    	handleData(data);
	    }).
	    error(function(data, status, headers, config) {
	      console.error(data);
	    });
  }
  
  //$scope.getAll();
  
  $scope.search = function() {
	  var startDate = $('#startQueryDate').val();
	  var endDate = $('#endQueryDate').val();
	  $http.get('/workform/?startDate=' + encodeURIComponent(startDate) + "&endDate=" + encodeURIComponent(endDate)).
	    success(function(data, status, headers, config) {
	    	handleData(data);
	    }).
	    error(function(data, status, headers, config) {
	      console.error(data);
	    });
  }
});