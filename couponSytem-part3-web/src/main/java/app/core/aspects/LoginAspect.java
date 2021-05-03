package app.core.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import app.core.loginManager.LoginManager;

@Component
//@Aspect
@Order(1)
public class LoginAspect {
	
	@Autowired
	private LoginManager loginManager;
	
	@Around("all() && !loginServices() && !job()")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("*** from log aspect");
		if (loginManager.isLogged()) {
			return jp.proceed();
		} else {
			throw new RuntimeException("Blocked by Log Aspect - you are not logged in");
		}
	}
	
	
	@Pointcut("execution(* app.core.services.JobService.*(..))")
	public void job() {
		
	}
	
	@Pointcut("execution(* app.core.services.ClientService.login(..))")
	public void loginServices() {
		
	};
	
	
	
	@Pointcut("execution(* app.core.services.*.*(..))")
	public void all()
	{
	};
}
