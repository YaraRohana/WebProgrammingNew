var app=angular.module('myApp',[]);
app.controller('myEbookCtrl',function($scope,$http){
	var d={
			username:"YaraRohana"
	};
	var jsonUsername=JSON.stringify(d);
$http.post("GetAllBooksByUsername",jsonUsername) 
.then(function(response){
   $scope.ebooks = response.data;
   console.log($scope.books);
});

}

);