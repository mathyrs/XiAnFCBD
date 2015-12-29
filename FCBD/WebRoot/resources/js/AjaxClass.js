
function AjaxClass()
{
    var XmlHttp = false;
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

    var me = this;
    this.Method = "POST";
    this.Url = "";
    this.Async = true;
    this.Arg = "";
    this.CallBack = function(){};
    this.Loading = function(){};
    
    this.Send = function()
    {
        if (this.Url=="")
        {
            return false;
        }
        if (!XmlHttp)
        {
            return IframePost();
        }

        XmlHttp.open(this.Method, this.Url, this.Async);
        if (this.Method=="POST")
        {
            XmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        }
        XmlHttp.onreadystatechange = function()
        {
            if (XmlHttp.readyState==4)
            {
                var Result = false;
                if (XmlHttp.status==200)
                {
                    Result = XmlHttp.responseText;
                }
                XmlHttp = null;
                
                me.CallBack(Result);
            }
             else
             {
				me.Loading();
             }
        }
        if (this.Method=="POST")
        {
            XmlHttp.send(this.Arg);
        }
        else
        {
            XmlHttp.send(null);
        }
    }
    
    //Iframe方式提交
    function IframePost()
    {
        var Num = 0;
        var obj = document.createElement("iframe");
        obj.attachEvent("onload",function(){ me.CallBack(obj.contentWindow.document.body.innerHTML); obj.removeNode() });
        obj.attachEvent("onreadystatechange",function(){ if (Num>=5) {alert(false);obj.removeNode()} });
        obj.src = me.Url;
        obj.style.display = 'none';
        document.body.appendChild(obj);
    }
}

/*----------------------------调用方法------------------------------
    var Ajax = new AjaxClass();			// 创建AJAX对象
    Ajax.Method = "POST"; 				// 设置请求方式为POST
    Ajax.Url = "default.asp"			// URL为default.asp
    Ajax.Async = true;					// 是否异步
    Ajax.Arg = "a=1&b=2";				// POST的参数
    Ajax.Loading = function(){			//等待函数
		document.write("loading...");
    }
    Ajax.CallBack = function(str)       // 回调函数
    {
        document.write(str);
    }
    Ajax.Send();            			// 发送请求
   -----------------------------------------------------------
    var Ajax = new AjaxClass();			// 创建AJAX对象
    Ajax.Method = "GET"; 				// 设置请求方式为POST
    Ajax.Url = "default.asp?a=1&b=2"	// URL为default.asp
    Ajax.Async = true;					// 是否异步
    Ajax.Loading = function(){			//等待函数
		document.write("loading...");
    }
    Ajax.CallBack = function(str)       // 回调函数
    {
        document.write(str);
    }
    Ajax.Send();           				// 发送请求
--------------------------------------------------------------------*/