package kr.co.isnotnull.engine.common.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import kr.co.isnotnull.common.encrypt.UBase64;
import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.common.UUUID;
import kr.co.isnotnull.engine.skill.Constant;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.diquest.common.engine.constant.MultimediaConstant.FILE_KIND;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class UUpload extends ComponentTagSupport{

	/**
	 *     flash uploader 
	 * <br>http://www.ziwoo.net/zb/view.php?uid=6&startPage=1&boardid=zb_ziwoo_actionscript
	 * <br>https://github.com/composite/jQuery.paging
	 * <br><br>
	 * <br> wmode          : 배경을 투명하게 할려면 0 플래쉬배경은 1ㄴ                               
	 * <br> movie_id       : 파일폼 고유ID                                               
	 * <br> flash_width    : 파일폼 너비 (기본값 400 권장최소 300)                              
	 * <br> limit_size     : 업로드 제한용량 (기본값 10)                                      
	 * <br> file_type_name : 파일선택창 파일형식명 (예: 그림파일 엑셀파일 모든파일 등)                      
	 * <br> allow_filetype : 파일선택창 파일형식 (예: *.jpg *.jpeg *.gif *.png)               
	 * <br> deny_filetype  : 업로드 불가형식                                               
	 * <br> upload_exe     : 업로드 담당프로그램                                             
     * <br><br>
     * 
	 * @exception JspException  실행 예외
	 ********************************************************************************************/
	public int doStartTag() throws JspException {
		
		try {
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("<script language='javascript'>                           "); 
			buffer.append("    makeSwfSingleUpload(                                 ");
			buffer.append("         wmode          = '1'                            "); 
			buffer.append("        ,movie_id           = 'upload#file_kind#'        "); 
			buffer.append("        ,flash_width        = '500'                      "); 
			buffer.append("        ,limit_size         = '#file_size#'              "); 
			buffer.append("        ,file_type_name     = ''                         "); 
			buffer.append("        ,allow_filetype     = '#allow_file#'             "); 
			buffer.append("        ,deny_filetype      = '*.cgi *.pl *.jsp'         "); 
			buffer.append("        ,_upload_file_path  = '#_upload_file_path#'      ");
			buffer.append("        ,_upload_file_name  = '#_upload_file_name#'      ");
			buffer.append("        ,upload_exe         = '/common/upload.do'        "); 
			buffer.append("    );                                                   ");
			buffer.append("</script>                                                ");
			buffer.append("<input type='hidden' name='_upload_file_path'         /> "); // 새로운 파일 경로
			buffer.append("<input type='hidden' name='_upload_file_name'         /> "); // 새로운 파일 명
			buffer.append("<input type='hidden' name='_upload_file_original_name'/> "); // 파일 명
			buffer.append("<input type='hidden' name='_upload_file_size'         /> "); // 파일 크기
			buffer.append("<input type='hidden' name='_upload_file_extenstion'   /> "); // 파일 확장자	
			
			
			if( -1 == file_size ){
				file_size = 10;
			}
			
			if( null == allow_file ){
				allow_file = "";
			}
			
			String newFilePath = null;
        	String newFileName = UUUID.getUUID();

        	// 업로드 경로가 고정 일 경우 처리
        	if( null == file_kind){
        		newFilePath = file_path;
        	}
        	else{
        		// 파일 경로 처리
        		AbstractAction action = (AbstractAction)ActionContext.getContext().getActionInvocation().getAction();
    	        switch( FILE_KIND.valueOf( file_kind ) ) {
    			 
    		        case   P : newFilePath = action.getText(Constant.PREFIX_UPLOAD_PICTURE_PATH      ); break; // 이미지 - 사진        
    		        case   I : newFilePath = action.getText(Constant.PREFIX_UPLOAD_IMAGE_PATH        ); break; // 이미지 - 삽화        
    		        case   L : newFilePath = action.getText(Constant.PREFIX_UPLOAD_STUDY_PATH        ); break; // 이미지 - 학습정보      
    		        case   V : newFilePath = action.getText(Constant.PREFIX_UPLOAD_VIDEO_PATH        ); break; // 비디오 - 동영상       
    		        case   A : newFilePath = action.getText(Constant.PREFIX_UPLOAD_ANIMATION_PATH    ); break; // 비디오 - 애니메이션     
    		        case   S : newFilePath = action.getText(Constant.PREFIX_UPLOAD_PRONUNCIATION_PATH); break; // 사운드 - 발음        
    		        case   O : newFilePath = action.getText(Constant.PREFIX_UPLOAD_SOUND_PATH        ); break; // 사운드 - 소리
    			}
        	}
			
			String html = buffer.toString();
			       html = html.replaceAll("#file_size#",         String.valueOf(file_size  ));
			       html = html.replaceAll("#file_kind#",         String.valueOf(file_kind  ));
			       html = html.replaceAll("#allow_file#",        String.valueOf(allow_file ));
			       html = html.replaceAll("#_upload_file_name#", String.valueOf(newFileName));
			       html = html.replaceAll("#_upload_file_path#", UBase64.encode(newFilePath));
			       
			
			pageContext.getOut().print( html );
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return SKIP_BODY;
	}
	
	public Component getBean(ValueStack paramValueStack, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		return null;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public void setAllow_file(String allow_file) {
		this.allow_file = allow_file;
	}
	public void setFile_kind(String file_kind) {
		this.file_kind = file_kind;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}


	private int    file_size  =   -1;
	private String file_kind  = null;
	private String file_path  = null;
	private String allow_file = null;
}





