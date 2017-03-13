package kr.co.isnotnull.engine.intercepter;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import kr.co.isnotnull.engine.action.AbstractAction;
import kr.co.isnotnull.engine.common.UFile;
import kr.co.isnotnull.engine.skill.Constant;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class UploadInterceptor extends FileUploadInterceptor {

	/**
	 * WAS TEMP DIRECTORY 에 업로드 된 파일을 해당 업로드 경로로 이동시킨다.
	 * 
	 * @param invocation 실행 정보
	 * @exception        예외발생
	 * @return           invokeActionOnly(해당 ACTION 실행하고 하위 프로세스 실행하지 않음)로 인해 null 처리
	 ********************************************************************************************/
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest      request  = ServletActionContext.getRequest();
		MultiPartRequestWrapper uploader = (MultiPartRequestWrapper)request;
		AbstractAction          action   = (AbstractAction)invocation.getAction();
		
		
		// 파라미터 설정
		action.params.addParam(ActionContext.getContext().getParameters());

		
        File  [] fileList      = uploader.getFiles(Constant.PREFIX_UPLOAD_KEY);
        String[] fileNameList  = uploader.getFileNames(Constant.PREFIX_UPLOAD_KEY);
        
        

        // 파일이 1개라면 처리
        if( 1 == fileList.length ){
        	
        	File file = fileList[0];
        	
        	String fileExtension = UFile.getFileExtenstion(fileNameList[0]);
        	String rootFilePath  = action.getConfig(Constant.PREFIX_DOWNLOAD_ORIGINAL_PATH);
        	String newFilePath   = action.params.getString(Constant.PREFIX_UPLOAD_FILE_PATH);
        	String newFileName   = action.params.getString(Constant.PREFIX_UPLOAD_FILE_NAME);
        	       newFileName   = newFileName + Constant.PREFIX_SYSTEM_FILE_DOT + fileExtension;
        	
        	// 파일 이동
			FileUtils.copyFile(file, new File(rootFilePath + newFilePath + newFileName));
        }	    
        
		
		return Action.SUCCESS;
	}

}


