/**
 * 共有方法
 */
define(function(require,exports,module){
	var preClose, 
	qType,cRed = "#ff4433",
	cGreen = "#03B83F",//00FBFA
	cGreen2 = "#32a632",
	cWhite = "#a5cce4",
	cWhite2 = '#ffffff',
	cBlue = "#00FF00",
	cYellow = "#b49709",
	cPink = "#ff9f9f",
	cText = "#899198",
	cRect = "#b0b0b0",
	cLine = "#26303a",
	//cbg = "#101419",
	cbg = "#f2f2f2",
	chartFont = "20px Helvetica";
	qType = "cn";
	ma5 = "#ffffff",
	ma10 = "#e0e275",
	ma30 = "#e7b4e3",
	ma60 = "#8acd86",
	canvasType = "day",
	lineWidth = 8;//默认8
	count = 100;
	function formatMinData(a,_canvas) {
	    var b = new Array();

	    var c = eval('(' + a + ')');
	    var d = new Array();
	    var i = 0;
	    var e = null;
	    for (var i = 0; i < c.mins.length; i++) {
	        e = c.mins[i];
	        var f = new Array();
	        f.push(e.price + "");
	        f.push(e.volume);
	        f.push(e.priceAvg);
	        f.push(c.quote.type);
	        b.push(f);
	    }
	    b.dayHigh = Math.max.apply(null, d) + "";
	    var h = kds(_canvas);
	    drawFsChart(b, c.quote.preClose, h);
	}

	function kds(o) 
	{
	    return document.getElementById(o);
	}
	function drawFsChart(j, q, z) // 参数，昨收，画板
	{
	    preClose = q;
	    function A(e, t, n, r) {
	        return r - r / (2 * t) * (e - n + t);
	    }
	    var B = z;// 画板
	    var D = B.getContext("2d");// 方法返回一个用于在画布上绘图的环境
	    D.save();
	    var r = B.height * 0.01,//距离顶部高度
	    i = B.width * 0.01,//左边
	    s = B.width * 0.99,//宽度
	    o = B.height * 0.52,//高度
	    u = B.height * 0.45,//成交量高度
	    a = B.height * 0.01,//分时图和成交量直接
	    f = 240;// 分时点数
	    D.clearRect(0, 0, B.width, B.height);
	    D.fillStyle = cbg;
	    D.fillRect(0, 0, B.width, B.height);
	    drawRect(D, r, i+80, s-160, o, u, a, qType, !0);
	    if(j.length <= 0)
	    {
	    	 return false;
	    }	 
	    if (preClose <= 0) {
	        D.restore();
	        return
	    }
	    var l = 0;
	   
	    for (var c = 0; c < j.length; c++) {
	        var h = j[c];
	        parseFloat(h[0]) == 0 && h[1] == 0 && (h[0] = preClose);
	        var p = Math.abs(preClose - parseFloat(h[0]));
	        p > l && (l = p);
	    }
	    var d = (s-160) / (f - 1);
	    D.save(),
	    D.beginPath(),
	    D.lineWidth = 3,
	    D.strokeStyle = cYellow;
	    var v = 0;
	    if(j[0][3] != "7"&&j[0][3] != "15")// 绘制均线
		{
		    for (var c = 0; c < j.length; c++) {
		        var m = i + 80 + d * c - .5,
		        b = r + A(parseFloat(j[c][2]), l, preClose, o) - .5;
		        c == 0 ? D.moveTo(m, b) : D.lineTo(m, b);
		    }
		}

	    D.stroke(),
	    D.beginPath(),
	    D.lineWidth = 3,
	    D.strokeStyle = cWhite;
	    for (var c = 0; c < j.length; c++) {
	        var m = i + 80 + d * c - .5,
	        b = r + A(parseFloat(j[c][0]), l, preClose, o) - .5;
	        c == 0 ? D.moveTo(m, b) : D.lineTo(m, b);
	    }
	    if(j.length <= 1){
	        D.strokeStyle = cYellow;
	        D.moveTo(i + 80 - .5, r + o / 2);
	        D.lineTo(i + 80 + s-160, r + o / 2);
	    }
	    D.stroke(),
	    D.restore();
	    var w = 0;
	    for (var c = 0; c < j.length; c++) {
	        var E = [];
	        if (c == 0) w = j[c][1];
	        else {
	            var S = j[c][1];
	            w = Math.max(S, w);
	        }
	    }
	    var d = (s-160) / (f - 1);
	    for (var c = 0; c < j.length; c++) {
	        var x = 0,
	        S = 0;
	        c == 0 ? (x = preClose, S = j[c][1]) : (x = parseFloat(j[c - 1][0]), S = j[c][1]);
	        var T = parseFloat(j[c][0]) - x >= 0 ? cRed: cGreen;
	        D.beginPath(),
	        D.strokeStyle = T;
	        var m = i + 80 + d * c - .5,
	        b = r + o + a + u - u / w * S - .5;
	        D.moveTo(m, b),
	        D.lineTo(m, r + o + a + u),
	        D.stroke();
	    }
	    var N = [[preClose + l, cRed], [preClose + l * 1 / 2, cRed], [preClose, cText], [preClose - l * 1 / 2, cGreen], [preClose - l, cGreen]];
	    for (var c = 0; c < N.length; c++) N[c].push((N[c][0] - preClose) / preClose * 100);
	    D.font = chartFont;
	    for (var c = 0; c < N.length; c++) {
	        D.textAlign = "right",
	        D.fillStyle = N[c][1];
	        var C = "";
	        C = N[c][0].toFixed(2);
	        if(C.length == 8)
	        {
	        	if(c==0)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==1)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 17);	
	        	}else if(c==2)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==3)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==4)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c );	
	        	}		
	        	
	        }	        
	        else if(C.length == 7)
	        {
	        	if(c==0)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==1)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 17);	
	        	}else if(c==2)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==3)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==4)
	        	{
	        		 D.fillText(C, i +75, r + (o / 4 - 2) * c );	
	        	}		
	        	
	        }else if(C.length == 6)
	        {
	        	if(c==0)
	        	{
	        		 D.fillText(C, i +63, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==1)
	        	{
	        		 D.fillText(C, i +63, r + (o / 4 - 2) * c + 17);	
	        	}else if(c==2)
	        	{
	        		 D.fillText(C, i +63, r + (o / 4 - 2) * c + 19);	
	        	}else if(c==3)
	        	{
	        		 D.fillText(C, i +63, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==4)
	        	{
	        		 D.fillText(C, i +63, r + (o / 4 - 2) * c );	
	        	}	
	        }
	        else if(C.length == 5)
	        {
	        	if(c==0)
	        	{
	        		 D.fillText(C, i +52, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==1)
	        	{
	        		 D.fillText(C, i +52, r + (o / 4 - 2) * c + 17);	
	        	}else if(c==2)
	        	{
	        		 D.fillText(C, i +52, r + (o / 4 - 2) * c + 19);	
	        	}else if(c==3)
	        	{
	        		 D.fillText(C, i +52, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==4)
	        	{
	        		 D.fillText(C, i +52, r + (o / 4 - 2) * c );	
	        	}	
	        }else if(C.length == 4)
	        {
	        	if(c==0)
	        	{
	        		 D.fillText(C, i +40, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==1)
	        	{
	        		 D.fillText(C, i +40, r + (o / 4 - 2) * c + 17);	
	        	}else if(c==2)
	        	{
	        		 D.fillText(C, i +40, r + (o / 4 - 2) * c + 19);	
	        	}else if(c==3)
	        	{
	        		 D.fillText(C, i +40, r + (o / 4 - 2) * c + 15);	
	        	}else if(c==4)
	        	{
	        		 D.fillText(C, i +40, r + (o / 4 - 2) * c );	
	        	}	
	        }
	        else
	        {
	        	D.fillText(C, i +40, r + (o / 4 - 2) * c );	
	        }	
	        
	        
	        D.textAlign = "left";
	        var xperLenght = i + s + 2-63;
	        if(N[c][2].toFixed(2).length == 5)
	        {
	        	if(N[c][2].toFixed(2).indexOf("-")>=0)
	        	{
	        		xperLenght = xperLenght -12;
	        	}else
	        	{
	        		xperLenght = xperLenght -17;
	        	}
	        }else if(N[c][2].toFixed(2).length == 6)
	        {
	        	xperLenght = xperLenght -23;
	        }else if(N[c][2].toFixed(2).length == 7)
	        {
	        	if(N[c][2].toFixed(2).indexOf("-")>=0)
	        	{
	        		xperLenght = xperLenght -30;
	        	}else
	        	{
	        		xperLenght = xperLenght -35;
	        	}	
	        	
	        }else if(N[c][2].toFixed(2).length == 8)
	        {
	        	if(N[c][2].toFixed(2).indexOf("-")>=0)
	        	{
	        		xperLenght = xperLenght -37;
	        	}else
	        	{
	        		xperLenght = xperLenght -43;
	        	}	
	        	
	        }	
	        	
        	if(c==0)
        	{
        		 D.fillText(N[c][2].toFixed(2) + "%", xperLenght, r + (o / 4 - 2) * c +15);
        	}else if(c==1)
        	{
        		 D.fillText(N[c][2].toFixed(2) + "%", xperLenght, r + (o / 4 - 2) * c +15);
        	}else if(c==2)
        	{
        		 D.fillText(N[c][2].toFixed(2) + "%", xperLenght, r + (o / 4 - 2) * c +15);
        	}else if(c==3)
        	{
        		 D.fillText(N[c][2].toFixed(2) + "%", xperLenght, r + (o / 4 - 2) * c +15);
        	}else if(c==4)
        	{
        		 D.fillText(N[c][2].toFixed(2) + "%", xperLenght, r + (o / 4 - 2) * c );
        	}	
	      
	    }
	    D.fillStyle = cText,
	    D.textAlign = "center",
	    D.font = "20px Helvetica";
	    D.fillText("10:30", i + 80 + (s-160) / 4, r + o + 18);
	    D.textAlign = "center",
	    D.fillText("11:30/13:00", i + 80 + (s-160) / 2, r + o + 18);
	    D.textAlign = "center",
	    D.fillText("14:00", i + 80 + (s-160) *3/ 4, r + o + 18);
	    D.textAlign = "center",
	    D.fillText("15:00", i + 80 + (s-160) -28, r + o + 18);
	    var k = getUnit(qType);
	    if(w == undefined){
	        w = 0;
	    }
	    w > 999999 && (w /= 1e4, k = "万" + k);
	    var L = [w, w / 2];
	    D.fillStyle = cText,
	    D.textAlign = "right";
	    //成交量
	    for (var c = 0; c < L.length; c++)
	    {
	    	if(L[c].toFixed(0).length ==6)
	    	{
	    		if(c==0)
	    		{
	    			D.fillText(L[c].toFixed(0), i + 69, r + o + a + u * 3 / 8 * c + 17);
	    		}else
	    		{
	    			D.fillText(L[c].toFixed(0), i + 69, r + o + a + u * 3 / 8 * c + 24);
	    		}	
	    		
	    	}else if(L[c].toFixed(0).length ==5)
	    	{
	    		if(c==0)
	    		{
	    			D.fillText(L[c].toFixed(0), i + 58, r + o + a + u * 3 / 8 * c + 17);
	    		}else
	    		{
	    			D.fillText(L[c].toFixed(0), i + 58, r + o + a + u * 3 / 8 * c + 24);
	    		}	
	    		
	    	}else if(L[c].toFixed(0).length ==4)
	    	{
	    		if(c==0)
	    		{
	    			D.fillText(L[c].toFixed(0), i + 45, r + o + a + u * 3 / 8 * c + 17);
	    		}else
	    		{
	    			D.fillText(L[c].toFixed(0), i + 45, r + o + a + u * 3 / 8 * c + 24);
	    		}
	    	}else if(L[c].toFixed(0).length ==3)
	    	{
	    		if(c==0)
	    		{
	    			D.fillText(L[c].toFixed(0), i + 34, r + o + a + u * 3 / 8 * c + 17);
	    		}else
	    		{
	    			D.fillText(L[c].toFixed(0), i + 34, r + o + a + u * 3 / 8 * c + 24);
	    		}
	    	}else
	    	{
	    		if(c==0)
	    		{
	    			D.fillText(L[c].toFixed(0), i + 20, r + o + a + u * 3 / 8 * c + 17);
	    		}else
	    		{
	    			D.fillText(L[c].toFixed(0), i + 20, r + o + a + u * 3 / 8 * c + 24);
	    		}
	    	}	
	    	
	    }	
	    
	    
	    D.restore();
	}

	function drawRect(b, y, x, w, h, d, e, g, a) {
	    b.beginPath(),
	    b.strokeStyle = cLine,
	    b.strokeRect(x - .2, y - .2, w, h),
	    b.strokeStyle = cLine;
	    for (var f = 1; f <= 3; f++) {// 三格
	        var l = y + h / 4 * f - .5;
	        b.moveTo(x, l);
	        b.lineTo(x + w, l);
	    }
	    for (var f = 1; f < 4; f++) {
	        var c;
	        c = x + w / 4 * f - .5;
	        b.moveTo(c, y);
	        b.lineTo(c, y + h);
	    }
	    b.strokeStyle = cLine,
	    b.strokeRect(x - .5, y + h + e - .5, w, d),
	    b.strokeStyle = cLine;
	    for (var f = 1; f <= 1; f++) {
	        var l = y + h + e + d / 2 * f - .5;
	        b.moveTo(x, l);
	        b.lineTo(x + w, l);
	    }
	    for (var f = 1; f < 4; f++) {
	        var c;
	        c = x + w / 4 * f - .5;
	        b.moveTo(c, y + h + e);
	        b.lineTo(c, y + h + e + d);
	    }
	    b.stroke();
	}
	function getUnit(e) {
	    return e == "cn" || e == "cn_b" || e == "cnIndex" || e == "fund" ? "手": "股";
	}
	function formatKlineData(a,canvasK,_count,state,stockInfo) {
		count = _count;
	    var b = new Array();
	    var c = a;
	    var result = {};
	    var ks = [];
	    for (var i = 0; i < c.results.length; i++) {
	        var rawData = c.results[i];
	        var item = {
	            quoteTime: rawData[0],
	            preClose: rawData[3],
	            open: Number((rawData[1]/100).toFixed(2)),
	            high:  Number((rawData[2]/100).toFixed(2)),
	            low:  Number((rawData[4]/100).toFixed(2)),
	            close:  Number((rawData[3]/100).toFixed(2)),
	            volume: rawData[5],
	            amount: rawData[6],
	            fiveavg: Number((rawData[7]/100).toFixed(2)),
	            tenavg: Number((rawData[8]/100).toFixed(2)),
	            thityavg: Number((rawData[9]/100).toFixed(2)),
	            sixavg: Number((rawData[10]/100).toFixed(2))
	            
	        };
	        if (ks.length == 0) {
	            result.low = item.low;
	            result.high = item.high;
	        } 
	        else 
	        {
	            result.high = Math.max(result.high, item.high);
	            result.high = Math.max(result.high, item.fiveavg);
	            result.high = Math.max(result.high, item.tenavg);
	            result.high = Math.max(result.high, item.thityavg);
	            result.high = Math.max(result.high, item.sixavg);
	            
	            if(item.low > 0)
	                result.low = Math.min(result.low, item.low);
	            if(item.fiveavg > 0)
	                result.low = Math.min(result.low, item.fiveavg);
	            if(item.tenavg > 0)
	                result.low = Math.min(result.low, item.tenavg);
	            if(item.thityavg > 0)
	                result.low = Math.min(result.low, item.thityavg);
	            if(item.sixavg > 0)
	                result.low = Math.min(result.low, item.sixavg);            
	        }
	        ks.push(item);
	    }
	    if(state)//判断是同一个周期，需要将今天附加或者比较前一天的数据
	    {
	       if(c.results.length <= 0)
	       {
	    	   return false;
	       }   
	 	   var stock =  stockInfo;
	 	   var rawLastData = c.results[c.results.length-1];//返回数据的最后一个点
	       var dayD = {};
		   dayD.quoteTime = "";
		   dayD.preClose = stock[8];
		   dayD.open = stock[6];
	 	   if(c.results.length>2)
	 	   {
	 		   rawData = c.results[c.results.length-2];//实际数据最后一个点的数据
			   dayD.high = (stock[11]>=rawData[2]/100)?stock[11]:(rawData[2]/100);
			   dayD.low = (stock[12]>=rawData[4]/100)?(rawData[4]/100):stock[12];
			   dayD.volume = stock[7]+rawData[5]/100;
			   dayD.amount = stock[9]+rawData[6];
	 	   }
		   dayD.close = stock[4];
		   dayD.fiveavg = Number(rawLastData[7]/100 + stock[4]/5).toFixed(2);
		   dayD.tenavg = Number(rawLastData[8]/100+ stock[4]/10).toFixed(2);
		   dayD.thityavg = Number(rawLastData[9]/100+ stock[4]/30).toFixed(2);
		   dayD.sixavg = Number(rawLastData[10]/100+ stock[4]/60).toFixed(2);
		   ks.length = (ks.length-1);
		   ks.push(dayD);
		   
		   //重新设置最高最低价
           result.high = Math.max(result.high, dayD.high);
           result.high = Math.max(result.high, dayD.fiveavg);
           result.high = Math.max(result.high, dayD.tenavg);
           result.high = Math.max(result.high, dayD.thityavg);
           result.high = Math.max(result.high, dayD.sixavg);
            
           result.low = Math.min(result.low, dayD.low);
           result.low = Math.min(result.low, dayD.fiveavg);
           result.low = Math.min(result.low, dayD.tenavg);
           result.low = Math.min(result.low, dayD.thityavg);
           result.low = Math.min(result.low, dayD.sixavg);   

	    }else
	    {
		 	   var rawLastData = c.results[c.results.length-1];//返回数据的最后一个点
			   var dayD = {};
			   dayD.quoteTime = "";
			   var stock =  stockInfo;
			   dayD.preClose = stock[8];
			   dayD.open = stock[6];
			   dayD.high = stock[11];
			   dayD.low = stock[12];
			   dayD.close = stock[4];
			   dayD.volume = stock[7];
			   dayD.amount = stock[9];
			   if(rawLastData)
			   {
				   dayD.fiveavg = Number(rawLastData[7]/100 + stock[4]/5).toFixed(2);
				   dayD.tenavg = Number(rawLastData[8]/100+ stock[4]/10).toFixed(2);
				   dayD.thityavg = Number(rawLastData[9]/100+ stock[4]/30).toFixed(2);
				   dayD.sixavg = Number(rawLastData[10]/100+ stock[4]/60).toFixed(2);  
			   }
			   ks[ks.length-1] = dayD;
			   //重新设置最高最低价
	            result.high = Math.max(result.high, dayD.high);
	            result.high = Math.max(result.high, dayD.fiveavg);
	            result.high = Math.max(result.high, dayD.tenavg);
	            result.high = Math.max(result.high, dayD.thityavg);
	            result.high = Math.max(result.high, dayD.sixavg);
	            
                result.low = Math.min(result.low, dayD.low);
                result.low = Math.min(result.low, dayD.fiveavg);
                result.low = Math.min(result.low, dayD.tenavg);
                result.low = Math.min(result.low, dayD.thityavg);
                result.low = Math.min(result.low, dayD.sixavg); 
	    }	
	    
	    
	    
       if(c.results.length <= 0)
       {
    	   return false;
       }   
	    
      result.ks = ks;
      var canvas = kds(canvasK);
      drawKlineChart(result, canvas,count);
	}
	function drawKlineChart(kxData, canvas,count) // 参数，昨收，画板
	{
	    var B = canvas;// 画板
	    var D = B.getContext("2d");// 方法返回一个用于在画布上绘图的环境
	    D.save();
	    var top = B.height * 0.01,//距离顶部高度
	    left = B.width * 0.01,//左边
	    width = B.width * 0.99*kxData.ks.length/count,
	    height = B.height * 0.67,
	    height_volume = B.height * 0.305,//成交量高度
	    height_spacing = B.height * 0.01;//分时图和成交量直接
	    D.clearRect(0, 0, B.width, B.height);
	    D.fillStyle = cbg;
        if(kxData.length <= 0)
        {
        	return false;
        }
	    if(kxData.length < count)//没有一页的数据
	    {
	    	D.fillRect(0, 0, B.width*kxData.ks.length/count, B.height);
	    }else	
	    {
	    	D.fillRect(0, 0, B.width, B.height);
	    }

	    //绘制外框
	    drawKlineRect(D, top, left+80, width*count/kxData.ks.length-80, height, height_volume, height_spacing, qType, !0);
	    
	    var data = kxData;
	    var total_low = data.low;
	    var total_high = data.high;
	    var volume_high = 0;
	    var volume_unit = "万";
	    var ks = data.ks;
	    var count = ks.length,k_width = lineWidth;

	    for(var i=0;i<ks.length;i++){
	        volume_high = Math.max(volume_high, ks[i].volume);
	    }
	    
	    D.fillStyle = cText,
	    D.textAlign = "right",
	    D.font = "20px 宋体";
	    if(ks.length>40)
	    {
		    D.textAlign = "left";
		    if(ks.length > 1)
	    	{
		    	D.fillText(ks[Math.ceil(ks.length/2)].quoteTime, (width-80)/2+40, top + height + 20);
	    	}	    	
	    }	

	    if(ks.length>20)
	    {
		    D.textAlign = "right";
		    D.fillText(ks[ks.length-1].quoteTime, left + width - 10, top + height + 20);
	    }	

	    
	    var flag = 0;//空心还是实心标志
	    for(var i=0;i<count;i++){
	        var tmp_color = cWhite;
	        if(ks[i].close > ks[i].open)
	        {
	        	flag = 1;
	        	tmp_color = cRed;
	        }	
	        else if(ks[i].close < ks[i].open)
	        {
	        	flag = 1;
	            tmp_color = cGreen2;//cGreen
	        }	
	        else
	        {
	        	flag = 1;
	            tmp_color = cWhite2;	
	        }	


	        D.beginPath();
	        D.strokeStyle = tmp_color;
	        D.moveTo(left+80+(width-80)/count*i+(width-80)/(2 * count),top+height*(1 - (ks[i].high-total_low)/(total_high-total_low))); 
	        D.lineTo(left+80+(width-80)/count*i+(width-80)/(2 * count),top+height*(1 -(ks[i].low-total_low)/(total_high-total_low)));
	        D.stroke();

	        D.beginPath();
	        D.fillStyle = tmp_color;
	        if(flag == 0)
	        {
	        	D.lineWidth=1.5;
	        	D.strokeRect(left+80+(width-80)/count*i+(width-80)/(2 * count)-k_width/2, top+height*(1 - (ks[i].close-total_low)/(total_high-total_low)), k_width, height*(ks[i].close-ks[i].open)/(total_high-total_low));
	        	if(ks[i].open == ks[i].close){
		            D.strokeRect(left+80+(width-80)/count*i+(width-80)/(2 * count)-k_width/2, top+height*(1 - (ks[i].close-total_low)/(total_high-total_low)), k_width, 1);
		        }
		        
		        D.strokeRect(left+80+(width-80)/count*i+(width-80) / (2 * count) - k_width / 2, top + height + height_spacing + height_volume * (1 - ks[i].volume / volume_high), k_width, height_volume * ks[i].volume / volume_high);
	        }else	
	        {
	        	D.fillRect(left+80+(width-80)/count*i+(width-80)/(2 * count)-k_width/2, top+height*(1 - (ks[i].close-total_low)/(total_high-total_low)), k_width, height*(ks[i].close-ks[i].open)/(total_high-total_low));
	        	if(ks[i].open == ks[i].close){
		            D.fillRect(left+80+(width-80)/count*i+(width-80)/(2 * count)-k_width/2, top+height*(1 - (ks[i].close-total_low)/(total_high-total_low)), k_width, 1);
		        }
		        
		        D.fillRect(left+80+(width-80)/count*i+(width-80) / (2 * count) - k_width / 2, top + height + height_spacing + height_volume * (1 - ks[i].volume / volume_high), k_width, height_volume * ks[i].volume / volume_high);
	        }
	        
	        D.stroke();
	    }
	    D.beginPath();
	    D.lineWidth = 3;
	    D.strokeStyle = ma5;
	    for(var i=0;i<count;i++){
	        if(ks[i].fiveavg >= total_low ){
	            D.lineTo(left+80+(width-80)/count*i+(width-80)/(2 * count),top+height*(1 -(ks[i].fiveavg-total_low)/(total_high-total_low)));
	        }
	    }
	    D.stroke();
	    D.lineWidth = 3;
	    D.beginPath();
	    D.strokeStyle = ma10;
	    for(var i=0;i<count;i++){
	        if(ks[i].tenavg >= total_low){
	            D.lineTo(left+80+(width-80)/count*i+(width-80)/(2 * count),top+height*(1 -(ks[i].tenavg-total_low)/(total_high-total_low)));
	        }
	    }
	    D.stroke();
	    D.lineWidth = 3;
	    D.beginPath();
	    D.strokeStyle = ma30;
	    for(var i=0;i<count;i++){
	        if(ks[i].thityavg >= total_low ){
	            D.lineTo(left+80+(width-80)/count*i+(width-80)/(2 * count),top+height*(1 -(ks[i].thityavg-total_low)/(total_high-total_low)));
	        }
	    }
	    D.stroke();
	    D.lineWidth = 3;
	    D.beginPath();
	    D.strokeStyle = ma60;
	    for(var i=0;i<count;i++){
	        if(ks[i].sixavg >= total_low ){
	            D.lineTo(left+80+(width-80)/count*i+(width-80)/(2 * count),top+height*(1 -(ks[i].sixavg-total_low)/(total_high-total_low)));
	        }
	    }
	    
	    
	    //K线Text
	    D.fillStyle = cText,
	    D.textAlign = "right",
	    D.font = "20px 宋体";
	    if(total_high.toFixed(2).length== 8)
	    {
		    D.fillText(total_high.toFixed(2), left + 80, top + 25);
		    D.fillText(((total_high + total_low) / 2).toFixed(2), left + 80, top + height / 2 + 25);
		    D.fillText(total_low.toFixed(2), left + 80, top + height);	
	    }
	    else if(total_high.toFixed(2).length== 7)
	    {
		    D.fillText(total_high.toFixed(2), left + 70, top + 25);
		    D.fillText(((total_high + total_low) / 2).toFixed(2), left + 70, top + height / 2 + 25);
		    D.fillText(total_low.toFixed(2), left + 70, top + height);	
	    }else if(total_high.toFixed(2).length== 6)
	    {
		    D.fillText(total_high.toFixed(2), left + 60, top + 20);
		    D.fillText(((total_high + total_low) / 2).toFixed(2), left + 60, top + height / 2 + 25);
		    D.fillText(total_low.toFixed(2), left + 60, top + height);	
	    }else if(total_high.toFixed(2).length== 5)
	    {
		    D.fillText(total_high.toFixed(2), left + 50, top + 25);
		    D.fillText(((total_high + total_low) / 2).toFixed(2), left + 50, top + height / 2 + 25);
		    D.fillText(total_low.toFixed(2), left + 50, top + height);	
	    }else if(total_high.toFixed(2).length== 4)
	    {
		    D.fillText(total_high.toFixed(2), left + 40, top + 25);
		    D.fillText(((total_high + total_low) / 2).toFixed(2), left + 40, top + height / 2 + 25);
		    D.fillText(total_low.toFixed(2), left + 40, top + height);	
	    }	
	    else
	    {
		    D.fillText(total_high.toFixed(2), left + 60, top + 18);
		    D.fillText(((total_high + total_low) / 2).toFixed(2), left + 60, top + height / 2 + 9);
		    D.fillText(total_low.toFixed(2), left +60, top + height);	
	    }	
	    //成交量text
	    if(volume_high > 1000000){
	        volume_unit = "亿";
	        if(((volume_high / 100000000).toFixed(2)+ volume_unit).length== 7)
	        {
	        	D.fillText((volume_high / 100000000).toFixed(2) + volume_unit, left +95, top + height + height_spacing + 18);
		        D.fillText((volume_high / 200000000).toFixed(2) + volume_unit, left +95, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +95, top + height + height_spacing + height_volume);	
	        }else if(((volume_high / 100000000).toFixed(2)+ volume_unit).length== 6)
	        {
	        	D.fillText((volume_high / 100000000).toFixed(2) + volume_unit, left +85, top + height + height_spacing + 18);
		        D.fillText((volume_high / 200000000).toFixed(2) + volume_unit, left +85, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +85, top + height + height_spacing + height_volume);	
	        }else if(((volume_high / 100000000).toFixed(2)+ volume_unit).length== 5)
	        {
	        	D.fillText((volume_high / 100000000).toFixed(2) + volume_unit, left +75, top + height + height_spacing + 18);
		        D.fillText((volume_high / 200000000).toFixed(2) + volume_unit, left +75, top + height + height_spacing + height_volume / 2 + 9);
		        //D.fillText(volume_unit, left +75, top + height + height_spacing + height_volume);	
	        }else if(((volume_high / 100000000).toFixed(2)+ volume_unit).length== 4)
	        {
	        	D.fillText((volume_high / 100000000).toFixed(2) + volume_unit, left +55, top + height + height_spacing + 18);
		        D.fillText((volume_high / 200000000).toFixed(2) + volume_unit, left +55, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +55, top + height + height_spacing + height_volume);	
	        }else
	        {
	        	D.fillText((volume_high / 100000000).toFixed(2) + volume_unit, left +40, top + height + height_spacing + 18);
		        D.fillText((volume_high / 200000000).toFixed(2) + volume_unit, left +40, top + height + height_spacing + height_volume / 2 + 9);
		        //D.fillText(volume_unit, left +40, top + height + height_spacing + height_volume);	
	        }			
	        
	    }
	    else{
	        volume_unit = "万";
	        if((volume_high / 10000).toFixed(2).length== 7)
	        {
		        D.fillText((volume_high / 10000).toFixed(2) + volume_unit, left +105, top + height + height_spacing + 18);
		        D.fillText((volume_high / 20000).toFixed(2) + volume_unit, left +105, top + height + height_spacing + height_volume / 2 + 9);
		        //D.fillText(volume_unit, left +85, top + height + height_spacing + height_volume);	
	        }else if((volume_high / 10000).toFixed(2).length== 6)
	        {
		        D.fillText((volume_high / 10000).toFixed(2) + volume_unit, left +95, top + height + height_spacing + 18);
		        D.fillText((volume_high / 20000).toFixed(2) + volume_unit, left +95, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +75, top + height + height_spacing + height_volume);	        	
	        }else if((volume_high / 10000).toFixed(2).length== 5)
	        {
		        D.fillText((volume_high / 10000).toFixed(2) + volume_unit, left +85, top + height + height_spacing + 18);
		        D.fillText((volume_high / 20000).toFixed(2) + volume_unit, left +85, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +65, top + height + height_spacing + height_volume);	        	
	        }else if((volume_high / 10000).toFixed(2).length== 4)
	        {
		        D.fillText((volume_high / 10000).toFixed(2) + volume_unit, left +65, top + height + height_spacing + 18);
		        D.fillText((volume_high / 20000).toFixed(2) + volume_unit, left +65, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +45, top + height + height_spacing + height_volume);	        	
	        }else 
	        {
		        D.fillText((volume_high / 10000).toFixed(2) + volume_unit, left +50, top + height + height_spacing + 18);
		        D.fillText((volume_high / 20000).toFixed(2) + volume_unit, left +50, top + height + height_spacing + height_volume / 2 + 9);
		       // D.fillText(volume_unit, left +30, top + height + height_spacing + height_volume);	        	
	        }			

	    }

	    D.stroke();
	}
	function drawKlineRect(b, y, x, w, h, d, e, g, a) {
	    b.beginPath(),
	    b.strokeStyle = cLine,
	    b.strokeRect(x - .5, y - .5, w, h),
	    b.strokeStyle = cLine;
	    for (var f = 1; f <= 1; f++) {// 三格
	        var l = y + h / 2 * f - .5;
	        b.moveTo(x, l);
	        b.lineTo(x + w, l);
	    }
	    for (var f = 1; f < 2; f++) {
	        var c;
	        c = x + w / 2 * f - .5;
	        b.moveTo(c, y);
	        b.lineTo(c, y + h);
	    }
	    b.strokeStyle = cLine,
	    b.strokeRect(x - .5, y + h + e - .5, w, d),
	    b.strokeStyle = cLine;
	    for (var f = 1; f <= 1; f++) {
	        var l = y + h + e + d / 2 * f - .5;
	        b.moveTo(x, l);
	        b.lineTo(x + w, l);
	    }
	    for (var f = 1; f < 2; f++) {
	        var c;
	        c = x + w / 2 * f - .5;
	        b.moveTo(c, y + h + e);
	        b.lineTo(c, y + h + e + d);
	    }
	    b.stroke();
	}
	
	var common = {
			"formatMinData":formatMinData,
			"formatKlineData":formatKlineData,
			"kds":kds
		
		
	};
	module.exports = common;
	
});