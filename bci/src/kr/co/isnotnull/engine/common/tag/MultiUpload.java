package kr.co.isnotnull.engine.common.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import kr.co.isnotnull.common.encrypt.UBase64;
import kr.co.isnotnull.engine.common.UUUID;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class MultiUpload extends ComponentTagSupport{

	/**
	 *     flash uploader 
	 * <br>http://www.ziwoo.net/zb/view.php?uid=6&startPage=1&boardid=zb_ziwoo_actionscript
	 * <br>https://github.com/composite/jQuery.paging
	 * <br><br>
	 * <br> wmode          : 배경을 투명하게 할려면 0 플래쉬배경은 1ㄴ                               
	 * <br> order_num      : 순서를 기록하는 input 태그의 id 값입니다.              
	 * <br> modify_files   : 수정할 파일 이름 한칸씩 띄워서 순서대로 작성    
	 * <br> modify_filesize: 수정할 파일 사이즈 한칸씩 띄워서 순서대로 작성
	 * <br> modify_fileidx : 수정할 파일 고유번호 한칸씩 띄워서 순서대로 작성
	 * <br> movie_id       : 파일폼 고유ID                                               
	 * <br> flash_width    : 파일폼 너비 (기본값 400 권장최소 300)                              
	 * <br> list_rows      : 파일목록 행 (기본값 5)
	 * <br> limit_size     : 업로드 제한용량 (기본값 10)                                      
	 * <br> limit_total    : 업로드 제한갯수(기본값 무한대, 0 이면 무한대)
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
			buffer.append("    makeSwfMultiUpload3(                                 ");
			buffer.append("         wmode              = '1'                        ");
			buffer.append("        ,order_num          = 'order_index'              ");
			buffer.append("        ,modify_files       = '#modify_files#'           ");
			buffer.append("        ,modify_filesize    = '#modify_filesize#'        ");
			buffer.append("        ,modify_fileidx     = '#modify_fileidx#'         ");
			buffer.append("        ,movie_id           = 'upload#upload_id#'        "); 
			buffer.append("        ,flash_width        = '500'                      "); 
			buffer.append("        ,list_rows          = '#list_rows#'              ");
			buffer.append("        ,limit_size         = '#file_size#'              "); 
			buffer.append("        ,limit_total        = '#limit_total#'            "); 
			buffer.append("        ,file_type_name     = '모든파일'                  "); 
			buffer.append("        ,allow_filetype     = '#allow_file#'             "); 
			buffer.append("        ,deny_filetype      = '*.cgi *.pl *.jsp'         "); 
			buffer.append("        ,_upload_file_path  = '#_upload_file_path#'      ");
			buffer.append("        ,_upload_file_name  = '#_upload_file_name#'      ");
			buffer.append("        ,upload_exe         = '/common/multiUpload.do'        "); 
			buffer.append("    );                                                   ");
			buffer.append("</script>                                                ");
			buffer.append("<input type='hidden' name='_upload_file_path'         /> "); // 새로운 파일 경로
			buffer.append("<input type='hidden' name='_upload_file_name'         /> "); // 새로운 파일 명
			buffer.append("<input type='hidden' name='_upload_file_original_name'/> "); // 파일 명
			buffer.append("<input type='hidden' name='_upload_file_size'         /> "); // 파일 크기
			buffer.append("<input type='hidden' name='_upload_file_extenstion'   /> "); // 파일 확장자	
			buffer.append("<input type='hidden' name='file_seq'                  /> "); // 파일 번호
			
			if( -1 == file_size ){
				file_size = 10;
			}
			
			if( null == allow_file ){
				allow_file = "";
			}
			
        	String newFileName = UUUID.getUUID();

			String html = buffer.toString();
			       html = html.replaceAll("#file_size#",         String.valueOf(file_size      ));
			       html = html.replaceAll("#allow_file#",        String.valueOf(allow_file     ));
			       html = html.replaceAll("#_upload_file_name#", String.valueOf(newFileName    ));
			       html = html.replaceAll("#_upload_file_path#", UBase64.encode(file_path      ));
			       html = html.replaceAll("#upload_id#" ,        String.valueOf(upload_id      ));
			       html = html.replaceAll("#list_rows#" ,        String.valueOf(list_rows      ));
			       html = html.replaceAll("#limit_total#",       String.valueOf(limit_total    ));
			       html = html.replaceAll("#modify_files#",      String.valueOf(modify_files   ));
			       html = html.replaceAll("#modify_filesize#",   String.valueOf(modify_filesize));
			       html = html.replaceAll("#modify_fileidx#",    String.valueOf(modify_fileidx ));
			
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
	

	public void setUpload_id(String upload_id) {
		this.upload_id = upload_id;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public void setAllow_file(String allow_file) {
		this.allow_file = allow_file;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public void setList_rows(int list_rows) {
		this.list_rows = list_rows;
	}
	public void setLimit_total(int limit_total) {
		this.limit_total = limit_total;
	}
	public void setModify_files(String modify_files) {
		this.modify_files = modify_files;
	}
	public void setModify_filesize(String modify_filesize) {
		this.modify_filesize = modify_filesize;
	}
	public void setModify_fileidx(String modify_fileidx) {
		this.modify_fileidx = modify_fileidx;
	}

	private String upload_id  = null;
	private int    file_size  =   -1;
	private String file_path  = null;
	private String allow_file = null;
	private int list_rows = -1;
	private int limit_total = -1;
	private String modify_files    = "";
	private String modify_filesize = "";
	private String modify_fileidx = "";
}





