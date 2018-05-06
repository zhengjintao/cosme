var app = angular.module('addGoodsApp', []);
	app.config(function($provide) {

		$provide.factory("transFormFactory", function() {
			return {
				transForm : function(obj) {
					var str = [];
					for ( var p in obj) {
						str.push(encodeURIComponent(p) + "="
								+ encodeURIComponent(obj[p]));
					}
					return str.join("&");
				}
			};
		});
	});

	app.controller('addgoodsController', function($scope, $http, transFormFactory) {
		var goodsinfo = this;
		
		// 商品编码
		goodsinfo.code = initdata.goodscode;
		// 商品图片
		goodsinfo.imgurl = initdata.imgurl;
		// 店铺名称
		goodsinfo.shopname = "";
		// 商品价格
		goodsinfo.price = "";
		// 相机图标显示FLG
		goodsinfo.showcamera = $.isEmptyObject(goodsinfo.imgurl);
		// 商品图片显示FLG
		goodsinfo.showimg = !$.isEmptyObject(goodsinfo.imgurl);
		
		// 商品图片（拍照）
		goodsinfo.chooseImage = function() {
			var images = {
				localId: [],
				serverId: []
			};
			  
		    wx.chooseImage({
		        success: function (res) {
		          images.localId = res.localIds;
		          alert('已选择 ' + res.localIds.length + ' 张图片');
		        }
		      });
		}
		
		// 店铺商品保存处理
		goodsinfo.save = function() {
			if(goodsinfo.showcamera && "" == goodsinfo.imgurl){
				goodsinfo.message = "请拍摄商品图片";
	    		$('.ui.basic.modal') .modal('show');
	    		return;
	    	}
			
			if("" == goodsinfo.price){
				goodsinfo.message = "请输入商品价格";
	    		$('.ui.basic.modal') .modal('show');
	    		return;
	    	}
			
	    	$scope.url =  "addgoods.do";
	    	var postdata = {
	    			'mode':'save',
	    			'goodscode':goodsinfo.code,
	    			'imgurl':goodsinfo.imgurl,
	    			'shopname':goodsinfo.shopname,
	    			'price':goodsinfo.price};
	        $http(
	    		{
	    			method:"POST",
	    			url:$scope.url,
	    			data:postdata,
	    			transformRequest:transFormFactory.transForm,
	    			headers:{'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}
	    		}).then(function (result) {
	            }).catch(function (result) {
	            	goodsinfo.message = "SORRY!エラーが発生しました。";
	            	$('.ui.basic.modal') .modal('show');
	            });
	    }
	});