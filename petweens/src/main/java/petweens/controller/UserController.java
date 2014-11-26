package petweens.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import petweens.model.User;
import petweens.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name="userService")
	private UserService userService;
	
	//AOP JoinPoint
	@RequestMapping("/signup")
	public ModelAndView signUp(HttpServletRequest request){
		return new ModelAndView("signup");
	}
	
	//AOP JoinPoint
	@RequestMapping("/signin")
	public ModelAndView signIn(HttpServletRequest request){
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute User user,HttpSession session){
		ModelAndView mv = new ModelAndView();
		if(userService.isResistedUser(user)){
			session.setAttribute("userId", user.getUserid());
			session.setAttribute("userName", user.getUsername());
			mv.setViewName("redirect:/home");
		}
		else{
			mv.addObject("error", true);
			mv.addObject("user", user);
			mv.setViewName("login");
		}
		return mv;
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView logout(HttpSession session){
		ModelAndView mv = new ModelAndView();
		session.invalidate();
		mv.setViewName("redirect:/home");
		return mv;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView register(@ModelAttribute User user){
		ModelAndView mv = new ModelAndView();
		if(userService.insertUser(user)){
			mv.setViewName("login");
		}
		else{
			mv.addObject(user);
			mv.setViewName("signup");
		}
		mv.addObject(user);
		return mv;
	}
	
	
	@RequestMapping(value="/checkName",method=RequestMethod.POST)
	public @ResponseBody String checkName(@RequestParam String username){
		return !userService.isUserNameExist(username) ? "true":"false";
	}
	@RequestMapping(value="/checkEmail",method=RequestMethod.POST)
	public @ResponseBody String checkEmail(@RequestParam String email){
		return !userService.isUserEmailExist(email) ? "true":"false";
	}
	

}
