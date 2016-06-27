package croSNS.friend;

import java.util.List;
import java.util.Map;

import croSNS.friend.CustomerListVO;

public interface FriendService {
	
	public String insertCustomerList(CustomerListVO clvo) throws Exception;

	public int selectCusSnsId(String count) throws Exception;
	
	public int selectCusNo(String cusNo) throws Exception;
	
	public String insertCustomerInfo(FriendVO vo) throws Exception;
	
	public List<FriendVO> selectCustomerInfo(int sns) throws Exception;
	
	public List<FriendVO> selectFriendSerch(String name) throws Exception;
	
	public List<FriendVO> exportToExcel(Map<String, String> param) throws Exception;
	
	public List<FriendVO> selectGroup(int num) throws Exception;
	
	public int updateGroup(FriendVO fv) throws Exception;
	
	public int updateMemo(FriendVO fv) throws Exception;
	
}
