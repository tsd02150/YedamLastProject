package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.service.AttachFileVO;

public interface AttachFileMapper {
	public int addBoardAttachFile(AttachFileVO vo);
	// 첨부파일 삭제
	public int deleteAttachFile(AttachFileVO vo);
}
