package kh.spring.s02.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kh.spring.s02.board.model.service.BoardService;
import kh.spring.s02.board.model.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	public final static int BOARD_LIMIT = 5;
	public final static int PAGE_LIMIT = 3;
	
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView viewListBoard(ModelAndView mv) {
		//TODO
		//검색단어는 제목, 내용, 작성자에서 포함되어있으면 찾기
		//null 또는 ""은 검색하지 않음
		
//		String searchWord = "null";
//		String searchWord = "";
		String searchWord = "답";
		
		
		
		
		int currentPage = 1;
		int totalCnt = service.selectOneCount(searchWord);
		int totalpage =(totalCnt% BOARD_LIMIT==0)? 
						(totalCnt / BOARD_LIMIT): 
						(totalCnt / BOARD_LIMIT)+1;
		int startpage =(currentPage %PAGE_LIMIT == 0 )?
						(currentPage /PAGE_LIMIT -1)*PAGE_LIMIT +1 :
						(currentPage /PAGE_LIMIT   )*PAGE_LIMIT +1 ;
		int endpage = (startpage +PAGE_LIMIT > totalpage)?
						totalpage :
						(startpage +PAGE_LIMIT);
		
		
		
		Map<String, Integer> map = new HashMap<String,Integer>();
		map.put("totalpage", totalpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("currentPage", currentPage);
		
//		mv.addObject("totalpage",totalpage );
//		mv.addObject("startpage", startpage);
//		mv.addObject("endpage", endpage);
//		mv.addObject("currentPage",currentPage );
		
		
		
		mv.addObject("boardlist", service.selectList(currentPage, BOARD_LIMIT, searchWord));
		mv.setViewName("board/list");
		return mv;
	}
	
	
	@GetMapping(value="/update")
	public void viewUpdateBoard() {
	}
	
	
	@GetMapping(value="/updatePostTest")
	public void updateBoard() {
		//TODO
		int boardNum = 1;
		String boardTitle = "수정제목";
		String boardContent = "수정내용";
		String boardOriginalFilename ="";  //"" 파일없음
		String boardRenameFilename ="";  //""파일없음

		BoardVo vo = new BoardVo();
		vo.setBoardTitle(boardTitle);
		vo.setBoardContent(boardContent);
		vo.setBoardOriginalFilename(boardOriginalFilename);
		vo.setBoardRenameFilename(boardRenameFilename);
		service.update(vo);
	}
	
	
	@GetMapping(value="/delete")
	public void viewDeleteBoard() {
		int boardNum = 8;
		int result = service.delete(boardNum);
	
	}	
	
	
	
	@GetMapping(value="/read")
	public void viewReadBoard() {
		//TODO
		int boardNum = 1;
		String writer = "user11";
		BoardVo vo = service.selectOne(boardNum, writer);
	}
	

	//원글 작성
	@GetMapping(value="/insert")
	public ModelAndView viewInsertBoard(
			ModelAndView mv
		   , HttpServletRequest req
		   , HttpSession session
		   , BoardVo vo
			) {
		
//		mv.addObject("test", "test value");
		mv.setViewName("board/insert");
		return mv;
	}
	
	
	
	//원글 작성
//	@PostMapping("/insert")
	//TODO
	@GetMapping("/insertPostTest")
	public ModelAndView doInsertBoard(ModelAndView mv
			, BoardVo vo
			) {
		vo.setBoardContent("임시내용");
		vo.setBoardTitle("임시제목");
		vo.setBoardWriter("user11");
		int result= service.insert(vo);
		return mv;
	}
	
	
	
	//답글 작성
	@GetMapping("/isertReply")
	public ModelAndView viewInsertReply(ModelAndView mv
		, int board //몇번 글에 답글인지
		) {
		mv.setViewName("insertReply");
		return mv;
	}
		
		
	//답글 작성
	@GetMapping("/isertReplyPostTest")
	public ModelAndView viewInsertReply(ModelAndView mv
			, BoardVo vo
			) {
		int boardNum = 4;
		vo.setBoardNum(boardNum);
		
		vo.setBoardContent("임시답내용");
		vo.setBoardTitle("임시답제목");
		vo.setBoardWriter("user22");
		
		service.insert(vo);
		
		return mv;
		}		
	
	
	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mv) {
		return mv;
	}
	
}