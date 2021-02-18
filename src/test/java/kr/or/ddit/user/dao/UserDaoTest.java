package kr.or.ddit.user.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.repository.UserDaoI;

public class UserDaoTest extends ModelAndView{

	@Autowired
	private UserDaoI userDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Test
	public void loginUserCountTest() {
		/***Given***/
		UserVo vo = new UserVo();

		vo.setUser_id("test");
		vo.setUser_pass("test");
		logger.debug("vo : {}",vo);
		/***When***/
		int cnt = userDao.loginUserCount(vo);
		logger.debug("cnt : {}",cnt);
		/***Then***/
		assertEquals(1, cnt);
		
	}

}
