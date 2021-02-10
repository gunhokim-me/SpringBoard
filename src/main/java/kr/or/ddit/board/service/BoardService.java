package kr.or.ddit.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.board.model.AttachVo;
import kr.or.ddit.board.model.BoardPostVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PageVo;
import kr.or.ddit.board.model.PostComVo;
import kr.or.ddit.board.repository.BoardDaoI;

@Service("boardService")
public class BoardService implements BoardServiceI {

	@Resource(name="boardDao")
	private BoardDaoI boardDao;
	
	//모든 게시판 조회
	@Override
	public List<BoardVo> allBoard() {
		return boardDao.allBoard();
	}

	//활성화 된 게시판 조회
	@Override
	public List<BoardVo> useBoard() {
		return boardDao.useBoard();
	}

	//새로운 게시판 생성
	@Override
	public int createBoardInfo(BoardVo vo) {
		//게시판 번호를 넣어준다
		int num = boardDao.allCountBoard();
		vo.setBor_num(num+1);
		return boardDao.createBoardInfo(vo);
	}

	//선택된 게시판 내용 조회
	@Override
	public Map<String, Object> boardContent(PageVo vo) {
		Map<String, Object> map = new HashMap<>();
		List<BoardPostVo> postlist = boardDao.boardContent(vo);
		int listcnt = boardDao.selectContCount(vo.getBor_num());
		
		int pagination = (int)Math.ceil((double)boardDao.selectContCount(vo.getBor_num()) / vo.getPageSize());
		map.put("pagination", pagination);
		map.put("pageSize", vo.getPageSize());
		map.put("pageVo", vo);
		map.put("selectBoardList", postlist);
		return map;
	}

	//선택한 게시판 이름 또는 상태 변경
	@Override
	public int modifyBoard(BoardVo vo) {
		return boardDao.modifyBoard(vo);
	}

	@Override
	public List<BoardVo> selectBoardInfo(int bor_num) {
		return boardDao.selectBoardInfo(bor_num);
	}

	//게시글 생성
	@Override
	public int createPost(BoardPostVo vo) {
		int bor_num = boardDao.selectContCount(vo.getBor_num());
		vo.setPost_no(bor_num+1);
		return boardDao.createPost(vo);
	}

	//첨부파일 저장
	@Override
	public int attachFile(AttachVo vo) {
		return boardDao.attachFile(vo);
	}

	//최신번호 가져오기
	@Override
	public int maxNumber(int bor_num) {
		return boardDao.maxNumber(bor_num);
	}

	//선택된 게시글 상세보기
	@Override
	public BoardPostVo selectBoardDetail(BoardPostVo vo) {
		return boardDao.selectBoardDetail(vo);
	}

	//게시글 삭제
	@Override
	public int deleteContent(BoardPostVo vo) {
		return boardDao.deleteContent(vo);
	}

	//게시글 수정
	@Override
	public int modifyContent(BoardPostVo vo) {
		return boardDao.modifyContent(vo);
	}

	//게시물에 등록된 첨부파일 들고오기
	@Override
	public List<AttachVo> selectattach(int post_no) {
		return boardDao.selectattach(post_no);
	}

	//현재 게시글에 등록된 댓글 가져오기
	@Override
	public List<PostComVo> allPostCom(int post_no) {
		return boardDao.allPostCom(post_no);
	}

	//댓글 생성
	@Override
	public int savePostCom(PostComVo vo) {
		return boardDao.savePostCom(vo);
	}

	//댓글 최신번호
	@Override
	public int countPostCom() {
		return boardDao.countPostCom();
	}

	//답글 생성
	@Override
	public int createAnsPost(BoardPostVo vo) {
		int bor_num = boardDao.selectContCount(vo.getBor_num());
		vo.setPost_no(bor_num+1);
		return boardDao.createPost(vo);
	}

	//첨부파일 삭제
	@Override
	public int deleteAttach(AttachVo vo) {
		return boardDao.deleteAttach(vo);
	}


}
