<%@ page language="java" import="java.util.*, com.inspur.domain.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
div#container{position:absolute;width:1200px;height:766px;margin:0px;left:75px;border:2px solid gray;line-height:100%;}
div#header{padding:1.4em;color:white;background-color:gray;clear:left;text-align:center;}
div#table1{float:left;width:500px;height:27px;margin:3px;border:1px solid black;color:#324143;background-color:#324143;clear:left;}
div#table2{position:relative;top:-32px;left:505px;width:400px;height: 27px;margin:2px;border:1px solid black;color:white;background-color:white;clear:left; z-index:1;}
div#table3{position:relative;top:-63px;left:910px;width:284px;height: 27px;margin:2px;border:1px solid black;color:white;background-color:white;clear:left;}
div#footer{position:absolute;top:668px;height:80px;width:1185px;padding:0.5em;color:white;background-color:gray;clear:left;text-align:center;}
div#footerter{position:relative;top:25px;left:-400px;}
h1#header{padding:0;margin:0;}
div#left{float:left;position:relative;top:-80px;width:200px;height: 560px;margin:2px;border:2px solid black;overflow-y:auto;}
div#content{position:relative;top:-78px;width:987px;height: 560px;margin-left:208px;border:2px solid black;}
div#contentmin{position:relative;left:-210px;top:0px;width:987px;height: 528px;margin-left:208px;border:0px solid black;overflow-y:auto;}

div#background{width: 1116px;height: 500px;border: 1px solid black;}
p#teshu {color:blue}
div#kuang{position:relative;top:1px;width:200px;height:26px;}
div#an{position:relative;top:-25px;left:234px;width:20px;height:26px;}
td {text-align:center;cursor:pointer;}
ul{ z-index:1;}
li{cursor:pointer;}


</style>
<style>
#jsddm
{	margin: 0;
	padding: 0}

	#jsddm li
	{	float: left;
		list-style: none;
		font: 12px Tahoma, Arial}

	#jsddm li a
	{	display: block;
		background: #324143;
		padding: 5px 12px;
		text-decoration: none;
		border-right: 0px solid white;
		width: 59.3px;
		color: #EAFFED;
		white-space: nowrap}

	#jsddm li a:hover
	{	background: #24313C}
		
		#jsddm li ul
		{	margin: 0;
			padding: 0;
			position: absolute;
			visibility: hidden;
			border-top: 1px solid white}
		
			#jsddm li ul li
			{	float: none;
				display: inline}
			
			#jsddm li ul li a
			{	width: auto;
				background: #CCFFCC;
				color: #24313C}
			
			#jsddm li ul li a:hover
			{	background: #8EA344}
			</style>


<script src="/FCBD/resources/js/jquery.min.js" type="text/javascript"></script>
<link rel="StyleSheet" href="/FCBD/resources/dtree/dtree.css" type="text/css" />
<script type="text/javascript" src="/FCBD/resources/dtree/dtree.js"></script>

  <script type="text/javascript">
  	
var curWwwPath = window.document.location.href;
//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8083
var localhostPath = curWwwPath.substring(0, pos);
//获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	
var timeout         = 500;
var closetimer		= 0;
var ddmenuitem      = 0;

function jsddm_open()
{	jsddm_canceltimer();
	jsddm_close();
	ddmenuitem = $(this).find('ul').eq(0).css('visibility', 'visible');}

function jsddm_close()
{	if(ddmenuitem) ddmenuitem.css('visibility', 'hidden');}

function jsddm_timer()
{	closetimer = window.setTimeout(jsddm_close, timeout);}

function jsddm_canceltimer()
{	if(closetimer)
	{	window.clearTimeout(closetimer);
		closetimer = null;}}

$(document).ready(function()
{	$('#jsddm > li').bind('mouseover', jsddm_open);
	$('#jsddm > li').bind('mouseout',  jsddm_timer);
	$('#jsddm > li').bind('onclick', jsddm_close);});


  </script>



<script type = 'text/javascript'>
//单击选中表格行
var curRow; //全局行号  
var curRowId; //选中行的记录信息的ID  
var curColor; 
function clickRow(tr){  
if(curRow){  
curRow.bgColor = curColor;  
curColor = tr.bgColor;  
tr.bgColor = "#FFE9B3"; 
tableb.style.display="none";
hidetablea.style.display="";
}else{  
curColor = tr.bgColor;  
tr.bgColor = "FFE9B3"; 
tableb.style.display="none";
hidetablea.style.display="";
}  
curRow = tr;  
curRowId = tr.id;  
//alert(tr.cells[1].innerText);
//tableb.style.display="none";
//hidetablea.style.display="";
}  
//function onmouseout(tr){
//table.tr.style.backgroundColor="";
//}
//function onmouseover(tr){
//table.tr.style.backgroundColor="#FFE9B3";
//}
function clickRow1(tr){  
if(curRow){  
curRow.bgColor = curColor;  
curColor = tr.bgColor;  
tr.bgColor = "#FFE9B3"; 
tableb.style.display="none";
hidetablea.style.display="none";
hidetableb.style.display="";
}else{  
curColor = tr.bgColor;  
tr.bgColor = "FFE9B3"; 
tableb.style.display="none";
hidetablea.style.display="none";
hidetableb.style.display="";
}  
}

function setText(str) {
	document.getElementById("tableaa").style.display = "";
	document.getElementById("tablea").style.display = "none";
	document.getElementById("tableb").innerHTML = str;
	show();
}

function setTable(str) {
	values = str.split("<br/>");
	var text = "";
	if (values[0].indexOf(";") == -1) {
		for (i = 0; i < values.length - 1; i++) {
			text += '<tr id = "' + 'tr' + values[i] + '" onmouseout="this.style.backgroundColor=&quot;&quot;;" onmouseover="this.style.backgroundColor=&quot;#FFE9B3&quot;;" ondblclick = "javascript:tree.getChildren(this.id.substring(2),setText);"><td width=260px>' + values[i] + ' </td><td width=120px>2015/11/01</td><td width=120px>军区级</td><td width=120px>空</td></tr>';
		}
	} else {
		for (i = 0; i < values.length - 1; i++) {
			array = values[i].split(";");
			text += '<tr id = "' + 'tr' + array[1] + '" onmouseout="this.style.backgroundColor=&quot;&quot;;" onmouseover="this.style.backgroundColor=&quot;#FFE9B3&quot;;" ondblclick = "javascript:tree.getChildren(this.id.substring(2),setTable);"><td width=260px>' + array[0] + ' </td><td width=120px>2015/11/01</td><td width=120px>军区级</td><td width=120px>空</td></tr>';
		}
		
	}
	document.getElementById("tableaa").style.display = "none";
	document.getElementById("tablea").style.display = "";
	document.getElementById("tableb").innerHTML = text;
	show();
}

</script>  
<!--弹出窗口 -->
<script type = 'text/javascript'>
function tanchu(){
 jsddm_close();
 window.showModalDialog(projectName + "/importDialog.jsp","","dialogWidth=596px;dialogHeight=448px;dialogTop=200px;dialogLeft=400px;");
}
function select(){
 jsddm_close();
 window.showModalDialog(projectName + "/select.jsp","","dialogWidth=800px;dialogHeight=700px;dialogTop=200px;dialogLeft=400px;");
}
function tuichu(){
window.location.href=localhostPath + projectName + "/servlet/LoginUIServlet";
}
</script>

</head>
<body>  

<% 
	//User user = (User)(session.getAttribute("user"));
	//if (null == user) {
		//session.invalidate();
		//request.getRequestDispatcher(response.encodeRedirectURL("/servlet/LoginUIServlet")).forward(request, response);
	//}
%>

<div id="container" onclick="show()" onmouseover="show()">
<div id="header">
<h1 id="header"><img class="normal" src="/FCBD/resources/img/u10.png" style="position:absolute;top:-4px;left:280px;height:70px;width:120px;"/>军用飞参大数据管控平台</h1>
</div>

<div id="table1">
<div id="all">
<ul id="jsddm">
	<li ><a href="#">文件</a>
		<ul>
			<li id="daoru" onclick="tanchu()" ><a>数据导入</a></li>
		 	<li id="chaxun" onclick="select()"><a>数据查询</a></li> 
			<li><a href="#">数据删除</a></li>
			<li onclick="tuichu()"><a>退出</a></li>
		</ul>
	</li>
	<li ><a href="#">编辑</a>
	<!--	<ul>
			<li><a href="#">Slide Effect</a></li>
			<li><a href="#">Fade Effect</a></li>
			<li><a href="#">Opacity Mode</a></li>
			<li><a href="#">Drop Shadow</a></li>
			<li><a href="#">Semitransparent</a></li>
		</ul>-->
	</li>
	<li><a href="#">视图</a>
	<!--	<ul>
			<li><a href="#">Slide Effect</a></li>
			<li><a href="#">Fade Effect</a></li>
			<li><a href="#">Opacity Mode</a></li>
			<li><a href="#">Drop Shadow</a></li>
		</ul>-->
	</li>
	<li><a href="#">工具</a>
	<!--	<ul>
			<li><a href="#">Slide Effect</a></li>
			<li><a href="#">Opacity Mode</a></li>
			<li><a href="#">Drop Shadow</a></li>
		</ul>-->
	</li>
	<li><a href="#">（备用）</a>
	<!--	<ul>
			<li><a href="#">Slide Effect</a></li>
			<li><a href="#">Opacity Mode</a></li>
			<li><a href="#">Drop Shadow</a></li>
		</ul>-->
	</li>
	<li><a href="#">帮助</a>
	<!--	<ul>
			<li><a href="#">Slide Effect</a></li>
			<li><a href="#">Opacity Mode</a></li>
			<li><a href="#">Drop Shadow</a></li>
		</ul>-->
	</li>
</ul>
</div>

</div>
<div class="clear"> </div>

<div id="table2">
<p id="currentUser" style="position:relative;top:-10px;left:0px;color:black; text-algin:center;">当前用户:<%= ((User)session.getAttribute("user")).getLogin_name() %></p>

</div>
<div id="table3">
<form action="" method="get">
<div id="kuang"><input name="" type="text" style="width:234px;height:26px;" /></div><!--搜索框-->
<div id="an"><input name="搜索" type="button" value="搜索"/></div><!--搜索按钮-->
</form>

</div>


<br/>

<div id="left" >
<p></p>
<script type="text/javascript">
			try //Internet Explorer
			{
				xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			} catch (e) {
				try //Firefox, Mozilla, Opera, etc.
				{
					xmlDoc = document.implementation.createDocument("", "",
							null);
				} catch (e) {
					alert(e.message);
				}
			}
			try {
				xmlDoc.async = false;
				xmlDoc.load("/FCBD/resources/Unit2.xml");
				//document.write("xmlDoc is loaded, ready for use");
			} catch (e) {
				try //Google Chrome  
				{
					var xmlhttp = new window.XMLHttpRequest();
					xmlhttp.open("POST", "/FCBD/resources/Unit2.xml", false);
					xmlhttp.setRequestHeader("Content-Type", "text/xml");
					xmlhttp.send(null);
					xmlDoc = xmlhttp.responseXML;
					//document.write("xmlDoc is loaded, ready for chrome");
				} catch (e) {
					alert(e.message);
				}

			}
		
			tree = new dTree('tree');
			var rootNode = xmlDoc.documentElement;
			tree.add(1, -1, '空军');
			var childrenNodes = rootNode.childNodes;
			for (i = 0; i < childrenNodes.length; i++) {
				if (childrenNodes[i].nodeName == "#text") continue;
				//document.write(parseInt(childrenNodes[i].attributes.getNamedItem("ID").nodeValue));
				tree.add(parseInt(childrenNodes[i].attributes.getNamedItem("ID").nodeValue), 1, childrenNodes[i].attributes.getNamedItem("name").nodeValue, " ");
				var childrenNodess = childrenNodes[i].childNodes;
				for (j = 0; j < childrenNodess.length; j++) {
					if (childrenNodess[j].nodeName == "#text") continue;
					//document.write(parseInt(childrenNodess[j].attributes.getNamedItem("ID").nodeValue));
					tree.add(parseInt(childrenNodess[j].attributes.getNamedItem("ID").nodeValue), parseInt(childrenNodes[i].attributes.getNamedItem("ID").nodeValue), childrenNodess[j].attributes.getNamedItem("name").nodeValue, " ");
					var childrenNodesss = childrenNodess[j].childNodes;
					for (h = 0; h < childrenNodesss.length; h++) {
						if (childrenNodesss[h].nodeName == "#text") continue;
						//document.write(parseInt(childrenNodesss[h].attributes.getNamedItem("ID").nodeValue));
						tree.add(parseInt(childrenNodesss[h].attributes.getNamedItem("ID").nodeValue), parseInt(childrenNodess[j].attributes.getNamedItem("ID").nodeValue), childrenNodesss[h].attributes.getNamedItem("name").nodeValue, " ");
					
					}
				}
			}
			document.write(tree);
			
			
		</script>

</div>

<div id="content">
<table border="1" id="tablea" style="position:relative;background-color:white;">
<tr>
<td width=255px>名称</td>
<td width=118px>修改时间</td>
<td width=118px>类型</td>
<td width=117px>大小</td>
</tr>

</table>
<table border="1" id="tableaa" style="position:relative;background-color:white;display:none;">
<tr>
<td width=140px>记录ID</td>
<td width=140px>时间</td>
<td width=140px>帧数</td>
<td width=140px>异常标志</td>
<td width=140px>设备名称</td>
<td width=140px>数值</td>
<td width=140px>机型</td>
</tr>

</table>
<div id="contentmin">
<table id ="tableb" >
</table>

<p id="filefind" style="display:none">
</p>
</div>
</div>
<div id="footer"><img class="normal" src="/FCBD/resources/img/u179.png" style="position:absolute;top:20px;left:40px;height:50px;width:100px;"/>
<div id="footerter">
0个对象
</div>
</div>

<script type="text/javascript"> 
//function setTable(data) {
//	var str = "";
//	for (i = 0; i < data.length; i++) {
//		str = str + '<tr onmouseout="this.style.backgroundColor=""" onmouseover="this.style.backgroundColor="#FFE9B3"" ondblclick = "setTable(this);"><td width=260px>' + data[i].name + '</td><td width=120px>' + data[i].time + '</td><td width=120px>' + data[i].lei + '</td><td width=120px>' + data[i].size + '</td></tr>';
//	}
//	document.getElementById("tableb").innerHTML = str;
//}
//var data=[{name:'沈空11',time:'2015/11/01',lei:'军区级',size:'无'},{name:'北空12',time:'2015/11/01',lei:'军区级',size:'无'},{name:'兰空13',time:'2015/11/01',lei:'军区级',size:'无'},{name:'济空14',time:'2015/11/01',lei:'军区级',size:'无'},{name:'南空15',time:'2015/11/01',lei:'军区级',size:'无'},{name:'广空16',time:'2015/11/01',lei:'军区级',size:'无'},{name:'成空17',time:'2015/11/01',lei:'军区级',size:'无'}]; 
//setTable(data);
 
</script> 
<script type="text/javascript">
 function show()
{
if(document.getElementById("tableb").innerHTML== ""){
document.getElementById('footerter').innerHTML =  '0个对象';
return;
}
var tab = document.getElementById("tableb") ;
 //表格行数
var rows = tab.rows.length ;
 //表格列数
var cells = tab.rows.item(0).cells.length ;

document.getElementById('footerter').innerHTML =  rows +'个对象';
}

window.onload=show;
</script>
</body>
</html>
