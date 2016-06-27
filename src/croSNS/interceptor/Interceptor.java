package croSNS.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import croSNS.social.SessionObjects;
import croSNS.social.SocialController;

public class Interceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("인터셉터에 들어온 URL검사 : " + request.getRequestURI());


		//트위터세션확인이 필요한 컨트롤러 주소
		ArrayList<String> twitterURL = new ArrayList<>();

		twitterURL.add("/twitterShow.do");
		twitterURL.add("/exportToExcel.do");
		twitterURL.add("/setGroup.do");
		twitterURL.add("/setMemo.do");
		twitterURL.add("/twitterLogOut.do");
		twitterURL.add("/friend.do");

		/*ArrayList<String> facebookURL = new ArrayList<>();

		facebookURL.add("/show.do");
		 */
		//시스템로그인 세션 확인
		try {
			if(request.getSession().getAttribute("userLoginInfo") == null ){
				response.sendRedirect("/login.do"); 

				PrintWriter writer = response.getWriter();
				writer.println("<script type='text/javascript'>");
				writer.println("alert('시스템 로그인 세션이 없음으로 로그인창으로 이동합니다.');");
				writer.println("history.back();");
				writer.println("</script>");
				writer.flush();

				return false;
			}

			if(twitterURL.contains(request.getRequestURI())){
				try {
					SessionObjects sessionObjects = (SessionObjects) request.getSession().getAttribute("twitterLogin");
					sessionObjects.getTwitter().getScreenName();
					System.out.println("트위터 세션이당");
					return true;
				} catch (Exception e) {
					System.out.println("트위터세션 없다잉");
					response.sendRedirect("/login.do"); 
					return true;
				}
			};

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("시스템로그인 세션 확인되었습니다.");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}

