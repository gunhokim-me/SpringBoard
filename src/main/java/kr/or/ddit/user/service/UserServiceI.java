package kr.or.ddit.user.service;

import kr.or.ddit.user.model.UserVo;

public interface UserServiceI {
	
	//로그인 시 입력한 회원이 있는지 확인
	int loginUserCount(UserVo vo);
}
