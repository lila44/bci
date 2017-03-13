package kr.co.isnotnull.biz.sample.dao;

import java.util.List;
import java.util.Map;

import kr.co.isnotnull.engine.dao.AbstractDao;
import kr.co.isnotnull.engine.map.Params;

public class SampleDao extends AbstractDao {

	/**
	 * 샘플 목록
	 * 
	 * @param params 사용자 정보
	 * @return       목록
	 ********************************************************************************************/
	public List selectSampleList(Params params){

		return (List)super.queryForList("Sample.selectSampleList", params);
	}
	
	/**
	 * 샘플 목록 갯수
	 * 
	 * @param params 사용자 정보
	 * @return       목록 갯수
	 ********************************************************************************************/
	public int selectSampleCount(Params params) {
		
		return (Integer)super.queryForObject("Sample.selectSampleCount", params);
	}
	
	/**
	 * 샘플 조회
	 * 
	 * @param params 사용자 정보
	 * @return       조회 정보
	 ********************************************************************************************/
	public Map selectSample(Params params) {
		
		return (Map)super.queryForObject("Sample.selectSample", params);
	}
	
	/**
	 * 샘플 등록
	 * 
	 * @param params 사용자 정보
	 * @return       삭제 갯수
	 ********************************************************************************************/
	public void insertSample(Params params) {
		
		super.insert("Sample.insertSample", params);
	}
	
	/**
	 * 샘플 수정
	 * 
	 * @param params 사용자 정보
	 * @return       수정 갯수
	 ********************************************************************************************/
	public void updateSample(Params params) {
		
		super.update("Sample.updateSample", params);
	}
	
	/**
	 * 샘플 삭제
	 * 
	 * @param params 사용자 정보
	 * @return       삭제 갯수
	 ********************************************************************************************/
	public void deleteSample(Params params) {
		
		super.delete("Sample.deleteSample", params);
	}

}
