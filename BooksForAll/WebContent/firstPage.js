var app=angular.module("firstPage",[]);
app.controller("myCtrl",function($scope,$http,$document){
  $scope.addNewUser=function(){
var User={
username: $scope.username,
email: $scope.email,
cityName: $scope.cityName,
streetName: $scope.streetName,
apartmentNumber: $scope.apartmentNumber,
postalCode: $scope.postalCode,
phoneNumber: $scope.telStart+$scope.phoneNumber,
password: $scope.password,
nickname: $scope.nickname,
};
var jsonUser=JSON.stringify(User);
$http.post('AddUser',jsonUser)
.then(function(response){
  $scope.msg=response.data;
  console.log($scope.msg);
  },function(error){
	  var msg=$document.getElementById("failed");
	  mag.i
  }
);

};
});
