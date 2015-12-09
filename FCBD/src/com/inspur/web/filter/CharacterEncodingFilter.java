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
	//����Ĭ���ַ�����
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
1.ʵ���뱻��ǿ������ͬ�Ľӿ� 
2������һ��������ס����ǿ����
3������һ�������������ձ���ǿ����
4��������Ҫ��ǿ�ķ���
5�����ڲ�����ǿ�ķ�����ֱ�ӵ��ñ���ǿ����Ŀ����󣩵ķ���
 */
class MyCharacterEncodingRequest extends HttpServletRequestWrapper{
    
    private HttpServletRequest request;
    public MyCharacterEncodingRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }
    /* ��дgetParameter����
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String name) {
        
        try{
            //��ȡ������ֵ
            String value= this.request.getParameter(name);
            if(value==null){
                return null;
            }
            //���������get��ʽ�ύ���ݵģ���ֱ�ӷ��ػ�ȡ����ֵ
            if(!this.request.getMethod().equalsIgnoreCase("get")) {
                return value;
            }else{
                //�������get��ʽ�ύ���ݵģ��ͶԻ�ȡ����ֵ����ת�봦��
                value = new String(value.getBytes("ISO8859-1"),this.request.getCharacterEncoding());
                return value;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
