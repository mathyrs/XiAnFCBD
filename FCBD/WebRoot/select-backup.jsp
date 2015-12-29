<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
div#select_head{position:absolute;left:18px;width:760px;top:0px;height:200px;color:white;margin:2px;border:3px solid gray;background-color:#F0F8FF;z-index:2;}
div#select_left1{position:relative;height:30px;width:60px;top:80px;left:690px;margin:0px;border:0px solid gray;text-align:center;z-index:1;}
div#select_left2{position:relative;height:30px;width:62px;top:-120px;left:10px;margin:0px;border:0px solid gray;text-align:center;z-index:1;}
div#select_left3{position:relative;height:26px;width:190px;top:-280px;left:565px;margin:0px;border:0px solid gray;color:black;text-align:center;z-index:1;}
div#time{position:absolute;height:30px;width:210px;top:35px;left:100px;margin:0px;border:0px solid gray;z-index:3;}
div#select_contest{position:absolute;top:216px;left:16px;width:764;height:426px;margin:0px;border:4px solid gray;color:white;background-color:white;z-index:1;}
span{color:red}
.file{ position:relative; height:26px; filter:alpha(opacity:30);opacity: 50;width:60px; }
</style>

<script type="text/javascript" src="/FCBD/resources/js/AjaxClass.js"></script>

<script type="text/javascript">
function HS_DateAdd(interval,number,date){
	number = parseInt(number);
	if (typeof(date)=="string"){var date = new Date(date.split("-")[0],date.split("-")[1],date.split("-")[2])}
	if (typeof(date)=="object"){var date = date}
	switch(interval){
	case "y":return new Date(date.getFullYear()+number,date.getMonth(),date.getDate()); break;
	case "m":return new Date(date.getFullYear(),date.getMonth()+number,checkDate(date.getFullYear(),date.getMonth()+number,date.getDate())); break;
	case "d":return new Date(date.getFullYear(),date.getMonth(),date.getDate()+number); break;
	case "w":return new Date(date.getFullYear(),date.getMonth(),7*number+date.getDate()); break;
	}
}
function checkDate(year,month,date){
	var enddate = ["31","28","31","30","31","30","31","31","30","31","30","31"];
	var returnDate = "";
	if (year%4==0){enddate[1]="29"}
	if (date>enddate[month]){returnDate = enddate[month]}else{returnDate = date}
	return returnDate;
}
function WeekDay(date){
	var theDate;
	if (typeof(date)=="string"){theDate = new Date(date.split("-")[0],date.split("-")[1],date.split("-")[2]);}
	if (typeof(date)=="object"){theDate = date}
	return theDate.getDay();
}
function HS_calender(){
	var lis = "";
	var style = "";
	/*可以把下面的css剪切出去独立一个css文件zzjs.net*/
	style +="<style type='text/css'>";
	style +=".calender { width:170px; height:auto; font-size:12px; margin-right:14px; background:url(calenderbg.gif) no-repeat right center #fff; border:1px solid #397EAE; padding:1px}";
	style +=".calender ul {list-style-type:none; margin:0; padding:0;}";
	style +=".calender .day { background-color:#EDF5FF; height:20px;}";
	style +=".calender .day li,.calender .date li{ float:left; width:14%; height:20px; line-height:20px; text-align:center}";
	style +=".calender li a { text-decoration:none; font-family:Tahoma; font-size:11px; color:#333}";
	style +=".calender li a:hover { color:#f30; text-decoration:underline}";
	style +=".calender li a.hasArticle {font-weight:bold; color:#f60 !important}";
	style +=".lastMonthDate, .nextMonthDate {color:#bbb;font-size:11px}";
	style +=".selectThisYear a, .selectThisMonth a{text-decoration:none; margin:0 2px; color:#000; font-weight:bold}";
	style +=".calender .LastMonth, .calender .NextMonth{ text-decoration:none; color:#000; font-size:18px; font-weight:bold; line-height:16px;}";
	style +=".calender .LastMonth { float:left;}";
	style +=".calender .NextMonth { float:right;}";
	style +=".calenderBody {clear:both}";
	style +=".calenderTitle {text-align:center;height:20px; line-height:20px; clear:both}";
	style +=".today { background-color:#ffffaa;border:1px solid #f60; padding:2px}";
	style +=".today a { color:#f30; }";
	style +=".calenderBottom {clear:both; border-top:1px solid #ddd; padding: 3px 0; text-align:left}";
	style +=".calenderBottom a {text-decoration:none; margin:2px !important; font-weight:bold; color:#000}";
	style +=".calenderBottom a.closeCalender{float:right}";
	style +=".closeCalenderBox {float:right; border:1px solid #000; background:#fff; font-size:9px; width:11px; height:11px; line-height:11px; text-align:center;overflow:hidden; font-weight:normal !important}";
	style +="</style>";
	var now;
	if (typeof(arguments[0])=="string"){
		selectDate = arguments[0].split("-");
		var year = selectDate[0];
		var month = parseInt(selectDate[1])-1+"";
		var date = selectDate[2];
		now = new Date(year,month,date);
	}else if (typeof(arguments[0])=="object"){
		now = arguments[0];
	}
	var lastMonthEndDate = HS_DateAdd("d","-1",now.getFullYear()+"-"+now.getMonth()+"-01").getDate();
	var lastMonthDate = WeekDay(now.getFullYear()+"-"+now.getMonth()+"-01");
	var thisMonthLastDate = HS_DateAdd("d","-1",now.getFullYear()+"-"+(parseInt(now.getMonth())+1).toString()+"-01");
	var thisMonthEndDate = thisMonthLastDate.getDate();
	var thisMonthEndDay = thisMonthLastDate.getDay();
	var todayObj = new Date();
	today = todayObj.getFullYear()+"-"+todayObj.getMonth()+"-"+todayObj.getDate();
	for (i=0; i<lastMonthDate; i++){  // Last Month's Date
		lis = "<li class='lastMonthDate'>"+lastMonthEndDate+"</li>" + lis;
		lastMonthEndDate--;
	}
	for (i=1; i<=thisMonthEndDate; i++){ // Current Month's Date
		if(today == now.getFullYear()+"-"+now.getMonth()+"-"+i){
			var todayString = now.getFullYear()+"-"+(parseInt(now.getMonth())+1).toString()+"-"+i;
			lis += "<li><a href=javascript:void(0) class='today' onclick='_selectThisDay(this)' title='"+now.getFullYear()+"-"+(parseInt(now.getMonth())+1)+"-"+i+"'>"+i+"</a></li>";
		}else{
			lis += "<li><a href=javascript:void(0) onclick='_selectThisDay(this)' title='"+now.getFullYear()+"-"+(parseInt(now.getMonth())+1)+"-"+i+"'>"+i+"</a></li>";
		}
	}
	var j=1;
	for (i=thisMonthEndDay; i<6; i++){  // Next Month's Date
		lis += "<li class='nextMonthDate'>"+j+"</li>";
		j++;
	}
	lis += style;
	var CalenderTitle = "<a href='javascript:void(0)' class='NextMonth' onclick=HS_calender(HS_DateAdd('m',1,'"+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDate()+"'),this) title='Next Month'>»</a>";
	CalenderTitle += "<a href='javascript:void(0)' class='LastMonth' onclick=HS_calender(HS_DateAdd('m',-1,'"+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDate()+"'),this) title='Previous Month'>«</a>";
	CalenderTitle += "<span class='selectThisYear'><a href='javascript:void(0)' onclick='CalenderselectYear(this)' title='Click here to select other year' >"+now.getFullYear()+"</a></span>年<span class='selectThisMonth'><a href='javascript:void(0)' onclick='CalenderselectMonth(this)' title='Click here to select other month'>"+(parseInt(now.getMonth())+1).toString()+"</a></span>月";
	if (arguments.length>1){
		arguments[1].parentNode.parentNode.getElementsByTagName("ul")[1].innerHTML = lis;
		arguments[1].parentNode.innerHTML = CalenderTitle;
	}else{
		var CalenderBox = style+"<div class='calender'><div class='calenderTitle'>"+CalenderTitle+"</div><div class='calenderBody'><ul class='day'><li>日</li><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li></ul><ul class='date' id='thisMonthDate'>"+lis+"</ul></div><div class='calenderBottom'><a href='javascript:void(0)' class='closeCalender' onclick='closeCalender(this)'>×</a><span><span><a href=javascript:void(0) onclick='_selectThisDay(this)' title='"+todayString+"'>Today</a></span></span></div></div>";
		return CalenderBox;
	}
}
function _selectThisDay(d){
	var boxObj = d.parentNode.parentNode.parentNode.parentNode.parentNode;
		boxObj.targetObj.value = d.title;
		boxObj.parentNode.removeChild(boxObj);
}
function closeCalender(d){
	var boxObj = d.parentNode.parentNode.parentNode;
		boxObj.parentNode.removeChild(boxObj);
}
function CalenderselectYear(obj){
		var opt = "";
		var thisYear = obj.innerHTML;
		for (i=1970; i<=2020; i++){
			if (i==thisYear){
				opt += "<option value="+i+" selected>"+i+"</option>";
			}else{
				opt += "<option value="+i+">"+i+"</option>";
			}
		}
		opt = "<select onblur='selectThisYear(this)' onchange='selectThisYear(this)' style='font-size:11px'>"+opt+"</select>";
		obj.parentNode.innerHTML = opt;
}
function selectThisYear(obj){
	HS_calender(obj.value+"-"+obj.parentNode.parentNode.getElementsByTagName("span")[1].getElementsByTagName("a")[0].innerHTML+"-1",obj.parentNode);
}
function CalenderselectMonth(obj){
		var opt = "";
		var thisMonth = obj.innerHTML;
		for (i=1; i<=12; i++){
			if (i==thisMonth){
				opt += "<option value="+i+" selected>"+i+"</option>";
			}else{
				opt += "<option value="+i+">"+i+"</option>";
			}
		}
		opt = "<select onblur='selectThisMonth(this)' onchange='selectThisMonth(this)' style='font-size:11px'>"+opt+"</select>";
		obj.parentNode.innerHTML = opt;
}
function selectThisMonth(obj){
	HS_calender(obj.parentNode.parentNode.getElementsByTagName("span")[0].getElementsByTagName("a")[0].innerHTML+"-"+obj.value+"-1",obj.parentNode);
}
function HS_setDate1(inputObj){
	var calenderObj = document.createElement("span");
	calenderObj.innerHTML = HS_calender(new Date());
	calenderObj.style.position = "absolute";
	calenderObj.style.left="54px";
	calenderObj.style.top="28px";
	calenderObj.targetObj = inputObj;
	inputObj.parentNode.insertBefore(calenderObj,inputObj.nextSibling);
}
function HS_setDate2(inputObj){
	var calenderObj = document.createElement("span");
	calenderObj.innerHTML = HS_calender(new Date());
	calenderObj.style.position = "absolute";
	calenderObj.style.left="195px";
	calenderObj.style.top="28px";
	calenderObj.targetObj = inputObj;
	inputObj.parentNode.insertBefore(calenderObj,inputObj.nextSibling);
}
  </script>
  <script>
  function check_name1(str1){				
  if(str1=="")				
  {					
  alert("单位不得为空！");				
  }		
  }
  function check_name2(str2){				
  if(str2=="")				
  {					
  alert("开始时间不得为空！");				
  }	
  }
  function check_name3(str3){				
  if(str3=="")				
  {					
  alert("结束时间不得为空！");				
  }			
  }
  function selectnew(){
  var str1 = document.getElementById("danwei").value;
  var str2 = document.getElementById("startTime").value;
  var str3 = document.getElementById("endTime").value;
  check_name1(str1);
  check_name2(str2);
  check_name3(str3);
  query();
  }
  
  function getXmlhttp() {
  	XmlHttp = false;
    try
    {
        XmlHttp = new XMLHttpRequest();        //FireFox专有
    }
    catch(e)
    {
        try
        {
            XmlHttp = new ActiveXObject("MSXML2.XMLHTTP");
        }
        catch(e2)
        {
            try
            {
                XmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch(e3)
            {
                alert("你的浏览器不支持XMLHTTP对象，请升级到IE9以上版本！");
                XmlHttp = false;
            }
        }
    }
  }
  
  //获得并整理表单数据
  function getRequestBody(oForm)  
     {  
        var aParams=new Array();  
        for(var i=0;i<oForm.elements.length;i++)  
        {  
          var sParam=encodeURIComponent(oForm.elements[i].name);  
          sParam+="=";  
          sParam+=encodeURIComponent(oForm.elements[i].value);  
          aParams.push(sParam);  
        }  
        return aParams.join("&");  
     }  
  function query()  
     {  
      try  
      {  
         getXmlhttp(); 
         var url="servlet/DataQueryServlet";  
         var postedData=getRequestBody(document.forms["form1"]);  
          
         XmlHttp.open("post",url,true);  
         XmlHttp.setRequestHeader("content-length",postedData.length);//post提交设置项  
         XmlHttp.setRequestHeader("content-type","application/x-www-form-urlencoded");//post提交设置项  
         XmlHttp.onreadystatechange =onComplete;  
         //将名值对发送到服务器  
         XmlHttp.send(postedData);  
       }  
       catch(e)  
       {  
         alert(e.message);  
       }   
     }  
     function onComplete()  
     {  
        if (XmlHttp.readyState==4&&XmlHttp.status==200) {  
            //显示结果  
            document.getElementById("select_contest").innerText=xmlHttp.responseText;  
        } else {
			document.getElementById("select_contest").innerText="Loading...";  
		}  
     }  
  
  
  </script>
</head>
<body>
<div id = "select_head" >
<form style="position:relative;width:230px;"id="form1" action="">
<div id = "select_left1">
<input name="查询" type="button"  value="查询" onclick="selectnew()" style="position:relative;top:-10px;width:60px;height:35px;background:#003333;color:white;"/>
</div>

<input type="hidden" name="requestType" value="query"> 
<p style="position:relative;color:black;left:10px;top:-30px;">单    位<span> *</span>:<input type="text" id="danwei" name="orgID"  onblur="check_name1(this.value);" style="width:120px;font-size:18px;" name="user" /></p>
<p style="position:relative;color:black;left:204px;top:-74px;">机    型:<select name="planeType" style="width:120px;font-size:18px;">
<option value="" style="color:#c2c2c2;">---请选择---</option> 
<option value="歼10-A">歼10-A</option>  
<option value="歼10-B">歼10-B</option>
<option value="歼10-D">歼10-D</option>
</select></p>
<p style="position:relative;color:black;left:392px;top:-119px;">架    次:<input type="text" id="sortie" name="sortie" style="width:120px;font-size:18px;" name="user" /></p>
<div id="time">
<p style="position:relative;color:black;left:-90px;top:30px;">时    间 <span>*</span>:<input type="text" id="startTime" name="startTime" style="width:100px;font-size:18px;" name="user"  onfocus="HS_setDate1(this)"/> —— <input type="text" id="endTime" name="endTime" style="position:relative;top:-28px;left:195px;width:100px;font-size:18px;" name="user"  onfocus="HS_setDate2(this)"/></p>
</div>
<p style="position:relative;color:black;left:324px;top:-99px;">编    号:<input type="text" id="user" name="planeID" style="width:120px;font-size:18px;" name="user" /></p>
<p style="position:relative;color:black;left:500px;top:-145px;">设    备:<input type="text" id="user" name="deviceName" style="width:120px;font-size:18px;" name="user" /></p>
</form>
<div id = "select_left2">
<p><form id="form2" action="FCBD/servlet/DataQueryServlet"><input type="hidden" name="requestType" value="hive"><input type="text" id="user" name="HiveQL" style="position:relative;top:0px;left:20px; width:630px;font-size:22px;" name="user" /></form><input name="userName" type="button" value="HiveQL" style="position:relative;top:-52px;left:680px;width:60px;height:35px;background:#003333;color:white;"/>
</p>
</div>
<div id = "select_left3">
（多个对象以逗号隔开）
</div>
</div>
<div id = "select_contest">
</div>
</body>
</html>