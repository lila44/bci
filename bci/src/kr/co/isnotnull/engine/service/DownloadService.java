package kr.co.isnotnull.engine.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;



public class DownloadService {	

	/**
	 * 다운로드
     *
     * @param _filePath     파일 경로
     * @param _fileName     파일 명
	 ********************************************************************************************/
    public void download(String _filePath, String _downloadName) throws Exception{
    	
    	InputStream         inputStream  = null;
        ServletOutputStream outputStream = null;
        
    	try {
    		
	    	HttpServletResponse response = ServletActionContext.getResponse();
	    	byte []             buffer   = new byte[4096];
	        File                file     = new File(_filePath);
	       
	        if( 0 == file.length() ){
	        	throw new FileNotFoundException();
	        }
	
	        response.setContentType(PREFIX_CONTENT_TYPE);
	        response.setHeader     (PREFIX_HEADER_DISPOSITION, PREFIX_HEADER_ATTACHMENT + PREFIX_HEADER_FILE_NAME + URLEncoder.encode(_downloadName, PREFIX_LANGUAGE_TYPE_UTF8));
	        
	        inputStream  = new FileInputStream(file);
	        outputStream = response.getOutputStream();
	        
	        while ( -1 != inputStream.read(buffer, 0, 4096)) {
	        	outputStream.write(buffer, 0, 4096);
	        }
	        
	        outputStream.flush();
    	} 
    	catch (FileNotFoundException e) {
			throw e;
		} 
    	catch (UnsupportedEncodingException e) {
			throw e;
		} 
    	catch (IOException e) {
			throw e;
		}
    	finally{
    		if( null != inputStream ){ try{ inputStream.close();  } catch(IOException ioe) { ioe.printStackTrace(); } }
    		if( null != inputStream ){ try{ outputStream.close(); } catch(IOException ioe) { ioe.printStackTrace(); } }
    	}
	}
    
    private final static String PREFIX_CONTENT_TYPE       = "application/octet-stream";
    private final static String PREFIX_HEADER_DISPOSITION = "Content-Disposition";
    private final static String PREFIX_HEADER_ATTACHMENT  = "attachment;";
    private final static String PREFIX_HEADER_FILE_NAME   = "filename=";
    private final static String PREFIX_LANGUAGE_TYPE_UTF8 = "utf-8";
}




