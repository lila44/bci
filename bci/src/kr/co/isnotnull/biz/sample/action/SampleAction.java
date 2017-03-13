package kr.co.isnotnull.biz.sample.action;

import kr.co.isnotnull.biz.sample.service.SampleService;
import kr.co.isnotnull.engine.action.AbstractAction;

import com.diquest.admin.biz.search.service.SearchService;
import com.diquest.biz.mail.domain.MailJob;
import com.diquest.biz.mail.service.SendMailService;
import com.diquest.common.engine.schedule.connector.ScheduleConnector;

public class SampleAction extends AbstractAction {

	/**
	 * 샘플 목록
	 ********************************************************************************************/
	public void selectSampleLit(){

		super.addResult("result_1", sampleService.selectSampleList (params));
		super.addResult("result_2", sampleService.selectSampleCount(params));
	}
	
	/**
	 * 샘플 목록 갯수
	 ********************************************************************************************/
	public void selectSampleCount() {
		
		super.addResult("result_1", sampleService.selectSampleCount(params));
	}
	
	/**
	 * 샘플 조회
	 ********************************************************************************************/
	public void selectSample() {
		
		super.addResult("result_1", sampleService.selectSample(params));
	}
	
	/**
	 * 샘플 등록
	 ********************************************************************************************/
	public void insertSample() {
		
		sampleService.insertSample(params);
	}
	
	/**
	 * 샘플 수정
	 ********************************************************************************************/
	public void updateSample() {
		
		sampleService.updateSample(params);
	}
	
	/**
	 * 샘플 삭제
	 ********************************************************************************************/
	public void deleteSample() {
		
		sampleService.deleteSample(params);
	}
	
	/**
	 * 샘플 업로드
	 * <br> 변환 서버 등록
	 ********************************************************************************************/
	public void uploadSample() {
		
		// 변환 서버 등록
//		Class        clazz        = VideoService.class;
//		String       url          = super.getText(Constant.PREFIX_SCHEDULE_SERVER);
//		Connector    connector    = new Connector(url + clazz.getSimpleName());
//		VideoService videoService = (VideoService)connector.service(clazz);
//		
//		if( null != videoService ){
//			
//			ConvertJob domain = new ConvertJob();
//			domain.setFileNo(1);
//			domain.setAuthority(ConvertJob.PREFIX_AUTHORITY_ADMIN);
//			domain.setFilePath(params.getString(Constant.PREFIX_UPLOAD_FILE_PATH));
//			domain.setFileName(params.getString(Constant.PREFIX_UPLOAD_FILE_NAME));
//			
//			videoService.insertJob(domain);
//		}
		
		//params.addParam(key, params.getString("_newFilePath"))
		
		
		System.out.println(params.toString());
	}
		

	/**
	 * 샘플 JSON
	 ********************************************************************************************/
	public void jsonSample() {

		super.addResult("result_1", "test");
	}
	
	
	public void insertSampleEmail(){
		
		StringBuffer html = new StringBuffer();                                                                                          
		html.append("<html>                                                                                                              ");
		html.append("<body>                                                                                                              ");
		html.append("	<br/><a href='http://www.isnotnull.co.kr'>isnotnull 로 바로가기</a>                                                ");
		html.append("	<br/><img src='http://i2.media.daumcdn.net/svc/image/U03/news/201311/06/mydaily/20131106092808295.jpg'/>         ");
		html.append("	<br/>                                                                                                                                                                                                   ");
		html.append("	<br/><br/><br/><img src='http://image.music.naver.net/music/contents/today/content/search/131106_sh_2_508.jpg'/> ");
		html.append("	<br/>네이버 이미지                                                                                                                                                                                                   ");
		html.append("</body>                                                                                                             ");
		html.append("</html>                                                                                                             ");
		
		
		SendMailService sendMailService = (SendMailService)ScheduleConnector.newInstance("http://124.137.201.202:8002/remote/SendMailService", SendMailService.class);
		
		MailJob domain = new MailJob();
		domain.setSendName ("이즈낫널");
		domain.setSendEmail("isnotnull@isnotnull.co.kr");
		domain.setReceiveEmail("jeongchae@daum.net");
		domain.setTitle("즐겁고 유쾌한 이즈낫널에 놀러오세요");
		domain.setContents(html.toString());
	
		sendMailService.insertJob(domain);
	}


	public SampleService sampleService = new SampleService();
	public SearchService searchService = new SearchService();
}
