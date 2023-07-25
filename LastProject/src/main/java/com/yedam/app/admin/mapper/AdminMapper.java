package com.yedam.app.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.app.admin.service.MembManageVO;

public interface AdminMapper {
	// 멤버리스트
	public List<MembManageVO> getMembList(@Param("page")int page , @Param("perPage")int perPage);
	// 멤버 전체 수
	public int membCnt();
	// 회원 정지
	public int memberBan(@Param("membNo") String membNo , @Param("period") Integer period);
}
