﻿<html ng-app="addGoodsApp">
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
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script src="asserts/js/addgoods.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="asserts/js/wechat.js"></script>
<script>
initdata=<%=request.getAttribute("initdata") %>;

$("#goodsname").attr("readonly",true);
$("#shopsname").attr("readonly",false);
</script>
<style>
body {
	margin-top: 10px;
	font: 12px sans-serif;
}
</style>

</head>
<body ng-controller="addgoodsController as goodsinfo">
	<div class="ui basic modal" id="basemodal">
		<div class="ui icon header">
			<i class="archive icon"></i>{{goodsinfo.message}}
		</div>
		<!-- <div class="content">
			<p>Your inbox is getting full, would you like us to enable
				automatic archiving of old messages?</p>
		</div> -->
		<div class="actions">
			<div class="ui green basic cancel inverted button">
				<i class="checkmark icon"></i> OK
			</div>
		</div>
	</div>

	<div class="ui one column grid container">
		<div class="column">
			<div class="ui middle aligned divided list">
				<div class="row">
					<a class="section" style="color:blue" href="list.do?mode=init&goodscode={{goodsinfo.code}}"><i class="left chevron icon divider" style="color:blue"></i>返回</a> 
				</div>
			</div>
			<div class="ui middle aligned divided list"
				ng-show=goodsinfo.showcamera>
				<div class="item">
					<button type="button" class="ui icon button"
						style="margin-left: 1px" ng-click="goodsinfo.chooseImage()">
						<i class="camera icon"></i>
					</button>
				</div>
			</div>
			
			<div class="ui middle aligned divided list">
				<div class="item">
					<div class="ui labeled input">
						<div class="ui label">商品编码</div>
						<input type="text" ng-model=goodsinfo.code readonly="readonly">
					</div>
				</div>
			</div>
			<div class="ui middle aligned divided list">
				<div class="item">
					<div class="ui labeled input">
						<div class="ui label">商品名称</div>
						<input id="goodsname" type="text" ng-model=goodsinfo.name>
					</div>
				</div>
			</div>
			<div class="ui middle aligned divided list">
				<div class="item">
					<div class="ui labeled input">
						<div class="ui label">店铺名称</div>
						<input id="shopname" type="text" ng-model=goodsinfo.shopname>
					</div>
				</div>
			</div>
			<div class="ui middle aligned divided list">
				<div class="item">
					<div class="ui labeled input">
						<div class="ui label">商品价格</div>
						<input type="text" ng-model=goodsinfo.price>
					</div>
				</div>
			</div>

			<button class="ui basic submit button" ng-click="goodsinfo.save()">
				<i class="icon user"></i> 追加
			</button>
		</div>
	</div>

</body>