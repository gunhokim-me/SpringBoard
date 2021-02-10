package kr.or.ddit.user.repository;

import kr.or.ddit.user.model.UserVo;

public interface UserDaoI {
	
	//로그인 시 입력한 회원이 있는지 확인
	int loginUserCount(UserVo vo);
}
