package petweens.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.servlet.ModelAndView;

@Aspect
public class LoginAspect {
	@Pointcut("execution(* petweens.controller.RoomController.*Room(..))")
	public void isUserLogin(){}
	
	@Pointcut("execution(* petweens.controller.UserController.sign*(..))")
	public void sign(){}
	
	@Around("isUserLogin()")
	public Object UserLoginCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		ModelAndView mv = new ModelAndView("login");
		HttpSession session = getSession(joinPoint);
		if(session.getAttribute("userId")!=null){
			return (ModelAndView)joinPoint.proceed();
		}
		return mv;
	}
	
	@Around("sign()")
	public Object aleadyLoginCheck(ProceedingJoinPoint joinPoint) throws Throwable{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home");
		HttpSession session = getSession(joinPoint);
		if(session.getAttribute("userId")==null){
			return (ModelAndView)joinPoint.proceed();
		}
		return mv;
	}

	private HttpSession getSession(ProceedingJoinPoint join){
		HttpServletRequest request = null;
		Object[] obj = join.getArgs();
		
		for(Object o : obj){
			if(o instanceof HttpServletRequest){
				request = (HttpServletRequest)o;
			}
		}
		HttpSession session = request.getSession();
		return session;
	}
}
