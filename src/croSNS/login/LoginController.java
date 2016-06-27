package croSNS.login;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class LoginController {

	@Resource(name="loginService")
	private LoginService loginService;

	//login.do==============================================================================
	@RequestMapping(value="/login.do")
	public ModelAndView main(HttpServletRequest request) throws Exception {

		System.out.println("login.do 접속/세션 : " + request.getSession().getAttribute("userLoginInfo"));


		ModelAndView mav = new ModelAndView();

		//로그인 세션이 있는 상태에서 접속 하려고 하면 main.do로 보낸다.
		if(request.getSession().getAttribute("userLoginInfo") != null){

			mav.setViewName("/main/main");

			return mav;

		}

		mav.setViewName("/login/login");

		return mav;
	}

	//loginCheck.do=================================================================================================
	@RequestMapping(value="/loginCheck.do")
	public ModelAndView loginCheck(LoginVO VO, HttpServletRequest request) throws Exception {

		System.out.println("loginCheck.do 접속/세션 : " + request.getSession().getAttribute("userLoginInfo"));

		ModelAndView mav = new ModelAndView();

		//로그인 세션이 있는 상태에서 접속 하려고 하면 main.do로 보낸다.
		if(request.getSession().getAttribute("userLoginInfo") != null){

			mav.setViewName("/main/main");

			return mav;

		}

		int loginCk = (Integer)loginService.selectIdCk(VO);
		System.out.println("loginCk : " + loginCk);

		String sysId = loginService.selectId(VO);
		System.out.println("sysId : " + sysId);

		//로그인 정보가 틀린 경우
		if(loginCk != 1){

			System.out.println("로그인 정보가 틀렸습니다.");

			request.getSession().setAttribute("userLoginInfo", null);

			mav.addObject("loginCk", null);
			mav.addObject("sysId", null);

			mav.setViewName("/login/login");

			return mav;
		}

		request.getSession().setAttribute("userLoginInfo", loginCk);

		mav.addObject("loginCk", loginCk);
		mav.addObject("sysId", sysId);

		mav.setViewName("/main/main");

		return mav;
	}

	//logOut.do===============================================================================
	@RequestMapping(value="/logOut.do")
	public ModelAndView logOut(LoginVO VO, ModelAndView mv,HttpSession session,HttpServletRequest request) throws Exception {

		request.getSession().setAttribute("userLoginInfo", null);
		request.getSession().setAttribute("userId", null);

		System.out.println("시스템 로그아웃 완료");

		mv.setViewName("/login/login");
		
		return mv;
	}

}
