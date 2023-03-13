package kh.spring.s02.board.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.spring.s02.board.controller.BoardController;
import kh.spring.s02.board.model.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired 
	private SqlSession sqlSession;
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("boardns.insertid", vo);
	}
	public int update(BoardVo vo) {
		return sqlSession.update("boardns.updateid", vo);
	}
	public int updateReadCount(int boardNum) {
		return sqlSession.update("boardns.updateReadCount", boardNum);
	}
	public int updateForReply(int boardNum) {
		return sqlSession.update("boardns.updateForReply", boardNum);
	}
	public int delete(int boardNum /*BoardVo vo 또는 PK 또는 List<PK> */) {
		return sqlSession.delete("boardns.deleteid", boardNum);
	}
	public BoardVo selectOne(int boardNum /* PK */) {
		return sqlSession.selectOne("boardns.selectOneid", boardNum);
	}
	public List<BoardVo> selectList() {
		return sqlSession.selectList("boardns.selectListid");
	}
	
	public List<BoardVo> selectList(int currentPage, int limit) {
//		int offset = (currentPage-1) *limit ;
//		RowBounds rb = new RowBounds(offset, limit);
//		return sqlSession.selectList("boardns.selectListid" , null, rb);
		return sqlSession.selectList("boardns.selectListid" , null, new RowBounds((currentPage-1)*limit, limit));
	}
	
	
	public List<BoardVo> selectList(int currentPage, int limit, String searchWord) {
		return sqlSession.selectList("boardns.selectListid" , searchWord, new RowBounds((currentPage-1)*limit, limit));
	}
	
	
	public int selectOneCount() {
		return sqlSession.selectOne("boardns.selectOneCount");
	}
	
	
	public int selectOneCount(String searchWord) {
		return sqlSession.selectOne("boardns.selectOneCount",searchWord);
	}
	
//	public List<HashMap<String, Object>> tempSelect() {
//		List <HashMap<String, Object>> listmap =sqlSession.selectList("boardns.tempSelect");
//		for(HashMap<String, Object> map : listmap) {
//			System.out.println((String)map.get("boardDate"));
//		}
//		return listmap;
//	}
	
	public List<BoardVo> tempSelect() {
		List<BoardVo> volist = sqlSession.selectList("boardns.tempSelect");
		for(BoardVo vo : volist) {
			System.out.println(vo.getBoardDate());
		}
		return volist;
	}
	public List<BoardVo> selectReplyList(int boardNum) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("boardns.selectReplyList", boardNum);
	}
	
}
