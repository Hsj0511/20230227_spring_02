package kh.spring.s02.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.spring.s02.board.model.vo.BoardVo;
import kh.spring.s02.member.vo.MemberVo;

@Repository
public class MemberDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(MemberVo vo) throws Exception {
		int result = -1;
		try {
			result=sqlSession.insert("memberMapper.insertId",vo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int update(MemberVo vo)throws Exception {
		return sqlSession.update("memberMapper.updateId",vo);
	}
	public int delete(String id)throws Exception {
		return sqlSession.delete("memberMapper.deleteId",id);
	}

	public MemberVo selectOne(String id)throws Exception {
		return sqlSession.selectOne("memberMapper.selectOneId",id);
	}
	
	
	public List<MemberVo> selectList()throws Exception {
		return sqlSession.selectList("memberMapper.selectListId");
	}
	

	
	
	
}
