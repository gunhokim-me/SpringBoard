package kr.or.ddit.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserServiceI;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource(name="userService")
	private UserServiceI userService;
	
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(path="loginUser", method = {RequestMethod.POST})
	public String loginViewPost(UserVo vo, HttpSession session) {
		int cnt = userService.loginUserCount(vo);
		if(cnt == 1) {
			session.setAttribute("S_USER", vo);
			return "redirect:/board/main";
		}else {
			return "login";
		}
	}

}
