<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<style type="text/css">
*{margin:0px;padding:0px;}
div#select_head{position:absolute;left:10px;width:760px;top:5px;height:200px;color:white;margin:2px;border:3px solid gray;background-color:#F0F8FF;z-index:2;}
div#select_left1{position:relative;height:20px;width:50px;top:76px;left:690px;margin:0px;border:0px solid gray;text-align:center;z-index:1;}
div#select_left2{position:relative;height:30px;width:62px;top:-120px;left:10px;margin:0px;border:0px solid gray;text-align:center;z-index:1;}
div#select_left3{position:relative;height:26px;width:190px;top:-158px;left:565px;margin:0px;border:0px solid gray;color:black;text-align:center;z-index:1;}
div#time{position:absolute;height:30px;width:210px;top:35px;left:100px;margin:0px;border:0px solid gray;z-index:3;}
div#select_contest{position:absolute;top:218px;left:10px;width:762;height:426px;margin:0px;border:4px solid gray;color:white;background-color:white;z-index:1;overflow-y:auto;}
div#pagechange{position:absolute;width:763px;height:40px;top:607px;left:12px;margin:1px;border:0px solid black;background-color:#CCFFFF;z-index:1;}
div#currentpagenumber{position:absolute;top:10px;left:350px;color:black;z-index:1;}
span{color:red}
.file{ position:relative; height:26px; filter:alpha(opacity:30);opacity: 50;width:60px; }

</style>

<script type="text/javascript" src="/FCBD/resources/js/lhgcore.js"></script>
<script type="text/javascript" src="/FCBD/resources/js/lhgcalendar.js"></script>

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
  function selectnew(oform){
  var str1 = document.getElementById("danwei").value;
  var str2 = document.getElementById("startTime").value;
  var str3 = document.getElementById("endTime").value;
  check_name1(str1);
  check_name2(str2);
  check_name3(str3);
  hiveQLGenerate(oform);
  query(oform);
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
  function query(oform)  
     {  
      try  
      {  
         getXmlhttp(); 
         var url="servlet/DataQueryServlet";  
        // var postedData=getRequestBody(document.forms["form1"]);  
         var postedData=getRequestBody(oform);
		 
         XmlHttp.open("post",url,true);  
         XmlHttp.setRequestHeader("content-length",postedData.length);//post提交设置项  
         XmlHttp.setRequestHeader("content-type","application/x-www-form-urlencoded");//post提交设置项  
         XmlHttp.onreadystatechange =onComplete;  
         //将名值对发送到服务器  
         XmlHttp.send(postedData);  
		 document.getElementById("select_contest").innerHTML="<p style='color:black;'>loading...</p>";
       }  
       catch(e)  
       {  
         alert(e.message);  
       }   
     }  
     //上下页翻页ajax;
     function pagequeryup()  
     {  
      try  
      {  
         getXmlhttp(); 
         var url="servlet/DataQueryServlet?method=previous";  
         
		 
         XmlHttp.open("get",url,true);  
         XmlHttp.onreadystatechange = onComplete;  
         //将名值对发送到服务器  
         XmlHttp.send(null);  
       }  
       catch(e)  
       {  
         alert(e.message);  
       }   
     }  
     function pagequerydown()  
     {  
      try  
      {  
         getXmlhttp(); 
         var url="servlet/DataQueryServlet?method=next";  
         
		 
         XmlHttp.open("get",url,true);  
         XmlHttp.onreadystatechange = onComplete;    
         //将名值对发送到服务器  
         XmlHttp.send(null);  
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
            document.getElementById("select_contest").innerHTML=XmlHttp.responseText;  
        } 
     } 
	 
	 function hiveQLGenerate(oform)  
     {  
      try  
      {  
         getXmlhttp(); 
         var url="servlet/DataQueryServlet?method=HiveQL";  
        // var postedData=getRequestBody(document.forms["form1"]);  
         var postedData=getRequestBody(oform);
		 
         XmlHttp.open("post",url,false);  
         XmlHttp.setRequestHeader("content-length",postedData.length);//post提交设置项  
         XmlHttp.setRequestHeader("content-type","application/x-www-form-urlencoded");//post提交设置项  
         XmlHttp.onreadystatechange = function(){
		 	if (XmlHttp.readyState==4&&XmlHttp.status==200) {  
            //显示结果  
            document.getElementById("HiveQL").value=XmlHttp.responseText;  
        	} 
		 };  
         //将名值对发送到服务器  
         XmlHttp.send(postedData);  
		 
       }  
       catch(e)  
       {  
         alert(e.message);  
       }   
     } 
	 
	  
	var curWwwPath = window.document.location.href;
//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8083
	var localhostPath = curWwwPath.substring(0, pos);
//获取带"/"的项目名，如：/uimcardprj
     var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
  //function picture(){
  
 //window.showModalDialog(projectName + "/picture.jsp","","dialogWidth=600px;dialogHeight=500px;dialogTop=200px;dialogLeft=400px;");
//}

var embObj;
function checkImg(theURL,winName){
  // 对象是否已创建
  if (typeof(embObj) == "object"){
    // 是否已取得了图像的高度和宽度
    if ((embObj.width != 0) && (embObj.height != 0))
      // 根据取得的图像高度和宽度设置弹出窗口的高度与宽度，并打开该窗口
      // 其中的增量 20 和 30 是设置的窗口边框与图片间的间隔量
      OpenFullSizeWindow(theURL,winName, ",width=" + (embObj.width+20) + ",height=" + (embObj.height+30));
    else
      // 因为通过 Image 对象动态装载图片，不可能立即得到图片的宽度和高度，所以每隔100毫秒重复调用检查
      setTimeout("checkImg('" + theURL + "','" + winName + "')", 100);
  }
}


function OpenFullSizeWindow(theURL,winName,features) {
  var aNewWin, sBaseCmd;
  //theURL = '/FCBD/resources/img/10.pdf';
  // 弹出窗口外观参数
  sBaseCmd = "toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,";
  // 调用是否来自 checkImg 
  //if (features == null || features == ""){
    // 创建图像对象
   // embObj = new Embed();
    // 设置图像源
   // embObj.src = theURL;
    // 开始获取图像大小
   // checkImg(theURL, winName)
  //}
  //else{
    // 打开窗口
    //aNewWin = window.open(theURL,winName, sBaseCmd + features);
	aNewWin = window.open(theURL,'new','height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    // 聚焦窗口
    aNewWin.focus();
  //}
}

</script>
<script>
 function pageup()
     {
     	pagequeryup();
    	Pagego(parseInt(document.getElementById("currentnumber").innerHTML)-1);
     }
     function pagedown()
     {
     	pagequerydown();
     	Pagego(parseInt(document.getElementById("currentnumber").innerHTML)+1);
     }
     function Pagego(currentpagenumber)
     {
      if(currentpagenumber<1) {alert("到了首页");return;}  
     // if(currentpagenumber>3) {alert("到了第三页了");return;}  
      //if(currentpagenumber<10)  currentpagenumber = "0"+currentpagenumber;  
      else {
      //document.getElementById("currentpagenumber").innerHTML ="第"+currentpagenumber +"页/共123页";
      document.getElementById("currentnumber").innerHTML =currentpagenumber;
      window.onload=Pagego;
     }
     }
</script>
</head>
<body>
	<% 
	  String imageFileName = (String)(session.getAttribute("imageFileName"));
	  String filePath = request.getContextPath() + "/resources/img/";
	//if (null == user) {
		//session.invalidate();
		//request.getRequestDispatcher(response.encodeRedirectURL("/servlet/LoginUIServlet")).forward(request, response);
	//}
   %>
	
<div id = "select_head" >
<form style="position:relative;width:210px;"id="form1" action="">

<input name="查询" type="button"  value="查询" onclick="selectnew(this.parentNode)" style="position:relative;top:80px;left:690px;width:60px;height:35px;background:#003333;color:white;z-index:1;"/>


<input type="hidden" name="requestType" value="query"> 
<p style="position:relative;color:black;left:10px;top:-6px;z-index:4;">单    位<span> *</span>:<input type="text" id="danwei" name="orgID" style="width:120px;font-size:18px;" name="user" /></p>
<p style="position:relative;color:black;left:204px;top:-30px;">机    型:<select name="planeType" style="width:120px;font-size:18px;">
<option value="" style="color:#c2c2c2;">---请选择---</option> 
<option value="歼10-A">歼10-A</option>  
<option value="歼10-B">歼10-B</option>
<option value="歼10-D">歼10-D</option>
</select></p>
<p style="position:relative;color:black;left:392px;top:-55px;">架    次:<input type="text" id="sortie" name="sortie" style="width:120px;font-size:18px;" name="user" /></p>
<div id="time">
<p style="position:absolute;color:black;left:-92px;top:60px;">时    间 <span>*</span>:</p>
<input type="text" id="startTime" name="startTime" style="position:absolute;top:60px;left:-38px;width:100px;font-size:18px;" name="user"  onclick="J.calendar.get();" />
<p style="position:absolute;top:60px;left:62px;color:black;">——</p>
<input type="text" id="endTime" name="endTime" style="position:absolute;top:60px;left:92px;width:100px;font-size:18px;" name="user"  onclick="J.calendar.get();"/>
</div>
<p style="position:relative;color:black;left:324px;top:-15px;">编    号:<input type="text" id="user" name="planeID" style="width:120px;font-size:18px;" name="user" /></p>
<p style="position:relative;color:black;left:500px;top:-39px;">设    备:<input type="text" id="user" name="deviceName" style="width:120px;font-size:18px;" name="user" /></p>
</form>
<div id = "select_left2">
<p><form id="form2" action=""><input type="hidden" name="requestType" value="hive">
<input type="text" id="HiveQL" name="HiveQL" style="position:relative;top:120px;left:20px; width:550px;height:30px;font-size:16px;h" name="user" />
<input name="userName" type="button" value="HiveQL" onclick="selectnew(this.parentNode)" style="position:relative;top:86px;left:600px;width:60px;height:35px;background:#003333;color:white;"/>
</form>
<input name="userName1" type="button" value="画图" onclick="OpenFullSizeWindow('<%= filePath + imageFileName%>' + '.pdf','','');return false" style="position:relative;top:50px;left:680px;width:60px;height:35px;background:#003333;color:white;"/>
</p>
</div>
<div id = "select_left3">
<p>（多个对象以逗号隔开）</p>
</div>
</div>
<div id = "select_contest">
	<!-- <embed width="800" height="600" src="/FCBD/resources/img/10.pdf" /> -->	
</div>
<div id="pagechange">
<input name="userName" type="button" value="上一页" onclick="pageup()" style="position:relative;top:3px;left:260px;width:60px;height:35px;background:#003333;color:white;z-index:1;"/>
<div id="currentpagenumber">
第<span id="currentnumber" style="color:black;">1</span>页
</div>
<input name="userName" type="button" value="下一页" onclick="pagedown()" style="position:relative;top:3px;left:360px;width:60px;height:35px;background:#003333;color:white;z-index:1;"/>
</div>
</body>
</html>