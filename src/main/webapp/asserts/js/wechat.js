$(document).ready(function() {
	configWx();
});
   function configWx() {
		var thisPageUrl = location.href.split('#')[0];
		$.ajax({ 
		    type: "post", 
		    url: "./getJsTicket.do?url="+ thisPageUrl, 
		    dataType: "json", 
		    success: function (data) {
		    	if (data != null) {
					configWeiXin(data.appId, data.timestamp, data.nonceStr,
							data.signature);
				} else {
					console.log("配置weixin jsapi失败");
				}
		    }, 
		    error: function() {
		            alert("网络异常，请稍后重试");
		    } 
		});
	}

	function configWeiXin(appId, timestamp, nonceStr, signature) {
		wx.config({
			debug : false,// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : appId,
			timestamp : timestamp,
			nonceStr : nonceStr,
			signature : signature,
			jsApiList: [
		        'checkJsApi',
		        'onMenuShareTimeline',
		        'onMenuShareAppMessage',
		        'onMenuShareQQ',
		        'onMenuShareWeibo',
		        'onMenuShareQZone',
		        'hideMenuItems',
		        'showMenuItems',
		        'hideAllNonBaseMenuItem',
		        'showAllNonBaseMenuItem',
		        'translateVoice',
		        'startRecord',
		        'stopRecord',
		        'onVoiceRecordEnd',
		        'playVoice',
		        'onVoicePlayEnd',
		        'pauseVoice',
		        'stopVoice',
		        'uploadVoice',
		        'downloadVoice',
		        'chooseImage',
		        'previewImage',
		        'uploadImage',
		        'downloadImage',
		        'getNetworkType',
		        'openLocation',
		        'getLocation',
		        'hideOptionMenu',
		        'showOptionMenu',
		        'closeWindow',
		        'scanQRCode',
		        'chooseWXPay',
		        'openProductSpecificView',
		        'addCard',
		        'chooseCard',
		        'openCard'
		      ]
		});
	}