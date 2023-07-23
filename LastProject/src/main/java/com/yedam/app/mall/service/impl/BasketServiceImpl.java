package com.yedam.app.mall.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.mall.mapper.BasketMapper;
import com.yedam.app.mall.service.BasketService;
import com.yedam.app.mall.service.BasketVO;
import com.yedam.app.member.service.MembVO;

@Service
public class BasketServiceImpl implements BasketService {

	@Autowired
	BasketMapper basketMapper;

//	@Override
//	public int addBasket(BasketVO bskVO) {
//
//		// 장바구니 데이터 체크
//		BasketVO checkBasket = basketMapper.checkBasket(bskVO);
//
//		if (checkBasket != null) {
//			return 2;
//		}
//
//		// 장바구니 등록 & 에러 시 0반환
//		try {
//			return basketMapper.addBasket(bskVO);
//		} catch (Exception e) {
//			return 0;
//		}
//	}
//
//	@Override
//	public int deleteBasket(String bskNo) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int modifyCount(BasketVO bskVO) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public List<BasketVO> getBasket(String membNo) {
//		return basketMapper.getBasket(membNo);
//	}
//
//	@Override
//	public BasketVO checkBasket(BasketVO bskVO) {
//
//		return null;
//	}

	@Override
	public List<BasketVO> getBasketList(BasketVO bskVO) {
		return basketMapper.getBasketList(bskVO);
	}
	

	@Override
	public boolean addCartItem(BasketVO bskVO) {
		return basketMapper.addCartItem(bskVO)>0;
	}

	//전체삭제
	@Override
	public boolean deleteAllBasket(BasketVO bskVO) {
		return basketMapper.deleteAllBasket(bskVO)>0;
	}
	
	//단건 삭제
	@Override
	public boolean deleteBasket(String prdtNo) {
		return basketMapper.deleteBasket(prdtNo) >0;
	}

	@Override
	public MembVO getMembInfo(String membNo) {
		return basketMapper.getMembInfo(membNo);
	}

	@Override
	public String getIntPrdt(String prdtNo) {
		return basketMapper.getIntPrdt(prdtNo);
	}

}
