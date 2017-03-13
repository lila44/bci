package kr.co.isnotnull.common.encrypt;

import java.security.MessageDigest;

public class UMd5 {
	
	/**
	 * MD5 암호화 <br><br>
	 *  
	 * @return 암호화된 String을  반환한다.
	 *********************************************************************************/
    public String encrypt(String key)throws Exception {
    	
    	MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(key.getBytes());

    	byte 			messageDigest[] = algorithm.digest();
        StringBuffer 	hexString 		= new StringBuffer();
        
        for (int i=0, m=messageDigest.length; i<m ;i++) {
		   	String hex = Integer.toHexString(0xFF & messageDigest[i]); 
		   	
		   	if(hex.length()==1) hexString.append('0');
		   	
		   	hexString.append(hex); 
        }
        
        return hexString.toString();
    }
}