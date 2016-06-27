package croSNS.contents;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import croSNS.social.SessionObjects;
import facebook4j.FacebookException;
import facebook4j.Media;
import facebook4j.PhotoUpdate;
import facebook4j.VideoUpdate;


@Service("contentsService")
public class ContentsServiceImpl implements ContentsService {

	@Autowired
	private SessionObjects sessionObjects;
	
	//////////////////////////
	
	@Resource(name="contentsDAO")
	private ContentsDAO contentsDAO;
	
	public List<String> groupName(ContentsVO VO) throws Exception{
	
		
		List<String> list = contentsDAO.groupName(VO);
		
		return list;
	}
	public List<String> groupNo(ContentsVO VO) throws Exception{
	
		
		List<String> list = contentsDAO.groupNo(VO);
		
		return list;
	}
	
	public int checkGroupName(ContentsVO VO) throws Exception{
		System.out.println(VO.getNewGroupName());
		int result = contentsDAO.checkGroupName(VO);
		
		return result;
	}
	public String newGroupName(ContentsVO VO) throws Exception{
				
		String result = contentsDAO.newGroupName(VO);
				
		return result ;
	}
	public String delGroupName(ContentsVO VO) throws Exception{
		
		
		
		String result = contentsDAO.delGroupName(VO);
				
		return result ;
	}
	
	//////////////////////////
	
	public String post(HttpServletRequest request) throws Exception{
		
		String fsl = File.separator;
		 
        // MAX SIZE : 5Mbyte
        int maxSize  = 1024*1024*5;   
        // Web Server save file Path 
        String root = request.getSession().getServletContext().getRealPath(fsl);

		String rootPath = root+"/upload" +fsl;
		String uploadFile = "";
		String status = "";
		try {
			MultipartRequest multi = new MultipartRequest(request, rootPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
			
			Media media;
			
			//System.out.println("rootPath: " + rootPath);
			String message = "";
			
			if(!multi.getParameter("message").equals("")) {
				message = multi.getParameter("message");
			}
			
			
			if(multi.getFilesystemName("photo") != null) {
				uploadFile = multi.getFilesystemName("photo");
	            media=new Media(new File(rootPath + uploadFile));
				PhotoUpdate post= new PhotoUpdate(media).message(message);
				status = sessionObjects.getFacebook().postPhoto(post);
				
				
			} else if(multi.getFilesystemName("video") != null){
				uploadFile = multi.getFilesystemName("video");
				Media videoUpdate=new Media(new File(rootPath + uploadFile));
				System.out.println("message : " + message);
				VideoUpdate movie= new VideoUpdate(videoUpdate).title(message);
				movie.description(message);
				status = sessionObjects.getFacebook().postVideo(movie);
			
			} else if(!multi.getParameter("url").equals("")) {
				String url = multi.getParameter("url");
				status = sessionObjects.getFacebook().postLink(new URL(url), message);
			
			} else if(!multi.getParameter("message").equals("")){
				status = sessionObjects.getFacebook().postStatusMessage(message);
			} else {
				status = "error";
			}

		} catch (FacebookException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		
		return status;
	}
}
