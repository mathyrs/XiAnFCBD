<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%
	String basePath = request.getContextPath();
	String cssPath = basePath + "/resources/dtree/dtree.css";
	String jsPath = basePath + "/resources/dtree/dtree.js";
%>
<html>

<head>
<title>Destroydrop &raquo; Javascripts &raquo; Tree</title>

<link rel="StyleSheet" href="<%=cssPath%>" type="text/css" />
<script type="text/javascript" src="<%=jsPath%>"></script>

</head>

<body>

	<h1>
		<a href="/">Destroydrop</a> &raquo; <a href="/javascripts/">Javascripts</a>
		&raquo; <a href="/javascripts/tree/">Tree</a>
	</h1>

	<h2>Example</h2>

	<div class="dtree">

		<p>
			<a href="javascript: d.openAll();">open all</a> | <a
				href="javascript: d.closeAll();">close all</a>
		</p>

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
				xmlDoc.load("Unit2.xml");
				document.write("xmlDoc is loaded, ready for use");
			} catch (e) {
				try //Google Chrome  
				{
					var xmlhttp = new window.XMLHttpRequest();
					xmlhttp.open("POST", "Unit2.xml", false);
					xmlhttp.setRequestHeader("Content-Type", "text/xml");
					xmlhttp.send(null);
					xmlDoc = xmlhttp.responseXML;
					document.write("xmlDoc is loaded, ready for chrome");
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

		<script type="text/javascript">
			d = new dTree('d');

			d.add(0, -1, 'My example tree');
			d.add(1, 0, 'Node 1', 'example01.html');
			d.add(2, 0, 'Node 2', 'example01.html');
			d.add(3, 1, 'Node 1.1', 'example01.html');
			d.add(4, 0, 'Node 3', 'example01.html');
			d.add(5, 3, 'Node 1.1.1', 'example01.html');
			d.add(6, 5, 'Node 1.1.1.1', 'example01.html');
			d.add(7, 0, 'Node 4', 'example01.html');
			d.add(8, 1, 'Node 1.2', 'example01.html');
			d.add(9, 0, 'My Pictures', 'example01.html',
					'Pictures I\'ve taken over the years', '', '',
					'resources/dtree/img/imgfolder.gif');
			d.add(10, 9, 'The trip to Iceland', 'example01.html',
					'Pictures of Gullfoss and Geysir');
			d.add(11, 9, 'Mom\'s birthday', 'example01.html');
			d.add(12, 0, 'Recycle Bin', 'example01.html', '', '',
					'resources/dtree/img/trash.gif');

			document.write(d);
		</script>

	</div>

	<p>
		<a href="mailto&#58;drop&#64;destroydrop&#46;com">&copy;2002-2003
			Geir Landr&ouml;</a>
	</p>
	<p id="panel">
		file show
	</p>
	<script>
	function setPanel(text) {
				document.getElementById("panel").innerHTML = text;
			};

</script>

</body>


</html>