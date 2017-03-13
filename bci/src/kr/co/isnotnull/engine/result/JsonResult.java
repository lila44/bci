package kr.co.isnotnull.engine.result;

import javax.servlet.http.HttpServletRequest;
import kr.co.isnotnull.engine.action.AbstractAction;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONResult;
import com.opensymphony.xwork2.ActionContext;


public class JsonResult extends JSONResult {
 
	/**
	 * RESULTS 의 정보를 JSON 형태로 변경한다.
     * <br><br>
     * 
	 * @param     request       사용자 정보
	 * @param     rootObject    JSON 변경 데이터
	 * @exception JSONException JSON EXCEPTION
	 * @return    json 형태로 변경 된 results 정보
	 ********************************************************************************************/
	@Override
	protected String createJSONString(HttpServletRequest request, Object rootObject) throws JSONException {

		return super.createJSONString(request, ((AbstractAction)ActionContext.getContext().getActionInvocation().getAction()).results);
	}
}




