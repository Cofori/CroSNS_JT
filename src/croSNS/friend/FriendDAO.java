package croSNS.friend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import croSNS.friend.CustomerListVO;

@Repository("friendDAO")
public class FriendDAO {

	@Autowired
	private SqlSession sqlSession;

	@SuppressWarnings("unchecked")
	public int selectCusSnsId(String count){
		return sqlSession.selectOne("friendDAO.selectCusSnsId", count);
	}

	//SNS ID ���� �ִ� �޼ҵ�
	@SuppressWarnings("unchecked")
	public String insertCustomerList(CustomerListVO clvo){
		return sqlSession.selectOne("friendDAO.insertCustomerList", clvo);
	}
	
	//cus_no������ �޾ƿ��� �޼ҵ�
	@SuppressWarnings("unchecked")
	public int selectCusNo(String cusNo){
		return sqlSession.selectOne("friendDAO.selectCusNo", cusNo);
	}
	
	@SuppressWarnings("unchecked")
	public String insertCustomerInfo(FriendVO vo){
		System.out.println("vo.getCus_name : "+vo.getCus_name());
		return sqlSession.selectOne("friendDAO.insertCustomerInfo", vo);
	}
	
	@SuppressWarnings("unchecked")
	public List<FriendVO> selectCustomerInfo(int sns){
		System.out.println("SNS구별 숫자 : " + sns);
		return sqlSession.selectList("friendDAO.selectCustomerInfo", sns);
	}
	
	//친구검색
	@SuppressWarnings("unchecked")
	public List<FriendVO> selectFriendSerch(String name){
		System.out.println("쿼리에 보내기 전에 name 값 : " + name);
		return sqlSession.selectList("friendDAO.selectFriendSerch", name);
	}
	
	//엑셀출력조건
	public List<FriendVO> exportToExcel(Map<String, String> param){
		return sqlSession.selectList("friendDAO.exportToExcel", param);
	}
	
	public List<FriendVO> selectGroup(int num){
		return sqlSession.selectList("friendDAO.selectGroup", num);
	}
	
	public int updateGroup(FriendVO fv){
		return sqlSession.update("friendDAO.updateGroup", fv);
	}
	
	public int updateMemo(FriendVO fv){
		return sqlSession.update("friendDAO.updateMemo", fv);
	}
	
}
