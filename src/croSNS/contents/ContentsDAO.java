package croSNS.contents;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("contentsDAO")
public class ContentsDAO {
	@Autowired
	private SqlSession sqlSession;
	
	@SuppressWarnings("unchecked")
	public List<String> groupName(ContentsVO VO) throws Exception {
		 
		
		return sqlSession.selectList("ContentsDAO.groupName", VO);
	}
	@SuppressWarnings("unchecked")
	public List<String> groupNo(ContentsVO VO) throws Exception {
		return sqlSession.selectList("ContentsDAO.groupNo", VO);
	}
	@SuppressWarnings("unchecked")
	public String newGroupName(ContentsVO VO) throws Exception {
		return sqlSession.selectOne("ContentsDAO.newGroupName", VO);
	}
	@SuppressWarnings("unchecked")
	public String delGroupName(ContentsVO VO) throws Exception {
		return sqlSession.selectOne("ContentsDAO.delGroupName", VO);
	}
	@SuppressWarnings("unchecked")
	public int checkGroupName(ContentsVO VO) throws Exception {
		return sqlSession.selectOne("ContentsDAO.checkGroupName", VO);
	}


}
