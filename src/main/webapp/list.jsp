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

<script>
initdata=<%=request.getAttribute("initdata") %>;
</script>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="dist/semantic.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="asserts/js/list.js"></script>
<script src="asserts/js/wechat.js"></script>
<style>
body {
	margin-top: 10px;
	font: 12px sans-serif;
}
</style>

</head>
<body ng-controller="ListController as list">

       <div class="ui basic modal" id="basemodal">
		<div class="ui icon header">
			<i class="archive icon"></i>{{list.message}}
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
			<div class="row">
				<div class="ui middle aligned divided list">
					<div class="item">
						<div class="ui action input">
						 <div class="ui icon input">
						  <input type="text" style="width: 213px"
								placeholder="输入或者扫描商品编码开始查询" ng-model=list.searchcode>
								<i class="trash link icon" ng-click="list.cleartext()"></i>
						 </div>
							<button class="ui icon button" ng-click="list.search()">
								<i class="search icon"></i>
							</button>
							<button type="button" class="ui icon button"
								style="margin-left: 1px" ng-click=list.scanQRCode()>
								<i class="camera icon"></i>
							</button>
						</div>

					</div>
				</div>
			</div>
			<h4 class="ui horizontal divider header"></h4>
			<div ng-show=list.showaddinfo>
			<div class="row">
			<a href="addgoods.do?mode=init&goodscode={{list.searchcode}}&imgurl={{list.goodinfo.imgurl}}">未搜索到相关商品，点击追加>></a>
			</div>
			<h4 class="ui horizontal divider red header">
				<i>热门</i>
			</h4>
			<div class="row">
			  <div class="ui celled horizontal list">
                 <div class="item" ng-repeat="good in list.hotgoods"><a style="color:red" ng-click="list.searchhotgood(good.code)">{{good.name}}</a></div>
              </div>
			</div>
			</div>
			
			<div ng-show=list.showlist>
			<div class="row">
				<div class="ui card">
					<a class="image" href="#"> <img src={{list.goodinfo.imgurl}}>
					</a>
					<div class="content" style="text-align: center">
						<a class="header" href="#">{{list.goodinfo.code}}</a>
						<div class="meta">
							<a>{{list.goodinfo.name}}</a>
						</div>
					</div>
				</div>
			</div>
			<h4 class="ui horizontal divider header">
				<i class="home icon"></i> 店铺一览
			</h4>
			<div class="row">
				<div class="ui celled middle aligned divided list">
				<div class="item">
						<a href="addgoods.do?mode=init&goodscode={{list.searchcode}}&imgurl={{list.goodinfo.imgurl}}">点击追加新店铺>></a>
					</div>
				
					<div class="item" ng-repeat="shop in list.shoplist">
						<div class="right floated content">
							<a class="label" href="addgoods.do?mode=init&goodscode={{list.searchcode}}&imgurl={{list.goodinfo.imgurl}} ">${{shop.price}}</a>
						</div>
						<div class="content">{{shop.name}}</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
</body>
</html>