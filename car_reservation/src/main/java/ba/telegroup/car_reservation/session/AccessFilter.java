package ba.telegroup.car_reservation.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebFilter("/api/*")
public class AccessFilter implements Filter {
    @Value("#{'${public.path}'.split(',')}")
    private List<String> publicPaths;

    @Value("#{'${companyAdmin.path}'.split(',')}")
    private List<String> companyAdminPaths;

    @Value("#{'${user.path}'.split(',')}")
    private List<String> userPaths;

    @Value("#{'${systemAdmin.path}'.split(',')}")
    private List<String> systemAdminPaths;

    @Value("${role.user}")
    private Integer roleUser;

    @Value("${role.company_admin}")
    private Integer roleCompanyAdmin;

    @Value("${role.system_admin}")
    private Integer roleSystemAdmin;

    private WebApplicationContext springContext;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        springContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserBean userBean = (UserBean) springContext.getBean("userBean");

        if (userBean != null && userBean.getLoggedIn()) {
            if(userBean.getUser().getRoleId().equals(roleCompanyAdmin)){
                filter(servletRequest, servletResponse, filterChain, request, response, companyAdminPaths);
            }
            else if(userBean.getUser().getRoleId().equals(roleUser)){
                filter(servletRequest, servletResponse, filterChain, request, response, userPaths);
            }
            else {
                filter(servletRequest, servletResponse, filterChain, request, response, systemAdminPaths);
            }
        }
        else {
            filter(servletRequest, servletResponse, filterChain, request, response, publicPaths);
        }
    }

    private void filter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain, HttpServletRequest request, HttpServletResponse response, List<String> paths) throws IOException, ServletException {
        boolean isPath = false;
        for (String path : paths) {
            String uri=path.split(":")[0];
            String method=path.split(":")[1];
            if (request.getRequestURI().startsWith(uri) && request.getMethod().toUpperCase().equals(method)) {
                isPath = true;
                break;
            }
        }
        if (isPath)
            filterChain.doFilter(servletRequest, servletResponse);
        else
            response.sendError(401);
    }

    @Override
    public void destroy() {

    }
}