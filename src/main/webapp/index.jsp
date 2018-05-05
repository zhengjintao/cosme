<html ng-app="listApp">
<head>
<!-- Standard Meta -->
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

<!-- Site Properties -->
<title>cosme navix</title>
<link rel="shortcut icon" type="image/png" href="favicon.ico">
<link rel="stylesheet" type="text/css" href="dist/semantic.min.css">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="dist/semantic.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>

<script>
var app = angular.module('listApp',[]);

app.config(function($provide){
    
    $provide.factory("transFormFactory",function(){
        return {
            transForm : function(obj){
                var str = [];  
                for(var p in obj){  
                  str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));  
                } 
                return str.join("&");  
            }
        };
    });
});

app.controller('ListController', function($scope,$http,transFormFactory) {
    var list = this;
    list.searchcode = "";
    list.goodinfo={};
    list.shoplist = [];
    list.showlist= !$.isEmptyObject(list.goodinfo);
    
    list.search=function (){
    	if(list.searchcode.length=0){
    		alert("Pleas input code then search again.");
    		return;
    	}
    	$scope.url =  "list.do";
    	var postdata = {'searchcode':list.searchcode};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    		}).then(function (result) {
    			list.shoplist = result.data.shoplist;
    			list.goodinfo = result.data.goodinfo;
    			list.showlist= !$.isEmptyObject(list.goodinfo);
            }).catch(function (result) {
            	orderList.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    }
   
  });
</script>
<style>
body {
	margin-top: 10px;
	font: 12px sans-serif;
}
</style>

</head>
<body ng-controller="ListController as list">

	<div class="ui one column grid container">
		<div class="column">
			<div class="row">
				<div class="ui middle aligned divided list">
					<div class="item">
						<div class="ui action input">
							<input type="text" style="width: 213px"
								placeholder="Search by code" ng-model=list.searchcode>
							<button class="ui icon button" ng-click="list.search()">
								<i class="search icon"></i>
							</button>
							<button type="button" class="ui icon button"
								style="margin-left: 1px">
								<i class="camera icon"></i>
							</button>
						</div>

					</div>
				</div>
			</div>
			<div ng-show=list.showlist>
			<h4 class="ui horizontal divider header"></h4>
			<div class="row">
				<div class="ui card">
					<a class="image" href="#"> <img src={{list.goodinfo.imgurl}}>
					</a>
					<div class="content" style="text-align: center">
						<a class="header" href="#">{{list.goodinfo.name}}</a>
						<div class="meta">
							<a>Last Seen 2 days ago</a>
						</div>
					</div>
				</div>
			</div>
			<h4 class="ui horizontal divider header">
				<i class="tag icon"></i> List
			</h4>
			<div class="row">
				<div class="ui celled middle aligned divided list">
					<div class="item" ng-repeat="shop in list.shoplist">
						<div class="right floated content">
							<a class="label">${{shop.price}}</a>
						</div>
						<div class="content">{{shop.name}}</div>
					</div>
				</div>
			</div>
			</div>
		</div>


	</div>

</body>