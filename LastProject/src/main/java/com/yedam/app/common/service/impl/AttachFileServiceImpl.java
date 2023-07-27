package com.yedam.app.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.mapper.AttachFileMapper;
import com.yedam.app.common.service.AttachFileService;
import com.yedam.app.common.service.AttachFileVO;

@Service
public class AttachFileServiceImpl implements AttachFileService{

	@Autowired
	AttachFileMapper attachFileMapper;
	
	@Override
	public boolean addBoardAttachFile(AttachFileVO vo) {
		return attachFileMapper.addBoardAttachFile(vo)>0;
	}

	@Override
	public boolean deleteAttachFile(AttachFileVO vo) {
		return attachFileMapper.deleteAttachFile(vo)>0;
	}

}
