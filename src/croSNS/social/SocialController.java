package croSNS.social;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

@Controller
public class SocialController
{
	private static Logger logger = Logger.getLogger(SocialController.class);
	
	RequestToken rt = null;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SessionObjects sessionObjects;
	
	@Resource(name="socialService")
	private SocialService socialService;

	//Facebook signin.do==============================================================================================
	@RequestMapping(value="/signin.do", method=RequestMethod.GET)
	public RedirectView signin() throws IOException
	{   
		Facebook facebook = new FacebookFactory().getInstance();

		// Storing the facebook object for further use
		sessionObjects.setFacebook(facebook);


		// Building the correct URL to return to our application
		StringBuffer callbackURL = request.getRequestURL();
		callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/callback");

		// URL to ask for the acceptance of permissions
		// It attaches the URL to return to your application
		String authAuthorizationURL = facebook.getOAuthAuthorizationURL(callbackURL.toString());

		return new RedirectView(authAuthorizationURL);
	}
	
	//callback==============================================================================================================
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	public RedirectView callback(String code) throws FacebookException, IOException
	{
		//sessionObjects.getFacebook().getOAuthAccessToken(code);	
		
		StringBuffer url = request.getRequestURL().replace(request.getRequestURL().lastIndexOf("/"), 
				request.getRequestURL().length(), "/show.do");
		
		request.getSession().setAttribute("facebookLogin", 2);//페이스북 로그인 세션 추가
		
		return new RedirectView(url.toString());
	}
	
	
	
	
	@RequestMapping(value="/facebookLogOut.do", method=RequestMethod.GET)
	public RedirectView logout(HttpSession session) throws IOException
	{
		//16:40 update
		if(sessionObjects.getFacebook()==null){return new RedirectView("/signin.do");}
		//16:40 update

		String accessToken = sessionObjects.getFacebook().getOAuthAccessToken().getToken();

		// Flush up the Facebook Session
		sessionObjects.setFacebook(null);

		//세션값 제거
		session.setAttribute("facebookLoginInfo", null);
		
		// Log out of action
		StringBuffer next = request.getRequestURL().replace((request.getRequestURL().lastIndexOf("/") + 1), request.getRequestURL().length(), "");
		return new RedirectView("http://www.facebook.com/logout.php?next=" + next.toString() + "&access_token=" + accessToken);
	}
	
	String ck = "dOiXJqOldWOIPMjjDRcYLZnu1";
	String cs = "AGG6Oa9kFIVreXJpoouAFSMVOnUyySEdxaP9XeOJGUv8UNl8ML";
	
	//Twitterログイン===========================================================================================================
	@RequestMapping(value="/twitterSignin.do", method=RequestMethod.GET)
	public RedirectView twitterSignin() throws TwitterException, IOException {
		
		System.out.println("twitterSign.do/시스템세션 : " + request.getSession().getAttribute("userLoginInfo"));
		
		Twitter twitter = new TwitterFactory().getInstance();
		
		// Storing the twitter object for further use
		sessionObjects.setTwitter(twitter);
		
		// Building the correct URL to return to our application
        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/twitterCallback.do");
        
        // URL to ask for the acceptance of permissions
        // It attaches the URL to return to your application
        RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
        sessionObjects.setTwitterRequestToken(requestToken);
        
        return new RedirectView(requestToken.getAuthenticationURL());
		
	}
	
	//트위터 콜백====================================================================================================
	@RequestMapping(value="/twitterCallback.do", method=RequestMethod.GET)
	public RedirectView twitterCallback(String oauth_verifier) throws TwitterException {
		
		System.out.println("twitterCallback.do 접속/세션 : " + request.getSession().getAttribute("userLoginInfo"));
		
		sessionObjects.getTwitter().getOAuthAccessToken(sessionObjects.getTwitterRequestToken(), oauth_verifier);
		sessionObjects.setTwitterRequestToken(null);
		
		String screenName = sessionObjects.getTwitter().getScreenName();
		System.out.println("스크린네임 : " + screenName);
		
		StringBuffer url = request.getRequestURL().replace(request.getRequestURL().lastIndexOf("/"), 
				request.getRequestURL().length(), "/twitterShow.do");
		
		//리퀘스트에 트위터 세션을 생성
		request.getSession().setAttribute("twitterLogin", sessionObjects);
		
		System.out.println("트위터 세션 값 확인 : " + request.getSession().getAttribute("twitterLogin"));

		return new RedirectView(url.toString());
		
	}
	
	//트위터 쇼========================================================================================================
	@RequestMapping(value="/twitterShow.do", method=RequestMethod.GET)
	public ModelAndView twitterShow(TwitterVO vo, ModelMap model, HttpServletRequest request) throws Exception {
		
		System.out.println("twitterShow.do 접속 / 시스템세션 : " + request.getSession().getAttribute("userLoginInfo"));
		System.out.println("트위터세션 : " + request.getSession().getAttribute("twitterLogin"));
		
		ModelAndView mav = new ModelAndView();
		
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter = sessionObjects.getTwitter();//쓰기 편하게 생성
		System.out.println("twitterShow.do 트위터스크린네임 : " + sessionObjects.getTwitter().getScreenName());
		
		//팔로워 스크림네임 시작
		String twitterScreenName = twitter.getScreenName();//스크린네임
		long myId = twitter.getId();
		
		//팔로워 스크린네임 출력
		try {
			IDs followerIDs = twitter.getFollowersIDs(twitterScreenName, -1);
			long[] ids = followerIDs.getIDs();
			for(long id : ids){
				twitter4j.User user = twitter.showUser(id);
				String useScreenName = user.getScreenName();
				System.out.println(useScreenName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//내글빼고 모든 글 보기
		ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
		/*for(Status ht : homeTimeline){
			if(ht.getUser().getId() != myId){
				System.out.println("==============================================================================");
				System.out.println("글쓴이 이름 : " + ht.getUser().getName());
				System.out.println("타임라인 스크린네임 : " + ht.getUser().getScreenName());
				System.out.println("글쓴이 아이디 : " + ht.getUser().getId());
				System.out.println("타임라인 내용 : " + ht.getText());
				System.out.println("타임라인 아이디 : " + ht.getId());
			}
			System.out.println("");
		}*///끝

		
		
		
						
		//SNS연동 구별
		/*if(String.valueOf(sessionObjects.getTwitter().getId()) != null){
			vo.setSys_id(sys_id);
			vo.setSns_list_no(2);
			vo.setCus_sns_id(String.valueOf(sessionObjects.getTwitter().getId()));			
		};*/
		
		//mav.addObject("screenName", screenName);
		//mav.addObject("HomeTimeline", HomeTimeline);
		
		mav.addObject("twitter", twitter);
		mav.addObject("twitterHomeTimeline", homeTimeline);
		mav.addObject("oat", sessionObjects.getTwitter().getOAuthAccessToken());
		mav.setViewName("/main/main");
		return mav;
	}
	
	//트위터 로그아웃=========================================================================================
	@RequestMapping(value="/twitterLogOut.do", method=RequestMethod.GET)
	public RedirectView twitterLogout() throws IOException
	{
		
		System.out.println("twitterLogOut.do 접속/세션 : " + request.getSession().getAttribute("userLoginInfo"));
		System.out.println("twitterLogOut.do 접속/세션 : " + request.getSession().getAttribute("twitterLogin"));
        
		StringBuffer url = request.getRequestURL().replace(request.getRequestURL().lastIndexOf("/"), request.getRequestURL().length(), "/main.do");
		System.out.println("로그아웃 URL : " + url.toString());
		
		request.getSession().setAttribute("twitterLogin", null);
		
        return new RedirectView(url.toString());
	}
	
	@RequestMapping(value="/twitterPost.do", method=RequestMethod.POST)
	public ModelAndView post(@RequestParam String message) throws TwitterException
	{
		sessionObjects.getTwitter().updateStatus(message);

		ModelAndView modelAndView = new ModelAndView("twitterShow.do");		
		modelAndView.addObject("messageSuccess", "See your twitter account! <br />The message has been posted in it!");
		
        return modelAndView;
	}
	
	@RequestMapping(value="/timeline.do")
	public ModelAndView TimeLine() throws TwitterException{
		
		ModelAndView mav = new ModelAndView();
		
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter = sessionObjects.getTwitter();//쓰기 편하게 생성
		System.out.println("timeline.do 트위터스크린네임 : " + sessionObjects.getTwitter().getScreenName());
		
		//팔로워 스크림네임 시작
		String twitterScreenName = twitter.getScreenName();//스크린네임
		long myId = twitter.getId();
		
		//팔로워 스크린네임 출력
		try {
			IDs followerIDs = twitter.getFollowersIDs(twitterScreenName, -1);
			long[] ids = followerIDs.getIDs();
			for(long id : ids){
				twitter4j.User user = twitter.showUser(id);
				String useScreenName = user.getScreenName();
				System.out.println(useScreenName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//내글빼고 모든 글 보기
		ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
		for(Status ht : homeTimeline){
			if(ht.getUser().getId() != myId){
				System.out.println("==timeline.do============================================================================");
				System.out.println("글쓴이 이름 : " + ht.getUser().getName());
				System.out.println("타임라인 스크린네임 : " + ht.getUser().getScreenName());
				System.out.println("글쓴이 아이디 : " + ht.getUser().getId());
				System.out.println("타임라인 내용 : " + ht.getText());
				System.out.println("타임라인 아이디 : " + ht.getId());
			}
			System.out.println("");
		}//끝
		
		mav.addObject("twitter", twitter);
		mav.addObject("twitterHomeTimeline", homeTimeline);
		mav.setViewName("/timeline/timeline");
		return mav;
		
	}
	
}
