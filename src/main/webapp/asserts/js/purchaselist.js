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
    list.totalnum=0;
    list.totalrest = 0;
    list.onlyshowrest = false;
    list.neworderdata = {'goodsname': '', 'customnm':'', 'total': 1, 'rest': 1, 'status': 1};
    list.orderlist = [
                        {'goodsname':'资生堂 面膜 300L', 'totalnum':3, 'restnum':2 ,
                                            'detaillist': [
                                                           {'customnm':'小明妈妈', 'num':1},
                                                           {'customnm':'土豪爸爸', 'num':2}
                                                         ]
                        }
                    ];
    // status- 0:delete 1:active
    list.orderdetaillist = [{'goodsname': '资生堂 面膜', 'customnm':'小明妈妈', 'total':1, 'rest': 1, 'status': 1},
                            {'goodsname': 'FANCL 乳液', 'customnm':'小明妈妈', 'total':2, 'rest': 0, 'status': 1},
                            {'goodsname': '资生堂 面膜', 'customnm':'土豪妈妈', 'total':2, 'rest': 1, 'status': 1},
                            {'goodsname': '资生堂 面膜', 'customnm':'土豪妈妈', 'total':2, 'rest': 1, 'status': 1},
                            {'goodsname': '资生堂 眼霜', 'customnm':'小冰块妈妈', 'total':2, 'rest': 0, 'status': 1},
                            {'goodsname': 'Dior 999 口红', 'customnm':'小冰块妈妈', 'total':2, 'rest': 0, 'status': 1},
                            {'goodsname': 'Dior 999 口红', 'customnm':'姐姐', 'total':1, 'rest': 1, 'status': 1},
                            {'goodsname': 'YSL口红', 'customnm':'小冰块', 'total':2, 'rest': 2, 'status': 1}
                          ];

    list.transdata = function(){
         list.totalnum=0;
         list.totalrest = 0;
         list.orderlist = [];
         list.orderdetaillist.forEach(
           function(value) {

             if(list.onlyshowrest && 0 == value.rest){
               return;
             }

             var hasgoods  = false;
             var detail = {};
             var order = {};
             list.orderlist.forEach(
               function(value2) {
                 if(value2.goodsname == value.goodsname ){
                   hasgoods = true;
                   order = value2;
                 }
               });

               detail.customnm = value.customnm;
               detail.num = value.total;
               detail.rest = value.rest;
               detail.style= detail.rest == detail.num ? "red" : detail.rest == 0 ? "grey" : "green";
             if (hasgoods){
               order.totalnum = order.totalnum + value.total;
               order.restnum = order.restnum + value.rest;
               hasgoods = false;
               order.detaillist.forEach(
                 function(value3) {
                   if(value3.customnm == value.customnm ){
                     hasgoods = true;
                     detail = value3;
                   }
                 });

                 if (hasgoods){
                   detail.num = detail.num + value.total;
                   detail.rest = detail.rest + value.rest;
                   detail.style= detail.rest == detail.num ? "red" : detail.rest == 0 ? "grey" : "green";
                 }else{
                   order.detaillist.push(detail);
                 }
             }else {
               order.goodsname = value.goodsname;
               order.totalnum = value.total;
               order.restnum = value.rest;
               order.detaillist=[];
               order.detaillist.push(detail);
               list.orderlist.push(order);
             }

             list.totalnum = list.totalnum + value.total;
             list.totalrest = list.totalrest + value.rest;
           });
    }

    list.transdata();

    list.addorder = function (){
      var neworder = {};
      neworder.goodsname = list.neworderdata.goodsname;
      neworder.customnm = list.neworderdata.customnm;
      neworder.total = list.neworderdata.total;
      neworder.rest = list.neworderdata.total;
      neworder.status = '1';
      list.orderdetaillist.push(neworder);
      list.transdata();
      list.neworderdata = {'goodsname': '', 'customnm':'', 'total': 1, 'rest': 1, 'status': 1};
      $('.ui.modal')
        .modal('hide')
      ;
    }

    list.itemedit = function (order, detail){
      alert(order.goodsname + " : "+detail.customnm);
    }
    list.search=function (goodsname){

    	$scope.url =  "https://script.google.com/macros/s/AKfycbweJFfBqKUs5gGNnkV2xwTZtZPptI6ebEhcCU2_JvOmHwM2TCk/exec?text=" + goodsname + "&source=zh-cn&target=ja";
    	var postdata = {};
        $http(
    		{
    			method:"GET",
    			url:$scope.url,
    			data:postdata,
    			transformRequest:transFormFactory.transForm,
    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
    		}).then(function (result) {

  //オプションパラメーターleftとtopに余白の値を指定
  window.location.href = "https://www.amazon.co.jp/gp/aw/s/ref=nb_sb_noss?k="+result.data;

            }).catch(function (result) {
            	list.message = "SORRY!出错啦。";
            	$('.ui.modal') .modal('show');
            });
    }

    list.chkchange = function (){
      list.transdata();
    }

    list.neworder = function (){
        $('.ui.modal')
          .modal('show')
        ;
    }
    
    $('.ui.dropdown')
    .dropdown()
  ;

  });
