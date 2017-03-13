package kr.co.isnotnull.engine.common.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class UPaging extends ComponentTagSupport{

	/**
	 *     jquery.paging plugin
	 * <br>https://github.com/composite/jQuery.paging
	 * <br><br>
	 * <br> item        : 페이징 요소 태그명, 기본값 "a".
	 * <br> itemClass   : 페이징 요소 중 페이지 수 CSS 클래스, 기본값 "paging-item".
	 * <br> itemCurrent : 현재 페이지를 나타내는 CSS 클래스이며 페이징 요소와 중첩됨, 기본값 "selected".
	 * <br> format      : 페이지를 나타낼 내용, 기본값 "[%d]".
	 * <br> sideClass   : 다음 또는 이전 버튼 CSS 클래스, 기본값 "paging-side".
	 * <br> next        : 다음 버튼 내용. 기본값 "[{5}&gt;]" ("[>]")
	 * <br> prev        : 이전 버튼 내용. 기본값 "[{4}&lt;]" ("[<]")
	 * <br> first       : 첫 페이지 내용. 기본값 "[1&lt;&lt;]"
	 * <br> last        : 마지막 페이지 내용. 기본값 "[&gt;&gt;{6}]"
	 * <br> length      : 페이지 표시할 개수. 기본값 10.
	 * <br> max         : 최대 표현할 페이지 수. 기본값 1.
	 * <br> current     : 현재 페이지 정의. 기본값 1.
	 * <br> href        : a 태그일 때 링크 주소를 정의. 기본값 "#%d"
	 * <br> append      : true 설정 시, 기존 내용을 삭제하지 않고 페이징을 포함시킴. 기본값 false.
	 * <br> event       : 기본 이벤트 활성화. 새로고침 없이 동적으로 페이징 초기화됨. ajax에 유용. 기본값 true. event=true 일 때 가능한 이벤트 정의.
	 * <br>               - onclick : 페이징 버튼 클릭 시 호출. false 반환 시 동적으로 페이지가 바뀌지 않음. 동적 페이징을 원하지만                       
	 * <br>                           href로 인한 링크 이동을 원하지 않을 경우 이벤트 메서드인 event.stopPropagation() 호출.              
     * <br>               - onprev  : 이전 버튼 초기화 시 이벤트. 'this' 는 이전 버튼 요소를 가리킴.(plain DOM. not jQuery!)                 
	 * <br>               - onnext  : 다음 버튼 초기화 시 이벤트. 'this' 는 다음 버튼 요소를 가리킴.(plain DOM. not jQuery!)                 
	 * <br>               - onitem  : 각 페이지 버튼 초기화 시 이벤트. 'this' 는 각 페이지 버튼 요소를 가리킴.                                   
     * <br><br>
     * 
	 * @exception JspException  실행 예외
	 ********************************************************************************************/
	public int doStartTag() throws JspException {
		
		try {
			
			StringBuffer buffer = new StringBuffer();
			
			buffer.append("<div class='paging' id='paging' />                    ");
			buffer.append("<script type='text/javascript'>                       ");
			buffer.append("    $('#paging').paging({                             ");
			buffer.append("         current     : #page_index#                   ");
			buffer.append("        ,max         : #total_size#                   ");
			buffer.append("        ,length      : 10                             ");  
			buffer.append("        ,format      : #format#                       ");
			buffer.append("        ,next        : #next#                         ");
			buffer.append("        ,prev        : #prev#                         ");
			buffer.append("        ,last        : #last#                         ");
			buffer.append("        ,first       : #first#                        ");
			buffer.append("        ,onclick     : function(e, page){             ");
			buffer.append("            $('input[name=_page_index]').val(page);   ");
			buffer.append("        	   $('form[name=form1]').attr('action', ''); ");
			buffer.append("            $('form[name=form1]').submit();           ");
			buffer.append("        }                                             ");
			buffer.append("    });                                               ");
			buffer.append("</script>                                             ");
			buffer.append("<input type='hidden' name='_page_index' value='1' />  "); // 페이지 인덱스
			
			
			if( -1 == page_index ){
				page_index = 1;
			}
			
			if( -1 != total_size ){
				total_size = (total_size % page_size) == 0 ? (total_size / page_size) : (total_size / page_size) + 1;
			}
			
			
			String html = buffer.toString();
			       html = html.replaceAll("#page_index#", String.valueOf(page_index));
			       html = html.replaceAll("#page_size#",  String.valueOf(page_size ));
			       html = html.replaceAll("#total_size#", String.valueOf(total_size));
			       html = html.replaceAll("#format#",     getFormat());
			       html = html.replaceAll("#next#",       getNext  ());
			       html = html.replaceAll("#prev#",       getPrev  ());
			       html = html.replaceAll("#last#",       getLast  ());
			       html = html.replaceAll("#first#",      getFirst ());
			
			pageContext.getOut().print( html );
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return SKIP_BODY;
	}

	private String getFormat(){	return "'<span style=\"margin-left:3px;\"                >{0}</span>'";	    }
	private String getNext()  { return "'<span style=\"margin-left:3px;\" class=\"next\" >다음</span>'";     }
	private String getPrev()  {	return "'<span style=\"margin-left:3px;\" class=\"prev\" >이전</span>'";	    }
	private String getLast()  {	return "'<span style=\"margin-left:3px;\" class=\"last\" >맨 마지막</span>'"; }
	private String getFirst() {	return "'<span style=\"margin-left:3px;\" class=\"first\">맨 처음</span>'";	}
	
	public Component getBean(ValueStack paramValueStack, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
		return null;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}
	public void setTotal_size(int total_size) {
		this.total_size = total_size;
	}
	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}


	private int page_index = -1;
	private int page_size  = -1;
	private int total_size = -1;
}





