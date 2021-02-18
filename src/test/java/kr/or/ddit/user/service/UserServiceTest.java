package kr.or.ddit.user.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.test.config.ModelTestConfig;
import kr.or.ddit.user.model.UserVo;

public class UserServiceTest extends ModelTestConfig {

	@Resource(name="userService")
	private UserServiceI userService;
	
	@Test
	public void loginUserCountTest() {
		/***Given***/
		UserVo vo = new UserVo();
		vo.setUser_id("test");
		vo.setUser_pass("test");

		/***When***/
		int cnt = userService.loginUserCount(vo);
		/***Then***/
		assertEquals(1, cnt);
	}
	
	@Test
	public void loginUserCountFailTest() {
		/***Given***/
		UserVo vo = new UserVo();
		vo.setUser_id("testttt");
		vo.setUser_pass("test");
		
		/***When***/
		int cnt = userService.loginUserCount(vo);
		/***Then***/
		assertEquals(0, cnt);
	}

}
