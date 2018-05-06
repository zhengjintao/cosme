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
    list.searchcode = initdata.goodscode;
    list.goodinfo={};
    list.shoplist = [];
    list.hotgoods = [];
    
    list.showlist= !$.isEmptyObject(list.goodinfo);
    list.showaddinfo = list.searchcode.length > 0 && !list.showlist;
    
    list.listhotgoods = function(){
    	$scope.url =  "list.do";
    	var postdata = {'mode':'listhot'};
        $http(
    		{
    			method:"POST",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    		}).then(function (result) {
    			list.hotgoods = result.data.hotgoods;
            }).catch(function (result) {
            	list.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
   }
    
    list.listhotgoods();
    
    list.scanQRCode = function(){
    	 wx.scanQRCode({
    	      needResult: 1,
    	      desc: 'scanQRCode desc',
    	      success: function (res) {
    	    	  list.searchcode= res.resultStr.split(",")[1];
    	    	  list.search();
    	      }
    	    });
    }
    
    list.cleartext= function(){
          list.searchcode= "";
          list.listhotgoods();
          list.goodinfo=[];
          list.showlist= !$.isEmptyObject(list.goodinfo);
		  list.showaddinfo = list.searchcode.length > 0 && !list.showlist;
      }

    list.searchhotgood= function(text){
      list.searchcode= text;
  	  list.search();
    }
    
    list.search=function (){
    	if(list.searchcode.length == 0){
    		list.cleartext();
    		return;
    	}
    	$scope.url =  "list.do";
    	var postdata = {'mode':'search', 'searchcode':list.searchcode};
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
    			list.showaddinfo = list.searchcode.length > 0 && !list.showlist;
            }).catch(function (result) {
            	list.message = "SORRY!エラーが発生しました。";
            	$('.ui.basic.modal') .modal('show');
            });
    }
    
    if(list.searchcode.length >0){
    	list.search();
    }
   
  });
  