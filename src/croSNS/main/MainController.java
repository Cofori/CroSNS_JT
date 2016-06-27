package croSNS.main;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import croSNS.login.LoginService;
import croSNS.login.LoginVO;
import croSNS.social.SessionObjects;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.api.TweetsResources;

@Controller
public class MainController {
	
	@Autowired
	private SessionObjects sessionObjects;
	
	@Resource(name="mainService")
	private MainService mainService;
	
	//main.do 컨트롤러=============================================================================================
	@RequestMapping(value="/main.do")
	public ModelAndView main(LoginVO VO, ModelAndView mav,HttpServletRequest request) throws Exception {
		
		System.out.println("main.do 접속/세션 : " + request.getSession().getAttribute("userLoginInfo"));
		
		mav.setViewName("/main/main");
		
		return mav;
		
    }
	
	@RequestMapping(value="/show.do", method=RequestMethod.GET)
	public ModelAndView show(HttpSession session,HttpServletRequest request) throws FacebookException, Exception
	{
		ModelAndView mav = new ModelAndView();


		//���ë��������󪬪ʪ�����
		if(session.getAttribute("userLoginInfo") == null){
			mav.setViewName("/login/login");
			return mav;
		}
		
		session.setAttribute("facebookLoginInfo", 1);
		
		//���̽��� �׽�Ʈ
		
		Facebook facebook = sessionObjects.getFacebook();
		
		String f_id = facebook.getId();//���̽��� ���̵�
		System.out.println(f_id);
		
		String f_name = facebook.getName();//���̽��� �̸�
		ResponseList<Friend> f_friends = facebook.getFriends(f_id);
		System.out.println(f_friends);
		
		

		mav = mainService.show();
		//16:40 update
		/*RedirectView rv = new RedirectView();
		rv.setUrl("/main.do");
		mav.setView(rv);
		if(sessionObjects.getFacebook()==null){return mav; }
		//16:40 update
		String id = sessionObjects.getFacebook().getId();
		User user = sessionObjects.getFacebook().getUser(id);
		ResponseList<Post> feed =  sessionObjects.getFacebook().getFeed();
		ResponseList<Post> post =  sessionObjects.getFacebook().getPosts();
	       
		mav.addObject("post", post);

		mav.addObject("user", user);
		mav.addObject("feed", feed);
		mav.setViewName("/main/main");*/

		return mav;
	}
	
	@RequestMapping(value="/comments.do", method=RequestMethod.POST)
	public ModelAndView comment(HttpServletRequest request) throws IOException, FacebookException, Exception
	{
		System.out.println("���̵� : " + request.getParameter("id") + ", �ڸ�Ʈ : " + request.getParameter("comments"));
		// Post Commit service
		
		String cm = sessionObjects.getFacebook().commentPost(request.getParameter("id"), request.getParameter("comments"));
		System.out.println("���Ⱑ�� ����");
		
		ModelAndView modelAndView = new ModelAndView();
		RedirectView rv = new RedirectView();
		rv.setUrl("/show.do");
		rv.setExposePathVariables(false);
		modelAndView.setView(rv);
		modelAndView.addObject("cm", cm);

		return modelAndView;
	}
	
	/*@RequestMapping(value="/twitterShow.do", method=RequestMethod.GET)
	public ModelAndView twitterShow(HttpSession session,HttpServletRequest request) throws FacebookException, Exception
	{
		ModelAndView mav = new ModelAndView();

		//���ë��������󪬪ʪ�����
		if(session.getAttribute("userLoginInfo") == null){
			mav.setViewName("/login/login");
			return mav;
		}

		mav = mainService.twitterShow();
		System.out.println("���� ������ �ϴ�?");
		return mav;
	}*/

}
