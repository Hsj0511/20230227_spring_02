package kh.spring.s02.member.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kh.spring.s02.member.service.MemberService;
import kh.spring.s02.member.service.MemberServiceImpl;
import kh.spring.s02.member.vo.MemberVo;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService service;


	@GetMapping("/signUp")
	public ModelAndView viewInsert(ModelAndView mv) throws Exception {
		mv.setViewName("member/signUp");
		return mv;
	}
	@PostMapping("/signUp")
	public ModelAndView insert(ModelAndView mv
			, MemberVo vo
			, String bbb
			, String id
			, RedirectAttributes rttr
			) throws Exception{
		int result = 1;
		try {
			result = service.insert(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				if(result > 0) {
				// 방법1 - 사용불가방법
				//	mv.setViewName("redirect:/msg=회원가입성공");
				// 방법2 
				//	mv.addObject("msg", "회원가입성공");
					rttr.addFlashAttribute("msg", "회원가입성공");
					mv.setViewName("redirect:/");
				} else {
					rttr.addFlashAttribute("msg", "회원가입실패");
					mv.setViewName("redirect:/member/signUp");
				}
		return mv;
	}
	
	@GetMapping("/update")
	public ModelAndView viewUpdate(ModelAndView mv
			,String id)throws Exception {
		MemberVo vo = service.selectOne(id);
		mv.addObject("membervo",vo);
		mv.setViewName("/member/update");
		return mv;
	}
	
	@PostMapping("/update")
	public ModelAndView update(ModelAndView mv, MemberVo vo) throws Exception {
		 service.update(vo);
		 return mv;
	}
	
	
	@GetMapping("/delete")
	public ModelAndView delete(ModelAndView mv
			,String id
			) throws Exception{
		service.delete(id);
		return mv;
	}
	
	@GetMapping("/info")
	public ModelAndView selectOne(ModelAndView mv
		,String id
		) throws Exception{
		if(id == null) {
			MemberVo result = service.selectOne(id);
			mv.addObject("membervo", result);
			mv.setViewName("redirect:/");
			return mv;
		}
	return mv;	
	}
	
	@GetMapping("/list")
	public  ModelAndView selectList(ModelAndView mv) throws Exception {
		List<MemberVo> result = service.selectList();
		mv.addObject("memberlist", result);
		return mv;
	}

	
	
	
	
	
	@ExceptionHandler(NullPointerException.class )
	public ModelAndView memberNullPointExceptionHandler(Exception e) {
		e.printStackTrace();
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", e.getMessage() + " 오류가 발생했습니다. ");
		mv.setViewName("error/error500");
		return mv;
		
	}
	
	@ExceptionHandler(SQLException.class )
	public ModelAndView memberSQLExceptionHandler(Exception e) {
		e.printStackTrace();
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", e.getMessage() + " 오류가 발생했습니다. ");
		mv.setViewName("error/error500");
		return mv;
		
	}
	@ExceptionHandler
	public ModelAndView memberExceptionHandler(Exception e) {
		e.printStackTrace();
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", e.getMessage() + " 오류가 발생했습니다. ");
		mv.setViewName("error/error500");
		return mv;
		
	}
	
	
	
	
}



