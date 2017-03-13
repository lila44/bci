package kr.co.isnotnull.engine.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.map.Params;
import kr.co.isnotnull.engine.map.Results;
import kr.co.isnotnull.engine.skill.Constant;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;

import com.opensymphony.xwork2.ActionInvocation;


public class DispatchResult extends StrutsResultSupport {
 
	/**
	 * 비지니스 로직이 완료 된 후 실행
	 * <br> - 기본 레이아웃 출력
     * <br><br>
     * 
	 * @param     location   이동 정보
	 * @param     invocation 실행 정보
	 * @exception Exception  실행 예외
	 ********************************************************************************************/
	public void doExecute(String location, ActionInvocation invocation) throws Exception {
		
		AbstractAction      action    = (AbstractAction)invocation.getAction();
		                              
		Params              params    = action.params;                      // 사용자 정보 
		Results             results   = action.results;                     // 결과 정보 
		HttpServletRequest  request   = ServletActionContext.getRequest();  // 요청 등록
	    HttpServletResponse response  = ServletActionContext.getResponse(); // 등답 등록	    
	    
	    
	    // 사용자 정보, 결과 정보 설정
	    request.setAttribute(Constant.PREFIX_PARAMS_KEY,   params); // 사용자 정보 등록
	    request.setAttribute(Constant.PREFIX_RESULTS_KEY, results); // 결과 정보 등록

	    
	    // 기본 레이아웃
	    if( null != location ){

	        TilesContainer   container        = TilesAccess.getContainer(request.getSession().getServletContext());
	        AttributeContext attributeContext = container.getAttributeContext(request, response);
	        attributeContext.putAttribute(Constant.PREFIX_TILES_BODY_KEY, new Attribute(location)); // /jsp/sample/sampleList.jsp
	         
	        container.render(request.getServletPath(), request, response); // /sample/sampleList.do
	    }
	}
}




