package croSNS.friend;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.data.time.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import croSNS.friend.CustomerListVO;
import croSNS.social.SessionObjects;
import facebook4j.Facebook;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

@Controller
public class FriendController {

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SessionObjects sessionObjects;

	@Resource(name = "friendService")
	private FriendService friendService;

	//(트위터)친구 리스트 컨트롤러=======================================================================================
	@RequestMapping(value="/friend.do")
	public ModelAndView friend(HttpSession session,HttpServletRequest request) throws Exception {

		System.out.println("friend.do 컨트롤러 접속============================================");
		
		ModelAndView mav = new ModelAndView();
		
		//트위터 세션 확인
		if(session.getAttribute("twitterLogin") == null){
			mav.setViewName("/main/main");
			System.out.println("트위터 세션값이 null이므로 메인페이지로 돌아감");
		}
		
		System.out.println("트위터 로그인 세션 값 : " + session.getAttribute("twitterLogin"));

		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter = sessionObjects.getTwitter();//���� ���ϰ� ��

		//Ʈ���Ͱ�ü�� ���� ��ٸ� ���� ������ ���� �ʾҴٴ� ���̹Ƿ�
		if(twitter == null){
			PrintWriter writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('Twitter�˫?���󪷪ƪ���������');");
			writer.println("history.back();");
			writer.println("</script>");
			writer.flush();
			return mav;
		}

		//customer_list DB�� insert�ϱ�
		PagableResponseList<User> followersList = twitter.getFollowersList(twitter.getScreenName(), -1);//�ȷο� ����Ʈ

		System.out.println(twitter.getFollowersList(twitter.getScreenName(), -1));


		for(User fl : followersList){//ģ���� ��ŭ �ݺ��� ����

			Map<String, Object> fidList = new HashMap<>();

			CustomerListVO clvo = new CustomerListVO();

			clvo.setSys_id((String)session.getAttribute("sysId"));//�ý��� ���̵� set
			clvo.setSns_list_no(2);//Ʈ���ʹ� ���� 2�� set

			String cusSnsId = String.valueOf(fl.getId());
			clvo.setCus_sns_id(cusSnsId);//ģ���� SNS���̵� set

			int count = friendService.selectCusSnsId(clvo.getCus_sns_id());//DB�˪���ȣ���DB�˪ʪ��ȣ�
			System.out.println(count);

			//DB����ӹ��SNS��ID���ʪ�����
			if(count == 0){
				friendService.insertCustomerList(clvo);//�ý��� ���̵�, SNS����, SNS���̵� ����
				fidList.put(cusSnsId, clvo);

				//SNS���̵� ������ �����Ͽ� �˸��� cus_no���� �ҷ��´�
				int cusNo = friendService.selectCusNo(cusSnsId);
				System.out.println("cusNo" + cusNo);

				//�ҷ��� cus_no���� �Բ� ������ ���������� DB�� �����Ѵ�
				FriendVO fv = new FriendVO();
				fv.setCus_no(cusNo);//cusNo
				fv.setCus_name(fl.getName());//�̸�
				fv.setCus_age(0);//���� Ʈ���� ���� ����
				fv.setCus_gender("none");//���� Ʈ���� ���� ����
				fv.setCus_locale(fl.getLocation());//���� �̰��ϰ�� ���
				friendService.insertCustomerInfo(fv);

			}

		}
		int sns = 2;
		mav.addObject("list", friendService.selectCustomerInfo(sns));
		mav.addObject("group", friendService.selectGroup(0));
		mav.setViewName("/friend/friend");

		return mav;
	}

	@RequestMapping(value="/friend2.do")
	public ModelAndView friend2(HttpSession session,HttpServletRequest request) throws Exception {
		System.out.println("friend2��Ʈ�ѷ��� ��� ��");

		ModelAndView mav = new ModelAndView();

		//���ë��������󪬪ʪ�����
		if(session.getAttribute("userLoginInfo") == null){
			PrintWriter writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('�?���󪷪ƪ���������');");
			writer.println("history.back();");
			writer.println("</script>");
			writer.flush();
			mav.setViewName("/login/login");
			return mav;
		}

		//
		if(session.getAttribute("facebookLoginInfo") == null){
			PrintWriter writer = response.getWriter();
			writer.println("<script type='text/javascript'>");
			writer.println("alert('Facebook�˫?���󪷪ƪ���������');");
			writer.println("history.back();");
			writer.println("</script>");
			writer.flush();
			return mav;
		}

		CustomerListVO clvo = new CustomerListVO();
		clvo.setSys_id((String)session.getAttribute("sysId"));//�ý��� ���̵� set
		clvo.setSns_list_no(1);//���̽����� ���� 1�� set

		Facebook facebook = sessionObjects.getFacebook();
		facebook.getId();
		String cusSnsId = String.valueOf(facebook.getFriendlist(facebook.getId()));
		clvo.setCus_sns_id(cusSnsId);//ģ���� SNS���̵� set


		mav.addObject("list",friendService.selectCustomerInfo(1));
		mav.setViewName("/friend/friend");

		return mav;
	}

	@RequestMapping(value="/friendSerch.do", method=RequestMethod.GET)
	public ModelAndView friendSerch(String friendSerch, HttpSession session, HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		System.out.println("friendSerch에 잘 들어왔니?");
		System.out.println(friendSerch);

		List<FriendVO> list = friendService.selectFriendSerch(friendSerch);
		if(list == null){
			System.out.println("리스트는 널이네?ㅠ");
		}else if(list != null){
			System.out.println("리스트 널이 아니네!!!!!!!!!!");
		}

		System.out.println("리스트 사이즈 : " + list.size());

		mav.addObject("list", null);
		mav.addObject("list", list);
		mav.setViewName("/friend/friend");
		return mav;
	}

	@RequestMapping(value = "/exportToExcel.do", method = RequestMethod.POST)
	public ModelAndView toExcel(HttpServletRequest req, HttpSession session) throws Exception {
		ModelAndView result = new ModelAndView();
		Map<String, String> param = new HashMap<String, String>();
		param.put("some",req.getParameter("some"));    //where에 들어갈 조건

		List<FriendVO> list = friendService.exportToExcel(param); //쿼리
		result.addObject("list",list); // 쿼리 결과를 model에 담아줌

		Calendar now = new GregorianCalendar();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		int MILLISECOND = now.get(Calendar.MILLISECOND);
		StringBuffer str = new StringBuffer();
		str.append( year ).append( month ).append( day ).append( hour ).append( minute ).append( second ).append( MILLISECOND );
		req.setAttribute("filename", str); //고유한 파일명을 위해 현재시간을 넘겨줌 

		result.setViewName("/friend/exportToExcel");// 엑셀로 출력하기 위한 jsp 페이지

		return result;

	}

	//Group
	@RequestMapping(value= "/setGroup.do", method=RequestMethod.POST)
	public RedirectView updateGroup(HttpSession session, HttpServletRequest request) throws Exception  {

		int cusNo = Integer.valueOf(request.getParameter("cusNo"));
		int groupNo = Integer.valueOf(request.getParameter("area"));

		FriendVO fv = new FriendVO();
		fv.setCus_no(cusNo);
		fv.setGroup_no(groupNo);

		int result = friendService.updateGroup(fv);
		System.out.println("업데이트그룹 반환값 : " + result);

		return new RedirectView("/friend.do");
	}

	@RequestMapping(value= "/setMemo.do", method=RequestMethod.GET)
	public RedirectView updateMemo(HttpSession session, HttpServletRequest request) throws Exception  {

		int cusNo = Integer.valueOf(request.getParameter("cusNo"));
		String setMemo = request.getParameter("setMemo");
		System.out.println(setMemo);

		FriendVO fv = new FriendVO();
		fv.setCus_no(cusNo);
		fv.setCus_memo(setMemo);

		int result = friendService.updateMemo(fv);
		System.out.println("업데이트그룹 반환값 : " + result);

		return new RedirectView("/friend.do");
	}
}
