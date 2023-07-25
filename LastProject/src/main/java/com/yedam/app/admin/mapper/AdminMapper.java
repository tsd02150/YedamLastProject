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
	// 회원 삭제
	public int deleteMember(List<String> list);
	// 회원 정지해제
	public int returnNorm(List<String> list);
	// 정지된 회원 조회
	public List<String> bannedMemb();
	// 정지기간 추가
	public int addBanPeriod(@Param("list")List<String> list , @Param("period") Integer period);
}
