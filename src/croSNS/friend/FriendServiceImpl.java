package croSNS.friend;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import croSNS.friend.CustomerListVO;

@Service("friendService")
public class FriendServiceImpl implements FriendService {

	@Resource(name="friendDAO")
	private FriendDAO friendDAO;

	@Override
	public int selectCusSnsId(String count) throws Exception {
		return friendDAO.selectCusSnsId(count);
	}

	@Override
	public String insertCustomerList(CustomerListVO clvo) throws Exception {
		return friendDAO.insertCustomerList(clvo);
	}

	@Override
	public int selectCusNo(String cusNo) throws Exception {
		return friendDAO.selectCusNo(cusNo);
	}

	@Override
	public String insertCustomerInfo(FriendVO vo) throws Exception {
		return friendDAO.insertCustomerInfo(vo);
	}

	@Override
	public List<FriendVO> selectCustomerInfo(int sns) throws Exception {
		return friendDAO.selectCustomerInfo(sns);
	}
	
	@Override
	public List<FriendVO> selectFriendSerch(String name) throws Exception {
		return friendDAO.selectFriendSerch(name);
	}

	@Override
	public List<FriendVO> exportToExcel(Map<String, String> param) throws Exception {
		return friendDAO.exportToExcel(param);
	}

	@Override
	public List<FriendVO> selectGroup(int num) throws Exception {
		return friendDAO.selectGroup(num);
	}

	@Override
	public int updateGroup(FriendVO fv) throws Exception {
		return friendDAO.updateGroup(fv);
	}
	
	@Override
	public int updateMemo(FriendVO fv) throws Exception {
		return friendDAO.updateMemo(fv);
	}

}
