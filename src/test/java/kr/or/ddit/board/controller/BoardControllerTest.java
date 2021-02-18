package kr.or.ddit.board.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.BoardServiceI;
import kr.or.ddit.test.config.WebTestConfig;

public class BoardControllerTest extends WebTestConfig{

	@Resource(name="boardService")
	private BoardServiceI boardService;
	
	
	@Test
	public void mainViewTest() throws Exception{
		mock.perform(get("/board/main"))
		.andExpect(status().isOk())
		.andExpect(view().name("main"));
	}
	
	@Test
	public void createBoardViewTest() throws Exception{
		mock.perform(get("/board/createBoard"))
		.andExpect(status().isOk())
		.andExpect(view().name("modifyBoard"));
	}
	
	@Test
	public void createBoardSubmitTest() throws Exception{
		mock.perform(get("/board/createBoardSubmit").param("bor_nm", "test").param("bor_act", "1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/board/createBoard"));
	}

}
