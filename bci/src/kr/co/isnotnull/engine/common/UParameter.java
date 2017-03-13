package kr.co.isnotnull.engine.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import kr.co.isnotnull.engine.map.Params;


public class UParameter {

	/**
	 *     REQUEST에 담긴 모든 파라미터를 MAP에 설정하고 반환한다.
	 * <br>파라미터 정보가 배열인 경우 배열로 설정한다.</font>
	 * <br><br>
	 * 
	 * @param request 사용자 정보
	 * @return        파라미터 정보
	 ********************************************************************************************/
	public static Params getParams(HttpServletRequest request){

		Params params = new Params();
    	Set    set    = request.getParameterMap().entrySet();
    	
    	Iterator iter = set.iterator();
		while(iter.hasNext()){

			Map.Entry entry = (Map.Entry)iter.next();
			Object    o     = entry.getValue();
			
			if( String.class == o.getClass() ){
				params.put(entry.getKey().toString(), o);
			}
			
			if( String[].class == o.getClass() ){
				params.put(entry.getKey().toString(), 1 < ((String[])o).length ? o : ((String[])o)[0]);
			}
		}
    	
		
    	return params;
    }
}