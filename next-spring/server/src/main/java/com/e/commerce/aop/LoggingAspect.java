package com.e.commerce.aop;

import com.e.commerce.domain.model.UserActions;
import com.e.commerce.domain.repository.UserActionsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final UserActionsRepository userActionsRepository;
    //private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return;
        }

        String username = request.getRemoteUser();
        String ip = request.getRemoteAddr();
        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.toString(joinPoint.getArgs());

        UserActions userActions = new UserActions();
        userActions.setUsername(username);
        userActions.setIp(ip);
        userActions.setMethodName(methodName);
        userActions.setArguments(arguments);
        userActions.setResult(result != null ? result.toString().substring(1, 10) : "void");
        userActions.setTimestamp(LocalDateTime.now());

        userActionsRepository.save(userActions);
    }
}
