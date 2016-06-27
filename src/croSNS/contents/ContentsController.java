package croSNS.contents;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import facebook4j.FacebookException;

@Controller
public class ContentsController {
	
	@Resource(name="contentsService")
	private ContentsService contentsService;
	
		@RequestMapping(value="/group.do" , method=RequestMethod.GET)
		public ModelAndView group(ContentsVO VO ,ModelAndView mv, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
			request.setCharacterEncoding("UTF-8");
			System.out.println("char : "+request.getCharacterEncoding());
			/*if(session.getAttribute("userLoginInfo") == null){
				mv.setViewName("/login/login");
				return mv;
			}*/
			List<String> name = null;
			List<String> no = null;
			String result = "";
			String status = request.getParameter("preStat");
			String status2 = request.getParameter("nowStat");
			
			System.out.println(request.getParameter("newGroupName"));
			
			
			/*insert*/
			/*System.out.println("ins");*/
			System.out.println(request.getParameter("newGroupName")!=null && request.getParameter("newGroupName")!="");
			
			
			if(request.getParameter("newGroupName")!=null && request.getParameter("newGroupName")!=""){
				VO.setGroupName(request.getParameter("newGroupName"));
				int count =0;
				System.out.println(count);
				count = contentsService.checkGroupName(VO);	
				if(count==1){
					 status = "none";
					 status2 = "block";
					 String message = "�볝겗��꺂�쇈깤�띲겘耶섇쑉�쀣겲�쇻�";
					 mv.addObject("message",message);
					 mv.addObject("status", status);
					 mv.addObject("status2", status2);
					 mv.setViewName("/contents/groupinfo");
				     return mv;
				}
				result = contentsService.newGroupName(VO);
				/*System.out.println(result);*/
				 name = contentsService.groupName(VO);
				 no = contentsService.groupNo(VO);
				 if(name!=null){
						mv.addObject("name", name);
						mv.addObject("no", no);
						}
				status = "block";
				status2 = "none";
				mv.addObject("status", status);
				mv.addObject("status2", status2);
				mv.setViewName("/contents/groupinfo");
				return mv;
			}
			/*delete*/
			System.out.println("del");
			System.out.println(request.getParameter("selectedGroup"));
			System.out.println(request.getParameter("selectedGroup")!=null && request.getParameter("selectedGroup")!="");
			if(request.getParameter("selectedGroup")!=null && request.getParameter("selectedGroup")!=""){
				VO.setGroupNo(request.getParameter("selectedGroup"));
				 result = contentsService.delGroupName(VO);
				 name = contentsService.groupName(VO);
				 no = contentsService.groupNo(VO);
				 if(name!=null){
						mv.addObject("name", name);
						mv.addObject("no", no);
						}
				status = "block";
				status2 = "none";
				mv.addObject("status", status);
				mv.addObject("status2", status2);
				mv.setViewName("/contents/groupinfo");
				return mv;
			}
			 name = contentsService.groupName(VO);
			 no = contentsService.groupNo(VO);
		
			if(name!=null){
				mv.addObject("name", name);
				mv.addObject("no", no);
				}
			
			if(status == null && status2 == null){
				status = "block";
				status2 = "none";
			}
			
			mv.addObject("status", status);
			mv.addObject("status2", status2);
			mv.setViewName("/contents/groupinfo");
			return mv;
	    }
		
	@RequestMapping(value="/contents.do")
	public ModelAndView contents(ModelAndView mv, HttpSession session, HttpServletRequest request) throws Exception {

		//セッションの中身がない場合
		if(session.getAttribute("userLoginInfo") == null){
			mv.setViewName("/login/login");
			return mv;
		}
		mv.setViewName("/contents/totalRelease");
		return mv;
    }
	
	@RequestMapping(value="/post.do", method=RequestMethod.POST)
	public ModelAndView post(HttpServletRequest request) throws IOException, FacebookException, Exception
	{
		// Post Commit service
		String status = contentsService.post(request);
		
		ModelAndView modelAndView = new ModelAndView();
		RedirectView rv = new RedirectView();
		rv.setUrl("/main.do");
		rv.setExposePathVariables(false);
		modelAndView.setView(rv);
		modelAndView.addObject("status", status);

		return modelAndView;
	}
}
