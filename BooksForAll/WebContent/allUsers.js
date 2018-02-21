
var app=angular.module('myApp',[]);
app.controller('myCtrl',function($scope,$http){
$http.post("GetAllUsers") 
			.then(function(response){
			   $scope.users = response.data;
			   console.log($scope.users);
			});
$scope.deleteUser=function(username){
	confirm("Are You Sure?");
	var data={
			username:username
	};
	var jsonData=JSON.stringify(data)
	$http.post("RemoveUser",jsonData);
};
});
