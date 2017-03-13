package kr.co.isnotnull.biz.sample.service;

import java.util.List;
import java.util.Map;

import kr.co.isnotnull.biz.sample.dao.SampleDao;
import kr.co.isnotnull.engine.aop.Transaction;
import kr.co.isnotnull.engine.map.Params;

public class SampleService {
	
	
	/**
	 * 샘플 목록
	 * 
	 * @param params 사용자 정보
	 * @return       목록
	 ********************************************************************************************/
	public List selectSampleList(Params params){
		
		return sampleDao.selectSampleList(params);
	}
	
	

	/**
	 * 샘플 목록 갯수
	 * 
	 * @param params 사용자 정보
	 * @return       목록 갯수
	 ********************************************************************************************/
	public int selectSampleCount(Params params) {
		
		return sampleDao.selectSampleCount(params);
	}
	
	/**
	 * 샘플 조회
	 * 
	 * @param params 사용자 정보
	 * @return       조회 정보
	 ********************************************************************************************/
	public Map selectSample(Params params) {
		
		return sampleDao.selectSample(params);
	}
	
	/**
	 * 샘플 등록
	 * 
	 * @param params 사용자 정보
	 * @return       등록 갯수
	 ********************************************************************************************/
	@Transaction(isTransaction=true)
	public void insertSample(Params params) {
		
		sampleDao.insertSample(params);
	}
	
	/**
	 * 샘플 수정
	 * 
	 * @param params 사용자 정보
	 * @return       수정 갯수
	 ********************************************************************************************/
	@Transaction(isTransaction=true)
	public void updateSample(Params params) {
		
		sampleDao.updateSample(params);
	}
	
	/**
	 * 샘플 삭제
	 * 
	 * @param params 사용자 정보
	 * @return       삭제 갯수
	 ********************************************************************************************/
	@Transaction(isTransaction=true)
	public void deleteSample(Params params) {
		
		sampleDao.deleteSample(params);
	}
	
	
	public SampleDao sampleDao = new SampleDao();
}




