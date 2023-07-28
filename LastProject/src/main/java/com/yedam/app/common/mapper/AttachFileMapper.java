package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.service.AttachFileVO;

public interface AttachFileMapper {
	public int addBoardAttachFile(AttachFileVO vo);
	// 첨부파일 삭제
	public int deleteAttachFile(AttachFileVO vo);
	// 해당 게시물의 첨부파일 전체 찾기
	public List<AttachFileVO> getAttachFileList(AttachFileVO vo);
	// 특정 첨부파일 찾기
	public AttachFileVO getAttachFile(AttachFileVO vo);
}
