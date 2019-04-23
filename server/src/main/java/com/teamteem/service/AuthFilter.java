package com.teamteem.service;

import com.teamteem.dao.Login;
import com.teamteem.model.Person;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*templated from: https://codenotfound.com/jsf-login-servlet-filter-example.html the beauty of coding*/

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Person person = (Person) req.getSession().getAttribute("user");
        System.out.println(person);

        if (person != null) { // if user is not null and is logged in
            chain.doFilter(request, response); // then filter
        } else {
            res.sendRedirect(req.getContextPath() + "/login.xhtml"); // else if not logged in, redirect to login page
        }
    }

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
