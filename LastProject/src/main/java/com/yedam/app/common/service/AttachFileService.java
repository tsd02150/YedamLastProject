package com.yedam.app.common.service;

import java.util.List;

public interface AttachFileService {
	// board,notice 첨부파일
	public boolean addBoardAttachFile(AttachFileVO vo);
	// 첨부파일 삭제
	public boolean deleteAttachFile(AttachFileVO vo);
	// 첨부파일 찾기
	public List<AttachFileVO> getAttachFileList(AttachFileVO vo);
	// 특정 첨부파일 찾기
	public AttachFileVO getAttachFile(AttachFileVO vo);
}
