package kh.spring.s02.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import kh.spring.s02.board.model.dao.BoardDao;
import kh.spring.s02.board.model.vo.BoardVo;


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao dao;
	
	
	@Override
	@Transactional
	public int insert(BoardVo vo) {
		if(vo.getBoardNum() !=0) {
			// 답글 (원글은 0임)
			dao.updateForReply(vo.getBoardNum());
		}
		return dao.insert(vo);
	}

	@Override
	public int update(BoardVo vo) {
		return dao.update(vo);
	}

	@Override
	public int delete(int boardNum) {
		return dao.delete(boardNum);
	}

	@Override
	public BoardVo selectOne(int boardNum, String writer) {
		BoardVo result = dao.selectOne(boardNum);
		
		if(result!=null && !result.getBoardWriter().equals(writer)) {
			dao.updateReadCount(boardNum);
		}return result;
		
		//		if(dao.updateReadCount(boardNum)>0) {
//			return dao.selectOne(boardNum);
//		} else {
//			return null;
//		}
	}

	@Override
	public List<BoardVo> selectList() {
		return dao.selectList();
	}

	@Override
	public int selectOneCount() {
		return dao.selectOneCount();
	}
	
	@Override
	public int selectOneCount(String searchWord) {
		// TODO Auto-generated method stub
		return dao.selectOneCount(searchWord);
	}
	
	public List<BoardVo> selectList(int currentPage, int limit) {
		return dao.selectList(currentPage,limit);
	}

	@Override
	public List<BoardVo> selectList(int currentPage, int limit, String searchWord) {
		// TODO Auto-generated method stub
		return dao.selectList(currentPage,limit,searchWord) ;
	}

	@Override
	public List<BoardVo> selectReplyList(int boardNum) {
		// TODO Auto-generated method stub
		return  dao.selectReplyList(boardNum);
	}

	@Override
	public List<BoardVo> selectReplyList(int boardNum, int currentPage, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


}
