package com.andy.recruitment.web.interceptor;

import com.andy.recruitment.web.RecruitmentSystemInfo;
import com.xgimi.auth.PermissionService;
import com.xgimi.context.ServletContext;
import com.xgimi.interceptor.MyPermissionInterceptor;
import java.lang.reflect.Method;
import javax.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 销售平台-认证系统-拦截器
 *
 * <p>权限拦截器 通过aop方式实现
 *
 * @author 庞先海 2018-07-27
 */
@Aspect
@Component
public class PermissionInterceptor extends MyPermissionInterceptor {

    private final PermissionService permissionService;

    @Autowired
    public PermissionInterceptor(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostConstruct
    public void init() {
        super.permissionService = permissionService;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method = signature.getMethod();
        ServletContext.getRequest().setAttribute("version", RecruitmentSystemInfo.VERSION);
        super.auth(method);
        return pjp.proceed();
    }
}
