package croSNS.contents;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ContentsService {

	public String post(HttpServletRequest request) throws Exception;
	
	/*public ModelAndView groupinfo(HttpServletRequest request) throws Exception;*/
	public List<String> groupName(ContentsVO VO) throws Exception;
	public List<String> groupNo(ContentsVO VO) throws Exception;
	public String newGroupName(ContentsVO VO) throws Exception;
	public String delGroupName(ContentsVO VO) throws Exception;
	public int checkGroupName(ContentsVO VO) throws Exception;

}
