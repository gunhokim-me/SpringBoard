package kr.or.ddit.board.repository;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board.model.AttachVo;
import kr.or.ddit.board.model.BoardPostVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PageVo;
import kr.or.ddit.board.model.PostComVo;

@Repository("boardDao")
public class BoardDao implements BoardDaoI{

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;
	/**
	 * 
	* Method : allBoard
	* 작성자 : HO
	* 변경이력 : 2021.01.23
	* @return 등록된 모든 게시판 리스트
	* Method 설명 : DB에 등록된 모든 게시판 리스트를 가지고 온다
	 */
	@Override
	public List<BoardVo> allBoard() {
		return template.selectList("board.allBoard");
	}

	/**
	 * 
	* Method : useBoard
	* 작성자 : HO
	* 변경이력 :2021.01.23
	* @return 활성화 되어있는 게시판 리스트
	* Method 설명 : DB에 등록된 모든 게시판 중 활성화 된 게시판 리스트만 가지고 온다.
	 */
	@Override
	public List<BoardVo> useBoard() {
		return template.selectList("board.useBoard");
	}

	/**
	 * 
	* Method : allCountBoard
	* 작성자 : HO
	* 변경이력 : 2021.01.24
	* @return  모든 게시판 수
	* Method 설명 : 생성되어 있는 모든 게시판의 수량을 가져온다
	 */
	@Override
	public int allCountBoard() {
		return template.selectOne("board.allCountBoard");
	}
	
	/**
	 * 
	* Method : createBoardInfo
	* 작성자 : HO
	* 변경이력 : 2021.01.24
	* @param BoardVo vo
	* @return 생성된 게시판 수량
	* Method 설명 : 게시판 이름과 상태를 받아서 새로운 게시판을 생성한다.
	 */
	@Override
	public int createBoardInfo(BoardVo vo) {
		return template.insert("board.createBoardInfo",vo);
	}

	/**
	 * 
	* Method : boardContent
	* 작성자 : HO
	* 변경이력 : 2021.01.24
	* @param 게시판 번호
	* @return 게시판 번호에 맞는 게시물
	* Method 설명 : 게시판 번호에 맞는 게시물 전체를 가지고온다
	 */
//	@Override
//	public List<BoardPostVo> boardContent(PageVo vo) {
//		Sqltemplate.template.= MyBatisUtil.gettemplate.);
//		List<BoardPostVo> list = template.selectList("board.paging",vo);
//		template.close();
//		return list;
//	}
	@Override
	public List<BoardPostVo> boardContent(PageVo vo) {
		return template.selectList("board.layerPaging",vo);
	}

	/**
	 * 
	* Method : modifyBoard
	* 작성자 : HO
	* 변경이력 : 2021.01.24
	* @param BoardVo
	* @return 변경된 행의 수
	* Method 설명 : 선택된 게시판의 이름 또는 상태를 변경하는 메서드
	 */
	@Override
	public int modifyBoard(BoardVo vo) {
		return template.update("board.updateBoard",vo);
	}

	/**
	 * 
	 * Method : createPost
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.24
	 * @param BoardPostVo
	 * @return 추가된 행의 개수
	 * Method 설명 : 회원이 입력을 하게되면 해당 게시판에 게시글이 입력된다. 
	 */
	@Override
	public int createPost(BoardPostVo vo) {
		return template.insert("board.newContent",vo);
	}

	/**
	 * 
	 * Method : selectBoardInfo
	 * 작성자 : PC-01
	 * 변경이력 :
	 * @param bor_num
	 * @return
	 * Method 설명 :
	 */
	@Override
	public List<BoardVo> selectBoardInfo(int bor_num) {
		return template.selectList("board.selectboardinfo", bor_num);
	}

	/**
	 * 
	 * Method : selectContCount
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.25
	 * @param bor_num
	 * @return 검색된 행의 개수
	 * Method 설명 : 선택된 게시판의 게시물 개수를 구한다.
	 */
	@Override
	public int selectContCount(int bor_num) {
		return template.selectOne("board.selectContCount", bor_num);
	}

	/**
	 * 
	 * Method : attachFile
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.25
	 * @param vo
	 * @return 저장이 되면 1을 반환
	 * Method 설명 : 게시글 생성시 첨부 파일이 있으면 저장 한다.
	 */
	@Override
	public int attachFile(AttachVo vo) {
		return template.insert("board.attachFile",vo);
	}

	/**
	 * 
	 * Method : maxNumber
	 * 작성자 : PC-01
	 * 변경이력 :
	 * @param bor_num
	 * @return
	 * Method 설명 :
	 */
	@Override
	public int maxNumber(int bor_num) {
		return template.selectOne("board.maxNumber", bor_num);
	}

	/**
	 * 
	 * Method : selectBoardDetail
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.25
	 * @param vo
	 * @return
	 * Method 설명 : 선택된 게시글에 대한 데이터를 가져옵니다.
	 */
	@Override
	public BoardPostVo selectBoardDetail(BoardPostVo vo) {
		return template.selectOne("board.selectBoardDetail",vo);
	}

	/**
	 * 
	 * Method : deleteContent
	 * 작성자 : PC-01
	 * 변경이력 :
	 * @param vo
	 * @return
	 * Method 설명 : 선택된 게시글을 삭제한다.
	 */
	@Override
	public int deleteContent(BoardPostVo vo) {
		return template.update("board.deleteContent",vo);
	}

	/**
	 * 
	 * Method : modifyContent
	 * 작성자 : PC-01
	 * 변경이력 :
	 * @param vo
	 * @return
	 * Method 설명 :선택한 게시글을 수정한다.
	 */
	@Override
	synchronized public int modifyContent(BoardPostVo vo) {
		return template.update("board.modifyContent",vo);
	}

	/**
	 * 
	 * Method : selectattach
	 * 작성자 : PC-01
	 * 변경이력 :
	 * @param post_no
	 * @return
	 * Method 설명 : 선택한 게시물의 첨부파일을 가져오는 메서드
	 */
	@Override
	public List<AttachVo> selectattach(int post_no) {
		return template.selectList("board.selectattach", post_no);
	}

	/**
	 * 
	 * Method : allPostCom
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.26
	 * @param post_no
	 * @return 등록된 댓글을 List로 반환
	 * Method 설명 : 현재 게시글에 등록된 댓글을 가져온다
	 */
	@Override
	public List<PostComVo> allPostCom(int post_no) {
		return template.selectList("board.allPostCom", post_no);
	}

	/**
	 * 
	 * Method : countPostCom
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.26
	 * @param post_no
	 * @return 등록된 댓글의 갯수를 반환
	 * Method 설명 : 현재 게시글에 등록된 댓글의 수
	 */
	@Override
	public int countPostCom() {
		return template.selectOne("board.countPostCom");
	}

	/**
	 * 
	 * Method : savePostCom
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.26
	 * @param vo
	 * @return 변경된 행의 갯수
	 * Method 설명 : 현재 게시판에 댓글을 등록한다.
	 */
	@Override
	public int savePostCom(PostComVo vo) {
		return template.update("board.savePostCom",vo);
	}

	/**
	 * 
	 * Method : createAnsPost
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.26
	 * @param vo
	 * @return
	 * Method 설명 : 답글등록
	 */
	@Override
	public int createAnsPost(BoardPostVo vo) {

		return template.insert("board.newContent",vo);
	}

	/**
	 * 
	 * Method : deleteAttach
	 * 작성자 : PC-01
	 * 변경이력 : 2021.01.27
	 * @param vo
	 * @return
	 * Method 설명 : 첨부파일을 삭제한다.
	 */
	@Override
	synchronized public int deleteAttach(AttachVo vo) {
		return	template.delete("board.deleteAttach", vo);
	}




}
