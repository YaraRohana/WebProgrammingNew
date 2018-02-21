
var app=angular.module('homeApp',[]);
app.controller('homeCtrl',function($scope,$http){
$scope.getLikes=function(bookName){
var data={
bookName:bookName
};
var jsonData=JSON.stringify(data);
$http.post("GetAllLikesByBookName",jsonData).then(function(response){
$scope.likes=response.data;
console.log($scope.likes);
});
};

});