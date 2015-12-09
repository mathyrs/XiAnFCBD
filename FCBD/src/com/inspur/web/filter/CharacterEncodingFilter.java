package com.inspur.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class CharacterEncodingFilter implements Filter {

	private FilterConfig filterConfig = null;
	//设置默认字符编码
	private String defaultCharset = "UTF-8";
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String charset = null;
		try {
			charset = filterConfig.getInitParameter("charset");
		} catch (NullPointerException e) {
			charset = defaultCharset;
		}
		
//		if(charset==null){
//		    charset = defaultCharset;
//		}
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		response.setContentType("text/html;charset="+charset);         
		MyCharacterEncodingRequest requestWrapper = new MyCharacterEncodingRequest(request);
		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = filterConfig;
	}

}

/*
1.实现与被增强对象相同的接口 
2、定义一个变量记住被增强对象
3、定义一个构造器，接收被增强对象
4、覆盖需要增强的方法
5、对于不想增强的方法，直接调用被增强对象（目标对象）的方法
 */
class MyCharacterEncodingRequest extends HttpServletRequestWrapper{
    
    private HttpServletRequest request;
    public MyCharacterEncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }
    /* 重写getParameter方法
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String name) {
        
        try{
            //获取参数的值
            String value= this.request.getParameter(name);
            if(value==null){
                return null;
            }
            //如果不是以get方式提交数据的，就直接返回获取到的值
            if(!this.request.getMethod().equalsIgnoreCase("get")) {
                return value;
            }else{
                //如果是以get方式提交数据的，就对获取到的值进行转码处理
                value = new String(value.getBytes("ISO8859-1"),this.request.getCharacterEncoding());
                return value;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
