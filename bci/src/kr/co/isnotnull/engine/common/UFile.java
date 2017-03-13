package kr.co.isnotnull.engine.common;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UFile {

	
	public final static String PREFIX_EXTENSION_CLASS = ".class";
	
	
	/**
	 * 해당 디렉토리의 모든 파일 목록을 가져온다.
	 * 
	 * @param resourcePath 디렉토리
	 * @param extension    확장자
	 * @return             파일 목록
	 ********************************************************************************************/
	public static List<File> getFileList(File resourcePath, String extension) {
		
		List<File> result       = new ArrayList<File>();
		File[]     filesAndDirs = resourcePath.listFiles();
		List<File> filesDirs    = Arrays.asList(filesAndDirs);
		
		for (File file : filesDirs) {
			
			if( null == extension ){
				result.add(file); 
			}
			else{
				if( -1 != file.getName().indexOf(extension)){
					result.add(file); 
				}
			}
			
			if (!file.isFile()) {
				List<File> deeperList = getFileList(file, extension);
				result.addAll(deeperList);
			}
		}
		
		return result;
	}

	/**
	 * 해당 디렉토리의 모든 클래스 목록을 가져온다.
	 * 
	 * @param resourcePath 디렉토리
	 * @param extension    확장자
	 * @return             클래스 목록
	 ********************************************************************************************/
	public static List getClassListing(String resourcePath, String extension) throws FileNotFoundException, ClassNotFoundException {
		
		boolean isWindow = false;
		List    result   = new ArrayList();
		List    fileList = getFileList(new File(resourcePath), extension);
		
		// window
		if( -1 != resourcePath.indexOf("\\")){
			isWindow = true;
		}
		
		for(int i=0; i<fileList.size(); i++){
			File   file       = (File)fileList.get(i); 
			String fileName   = file.getPath();
			String searchTxt  = "classes" + File.separator;
			
			fileName = fileName.substring(fileName.indexOf(searchTxt) + searchTxt.length(), fileName.indexOf(PREFIX_EXTENSION_CLASS));
			
			if( true == isWindow ){
				fileName = fileName.replaceAll("\\\\", ".");
			}
			else{
				fileName = fileName.replaceAll(File.separator, ".");
			}

			result.add(Class.forName(fileName));
		}
		
		return result;
	}
	
	/**
	 * 확장자 정보를 반환한다.
	 * 
	 * @param fileName 파일명
	 * @return         확장자
	 ********************************************************************************************/
	public static String getFileExtenstion(String fileName){
		
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}
	
	/**
	 * 파일을 삭제한다.
	 * 
	 * @param filePath 파일 경로
	 * @param fileName 파일명
	 * @return         파일을 정상적으로 삭제 했다면 true 오류가 발생했다면 false
	 ********************************************************************************************/
	public static boolean deleteFile(String filePath, String fileName){

		File originalFile = new File(filePath + fileName);
		
		// 원본 파일이 존재 한다면 삭제
		if( true == originalFile.exists() ) {
			return originalFile.delete();
		}
		
		return false;
	}
}
