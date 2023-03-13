package kh.spring.s02.board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kh.spring.s02.board.model.service.BoardService;
import kh.spring.s02.board.model.vo.BoardVo;
import kh.spring.s02.common.file.FileUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	public final static int BOARD_LIMIT = 5;
	public final static int PAGE_LIMIT = 3;

	
	@RequestMapping(value="/list")
	public ModelAndView viewListBoard(ModelAndView mv,HttpServletRequest req) {
		//TODO
		//검색단어는 제목, 내용, 작성자에서 포함되어있으면 찾기
		//null 또는 ""은 검색하지 않음
		
//		String searchWord = "null";
//		String searchWord = "";
		String searchWord = "답";
		
		try {
			req.setCharacterEncoding("UTF-8");
			searchWord = req.getParameter("searchWord");
			System.out.println("한글 확인: "+ searchWord);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
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
		int result = service.update(vo);
	}
	
	
	@GetMapping(value="/delete")
	public void viewDeleteBoard() {
		int boardNum = 8;
		int result = service.delete(boardNum);
	
	}	
	
	
	
	@GetMapping(value="/read")
	public ModelAndView viewReadBoard(
			ModelAndView mv,
			@RequestParam("boardNum") int boardNum) {
		
		String writer = "user2";
		
		BoardVo vo = service.selectOne(boardNum, writer);
		mv.addObject("board", vo);
		
		List<BoardVo> replyList =service.selectReplyList(boardNum);
		mv.addObject("replyList", replyList);
				
		mv.setViewName("board/read");
		
		
		return mv;
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
	@PostMapping("/insert")
	public ModelAndView doInsertBoard(
			MultipartHttpServletRequest multiReq, 
			@RequestParam(name="report", required=false) MultipartFile multi
			, HttpServletRequest request
			,ModelAndView mv
			, BoardVo vo
			) {
		
		Map<String, String>filePath;
		List<Map<String,String>> fileListPath; 
		try {
			fileListPath = new FileUtil().saveFileList(multiReq, request, null);
			filePath = new FileUtil().saveFile(multi, request, null);
			vo.setBoardOriginalFilename(filePath.get("original"));
			vo.setBoardRenameFilename(filePath.get("rename"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		vo.setBoardWriter("user11"); //TODO
		int result= service.insert(vo);
		return mv;
	}
	
	

	
	
	//답글 작성 페이지 이동
	@GetMapping("/isertReply")
	public ModelAndView viewInsertReply(ModelAndView mv
		, int boardNum //몇번 글에 답글인지
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
		
		//답글작성
		service.insert(vo);
		return mv;
	

		
	}

	
	
	@PostMapping("/insertReplyAjax")
	@ResponseBody
	public String insertReplyAjax(
			BoardVo vo) {
		
//		int boardNum = 4;
//		vo.setBoardNum(boardNum);
//		
//		vo.setBoardContent("임시답내용");
//		vo.setBoardTitle("임시답제목");
		vo.setBoardWriter("user22");
		
		service.insert(vo);
		//연관 답글들 조회해서 ajax로 return
		List<BoardVo> replyList =service.selectReplyList(vo.getBoardNum());
		//ajax는 mv에 실어갈 수 없음		return mv;
		return new Gson().toJson(replyList);
		
	
	}
	
	
	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mv) {
		return mv;
	}

	
	
	
	
	
}
