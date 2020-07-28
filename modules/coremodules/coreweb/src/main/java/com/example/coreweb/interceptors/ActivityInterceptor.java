package com.example.coreweb.interceptors;


import com.example.auth.config.security.SecurityContext;
import com.example.coreweb.domains.activities.models.entities.Activity;
import com.example.coreweb.domains.activities.services.ActivityService;
import com.example.auth.entities.UserAuth;

import com.example.auth.utils.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ActivityInterceptor extends HandlerInterceptorAdapter {

    private final ActivityService activityService;

    @Autowired
    public ActivityInterceptor(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) userAgent = request.getHeader("user-agent");
        String expires = response.getHeader("Expires");
        Activity activity = new Activity();
        activity.setIp(NetworkUtil.getClientIP());
        activity.setExpires(expires);
        activity.setRequestMethod(request.getMethod());
        activity.setUrl(request.getRequestURI());

        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(userAgent);
        if (m.find()) {
            activity.setUserAgent(m.group(1));
        }

        if (SecurityContext.isAuthenticated()) {
            UserAuth user = SecurityContext.getCurrentUser();
            activity.setUser(user);
            if (
                    activity.getUrl().contains("image")
                            || activity.getUrl().equals("/")
                            || activity.getUrl().startsWith("/css")
                            || activity.getUrl().startsWith("/fonts")
                            || activity.getUrl().startsWith("/fileuploads")
                            || activity.getUrl().startsWith("/js")
            )
                return super.preHandle(request, response, handler);
            this.activityService.save(activity);
        }
        return super.preHandle(request, response, handler);
    }

}
