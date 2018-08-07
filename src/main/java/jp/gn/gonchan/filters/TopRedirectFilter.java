package jp.gn.gonchan.filters;

import org.apache.commons.codec.binary.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TopRedirectFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String requestUrl = ((HttpServletRequest) request).getPathInfo();
            if (StringUtils.equals(requestUrl, "/")) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpStatus.SEE_OTHER_303);
                httpResponse.sendRedirect("/member/top");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}
