package kr.or.ddit.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.repository.UserDaoI;

@Service("userService")
public class UserService implements UserServiceI {

	@Resource(name="userDao")
	private UserDaoI userDao;
	
	/**
	 * 
	* Method : loginUserCount
	* 작성자 : HO
	* 변경이력 : 2021.01.23
	* @param UserVo vo
	* @return 아이디와 비밀번호가 일치하면 1, 일치하지 않으면 0 
	* Method 설명 : login.jsp에서 아이디와 비밀번호를 입력한 값이 있는지 확인(로그인)
	 */
	@Override
	public int loginUserCount(UserVo vo) {
		return userDao.loginUserCount(vo);
	}

}
