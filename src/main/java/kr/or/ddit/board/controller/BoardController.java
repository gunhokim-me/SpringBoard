package kr.or.ddit.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.ddit.board.model.AttachVo;
import kr.or.ddit.board.model.BoardPostVo;
import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.model.PageVo;
import kr.or.ddit.board.model.PostComVo;
import kr.or.ddit.board.service.BoardServiceI;
import kr.or.ddit.user.model.UserVo;

@RequestMapping("board")
@Controller
public class BoardController {

	@Resource(name="boardService")
	private BoardServiceI boardService;
	
	//메인 페이지
	@RequestMapping("main")
	public String mainView(HttpSession session) {
		List<BoardVo> list = boardService.allBoard();
		if(list.size() > 0) {
			session.setAttribute("boardCnt", boardService.allBoard());
		}
		return "main";
	}
	
	//게시판 생성
	@RequestMapping(path="createBoard", method = RequestMethod.GET)
	public String createBoard() {
		return "modifyBoard";
	}
	
	//게시판 생성 
	@RequestMapping(path="createBoardSubmit")
	public String createBoardPost(String bor_nm, int bor_act ,HttpSession session) {
		BoardVo vo = new BoardVo();
		vo.setBor_nm(bor_nm);
		vo.setBor_act(bor_act);
		int cnt = boardService.createBoardInfo(vo);
		if(cnt == 1) {
			session.setAttribute("boardCnt", boardService.allBoard());
		}
		return "redirect:/board/createBoard";
	}
	
	//게시판 수정 
	@RequestMapping(path="modifyBoard")
	public String modifyBoardPost(BoardVo vo ,HttpSession session) {
		int cnt = boardService.modifyBoard(vo);
		if(cnt == 1) {
			session.setAttribute("boardCnt", boardService.allBoard());
		}
		return "redirect:/board/createBoard";
	}
	
	//선택한 게시판 들어가기
	@RequestMapping(path="selectBoard")
	public String modifyBoardPost(int bor_num,
								  @RequestParam(defaultValue = "1") int page,
								  @RequestParam(defaultValue = "10") int pageSize,
								  Model model) {
		
		PageVo vo = new PageVo(page, pageSize, bor_num);
		Map<String, Object> map = new HashMap<String, Object>();
		map = boardService.boardContent(vo); 
		List<BoardVo> list = boardService.selectBoardInfo(bor_num);
		map.put("boardinfo", list);
		map.put("bornum", bor_num);
		
		model.addAllAttributes(map);
		
		return "boardmain";
	}
	
	//선택한 게시글 보기
	@RequestMapping("boarddetailContent")
	public String boardDetail(int postnum, int num, Model model) {
		BoardPostVo vo = new BoardPostVo();
		BoardPostVo vo2 = new BoardPostVo();
		
		//댓글 들고오기
		List<PostComVo> comlist = boardService.allPostCom(postnum);
		
		//첨부파일 들고오기
		List<AttachVo> attlist = boardService.selectattach(postnum);
		
		vo.setPost_no(postnum);
		vo.setBor_num(num);
		
		vo2 = boardService.selectBoardDetail(vo);
		int lft = vo2.getLft();
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("lft", lft);
		map.put("attlist", attlist);
		map.put("postDetail", vo2);
		map.put("postcom", comlist);
		
		model.addAllAttributes(map);
		
		return "boardDetail";
	}
	
	//게시글 수정페이지로 이동
	@RequestMapping(path="modifyBoardContent", method = RequestMethod.GET )
	public String modifyBoard(String userid, int bornum, int postno, String title, String cont, Model model) {
		BoardPostVo vo = new BoardPostVo();
		List<AttachVo> attlist = boardService.selectattach(postno);
		
		vo.setUser_id(userid);
		vo.setBor_num(postno);
		vo.setPost_no(postno);
		vo.setTitle(title);
		vo.setCont(cont);
		
		int size = attlist.size();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filesize", size);  
		map.put("attlist", attlist);
		map.put("summer", vo);      
		map.put("userid", userid);  
		map.put("bornum", bornum);  
		map.put("postno", postno);  
		
		model.addAllAttributes(map);
		
		return "modifypost";
	}
	
	//게시글 수정
	@RequestMapping(path="modifyBoardContent", method = RequestMethod.POST)
	public String modifyBoardContent(int bor3, int post3, String title3, String cont3, String user3, 
									 String[] filename ,  MultipartHttpServletRequest mtfRequest, Model model) {
		
		BoardPostVo vo = new BoardPostVo();
		AttachVo attvo = new AttachVo();
		vo.setUser_id(user3);
		vo.setBor_num(bor3);
		vo.setTitle(title3);
		vo.setCont(cont3);
		vo.setPost_no(post3);
		
		List<MultipartFile> fileList = mtfRequest.getFiles("files");
		
		for(MultipartFile mf : fileList) {
			if(mf.getSize() > 0) {
				String realfilename= "D:/upload/" + UUID.randomUUID().toString() + "." +mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".")+1);;
				String filenames= mf.getOriginalFilename();
				AttachVo avo = new AttachVo();
				avo.setPost_no(post3);
				avo.setBor_num(bor3);
				avo.setUser_id(user3);
				avo.setFile_nm(filenames);
				avo.setFile_path(realfilename);
				boardService.attachFile(avo);
			}
		}
		
		int cnt = boardService.modifyContent(vo);

		if (cnt == 1) {
			attvo.setUser_id(user3);
			attvo.setPost_no(post3);
			if (filename == null) {

			} else if (filename.length == 0) {

			} else {
				for (int i = 0; i < filename.length; i++) {
					attvo.setFile_path(filename[i]);
					boardService.deleteAttach(attvo);
				}
			}
			return "redirect:/board/boarddetailContent?postnum=" + post3 + "&num=" + bor3;
		} 
		return "modifypost";
	}
	
	//게시글 삭제
	@RequestMapping(path="deleteBoardContent")
	public String deleteBoardContent(String userid, int bornum, int postno) {
		BoardPostVo vo = new BoardPostVo();
		
		vo.setUser_id(userid);
		vo.setBor_num(bornum);
		vo.setPost_no(postno);
		
		int cnt = boardService.deleteContent(vo);
		
		if(cnt == 1) {
			return "redirect:/board/selectBoard?bor_num=" + bornum;
		}else {
			return "redirect:/board/boarddetailContent?postnum=" + postno + "&num="+ bornum;
		}
	}
	
	//댓글 쓰기
	@RequestMapping(path="saveComment", method = RequestMethod.POST)
	public String saveComment(HttpSession session ,int postno, int bornum, String commcont, String userid) {
		
		int com_no = boardService.countPostCom();
				
		PostComVo vo = new PostComVo();
		BoardPostVo vo2 = new BoardPostVo();
		UserVo uvo = (UserVo) session.getAttribute("S_USER");
		
		vo2.setBor_num(bornum);
		vo2.setPost_no(postno);
		
		BoardPostVo vo3 = boardService.selectBoardDetail(vo2);
		int lft = vo3.getLft();
		
		int no = com_no +1;
		int no2 = no == 1? 1 : no-1;
		
		vo.setCom_no(no);
		vo.setCom_user_id(userid);
		vo.setCom_con(commcont);
		vo.setLft(lft);
		vo.setPost_no(postno);
		vo.setBor_num(bornum);
		vo.setUser_id(uvo.getUser_id());
		boardService.savePostCom(vo);
		
		return "redirect:boarddetailContent?postnum=" + postno + "&num=" + bornum;
		
	}
	
	//댓글 수정
	
	//댓글 삭제
	
	//새글 쓰기 페이지
	@RequestMapping(path="boardcontent", method = RequestMethod.POST)
	public String newBoardcontent(int num, Model model) {
		
		List<BoardVo> list = boardService.selectBoardInfo(num);
		
		model.addAttribute("boardinfo", list);
		
		return "newboardpost";
	}
	
	//새글 쓰기 저장
	@RequestMapping(path="saveNewBoardContent", method = RequestMethod.POST)
	public String saveNewBoardContent(String editordata,String title, int bor_num, String userid,
									  MultipartHttpServletRequest mtfRequest, Model model) {
		int post_del = 1;
		int post_c_no = 0;
		String b_user_id = "";
		
		BoardPostVo vo = new BoardPostVo();
		
		vo.setTitle(title);
		vo.setCont(editordata);
		vo.setUser_id(userid);
		vo.setPost_del(post_del);
		vo.setBor_num(bor_num);
		vo.setPost_no2(post_c_no);
		vo.setUser_id2(b_user_id);
		vo.setBor_num2(bor_num);
		
		int cnt = boardService.createPost(vo);
		int num = boardService.maxNumber(bor_num);
		
		List<MultipartFile> fileList = mtfRequest.getFiles("files");
		for(MultipartFile mf : fileList) {
			if(mf.getSize() > 0) {
				String realfilename= "D:/upload/" + UUID.randomUUID().toString() + "." +mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".")+1);;
				String filename= mf.getOriginalFilename();
				AttachVo avo = new AttachVo();
				avo.setPost_no(num);
				avo.setBor_num(bor_num);
				avo.setUser_id(userid);
				avo.setFile_nm(filename);
				avo.setFile_path(realfilename);
				boardService.attachFile(avo);
			}
		}
		if(cnt == 1) {
			return "redirect:/board/selectBoard?bor_num=" + bor_num;
		}
		return "redirect:/board/boardcontent";
	}
	
	//답글 쓰기 페이지로 이동
	@RequestMapping(path="ansBoardPostContent" , method = RequestMethod.GET)
	public String ansBoardPostContentView(String userid, int bornum, int postno, Model model) {
		
		List<BoardVo> vo = boardService.selectBoardInfo(bornum);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("boardinfo", vo); 
		map.put("userid", userid);
		map.put("bornum", bornum);
		map.put("postno", postno);
		
		model.addAllAttributes(map);
		
		return "anspost";
		
	}
	
	//답글 쓰기
	@RequestMapping(path="ansBoardPostContent", method = RequestMethod.POST)
	public String ansBoardPostContent(String editordata2 ,String title2, int bornum2, String userid2,
									  int postno2, HttpSession session, MultipartHttpServletRequest mtfRequest, Model model) {
		
		UserVo uvo = (UserVo) session.getAttribute("S_USER");
		int post_del = 1;
		
		BoardPostVo vo = new BoardPostVo();
		BoardPostVo detailvo = new BoardPostVo();
		detailvo.setBor_num(bornum2);
		detailvo.setPost_no(postno2);
		
		BoardPostVo detail = boardService.selectBoardDetail(detailvo);
		
		int lft = (detail.getLft()+1);
		
		vo.setBor_num(bornum2);
		vo.setUser_id(uvo.getUser_id());
		vo.setTitle(title2);
		vo.setCont(editordata2);
		vo.setPost_del(post_del);
		vo.setLft(lft);
		vo.setPost_no2(postno2);
		vo.setUser_id2(userid2);
		vo.setBor_num2(bornum2);
		int cnt = boardService.createPost(vo);
		int num = boardService.maxNumber(bornum2);
		
		List<MultipartFile> fileList = mtfRequest.getFiles("files");
		for(MultipartFile mf : fileList) {
			if(mf.getSize() > 0) {
				String realfilename= "D:/upload/" + UUID.randomUUID().toString() + "." +mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".")+1);;
				String filename= mf.getOriginalFilename();
				AttachVo avo = new AttachVo();
				avo.setPost_no(num);
				avo.setBor_num(bornum2);
				avo.setUser_id(uvo.getUser_id());
				avo.setFile_nm(filename);
				avo.setFile_path(realfilename);
				boardService.attachFile(avo);
			}
		}
		if(cnt == 1) {
			return "redirect:/board/selectBoard?bor_num=" + bornum2;
		}
		return "redirect:/board/boardcontent";
	}
	
}
