package kr.co.isnotnull.biz.sample.test;


import kr.co.isnotnull.common.remote.Connector;

import com.diquest.biz.convert.service.AudioService;
import com.diquest.biz.convert.service.ConvertMonitorService;
import com.diquest.biz.convert.service.ImageService;
import com.diquest.biz.convert.service.VideoService;
import com.diquest.biz.download.service.XmlService;


public class ScheduleServerTest {

	
	public static void main(String[] args) throws Exception {
		
//		Connector connector  = new Connector("http://10.25.56.195/remote/SampleService"         );
		Connector connector1 = new Connector("http://10.25.56.195/remote/XmlService"            );
		Connector connector2 = new Connector("http://10.25.56.195/remote/VideoService"          );
		Connector connector3 = new Connector("http://10.25.56.195/remote/ImageService"          );
		Connector connector4 = new Connector("http://10.25.56.195/remote/AudioService"          );
		Connector connector5 = new Connector("http://10.25.56.195/remote/ConvertMonitorService" );

//		SampleService          service  = (SampleService         )connector.service (SampleService.class);
		XmlService             service1 = (XmlService            )connector1.service(XmlService.class);
		VideoService           service2 = (VideoService          )connector2.service(VideoService.class);
		ImageService           service3 = (ImageService          )connector3.service(ImageService.class);
		AudioService           service4 = (AudioService          )connector4.service(AudioService.class);
		ConvertMonitorService  service5 = (ConvertMonitorService )connector5.service(ConvertMonitorService.class);
		
		
		
//		if( null != service ){ 
//
//			boolean isConnect = service.connectJob();
//			System.out.println(isConnect);
//		}
//		
//		for(int i=0; i<1; i++){ 
//			
//			ConvertJob domain = new ConvertJob();
//			domain.setFileNo(i+1);
//			domain.setFilePath("D:/projects/schedule/workspace/schedule/works/upload/");
//			domain.setFileType(ConvertJob.PREFIX_FILE_TYPE_IMAGE_IMG);
//			domain.setAuthority(ConvertJob.PREFIX_AUTHORITY_ADMIN);
//			domain.setFileName("test.png");
//			
//			service3.insertJob(domain);
//		}
//
//		for(int i=0; i<1; i++){ 
//			
//			ConvertJob domain = new ConvertJob();
//			domain.setFileNo(i+2);
//			domain.setFilePath("D:/projects/schedule/workspace/schedule/works/upload/");
//			domain.setFileType(ConvertJob.PREFIX_FILE_TYPE_AUDIO_SND);
//			domain.setAuthority(ConvertJob.PREFIX_AUTHORITY_ADMIN);
//			domain.setFileName("test.wav");
//			service4.insertJob(domain);
//		}
//		
//		for(int i=0; i<1; i++){ 
//			
//			ConvertJob domain = new ConvertJob();
//			domain.setFileNo(i+3);
//			domain.setFilePath("D:/projects/schedule/workspace/schedule/works/upload/");
//			domain.setFileType(ConvertJob.PREFIX_FILE_TYPE_VIDEO_MOV);
//			domain.setFileName("test1.avi");
//			
//			service2.insertJob(domain);
//		}	
		
		// 파일 삭제
//		for(int i=0; i<1; i++){ 
//			
//			ConvertJob domain = new ConvertJob();
//			domain.setFileNo(i+1);
//			
//			service2.cancelJob(domain);
//		}	
//		
		// 파일 수정
//		for(int i=0; i<1; i++){ 
//			
//			ConvertJob domain = new ConvertJob();
//			domain.setFileNo(i+3);
//			domain.setFilePath("D:/projects/schedule/workspace/schedule/works/upload/");
//			domain.setAuthority(ConvertJob.PREFIX_AUTHORITY_ADMIN);
//			domain.setFileType(ConvertJob.PREFIX_FILE_TYPE_AUDIO_SND);
//			domain.setFileName("beoseojeochieo.wav");
//			
//			service4.updateJob(domain);
//		}	
//			
//		
//		
		// 비디오 시간
//		ConvertJob domain = new ConvertJob();
//		domain.setFilePath("D:/projects/schedule/workspace/schedule/works/upload/");
//		domain.setFileName("test100.avi");
//		
//		int runTime = service2.getRunTime(domain);
//		System.out.println(runTime);
	}
}




