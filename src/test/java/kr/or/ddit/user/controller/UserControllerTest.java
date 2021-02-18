package kr.or.ddit.user.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import kr.or.ddit.test.config.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

public class UserControllerTest extends WebTestConfig {

	@Test
	public void loginTest() throws Exception {
		mock.perform(get("/user/login"))
		  .andExpect(view().name("login"))
		  .andExpect(status().isOk());
	}
	
	@Test
	public void loginViewTest() throws Exception {
		mock.perform(post("/user/loginUser").param("user_id", "test").param("user_pass", "test"))
		.andExpect(view().name("redirect:/board/main"))
		.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void loginViewFailTest() throws Exception {
		mock.perform(post("/user/loginUser").param("user_id", "testt").param("user_pass", "test"))
		.andExpect(view().name("login"))
		.andExpect(status().isOk());
	}

}
