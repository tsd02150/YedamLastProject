package com.yedam.app.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.admin.mapper.AdminMapper;
import com.yedam.app.admin.service.AdminService;
import com.yedam.app.admin.service.MembManageVO;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.FaqVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.mall.service.CommonCdVO;
import com.yedam.app.mall.service.OrderVO;
import com.yedam.app.mall.service.ProductVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper adminMapper;
	
	
	// 회원 리스트
	@Override
	public Map<String , Object> getMembList(int page, int perPage) {
		Map<String , Object> map = new HashMap<>();
		List<MembManageVO> list = adminMapper.getMembList(page, perPage);
		map.put("membList", list);
		map.put("membTotal", adminMapper.membCnt());
		return map;
	}
	
	// 회원정지
	@Override
	public int memberBan(List<String> list, Integer period) {
		int cnt = list.size();
		int result =0;
		List<String> being = new ArrayList<>();
		List<String> banList = adminMapper.bannedMemb();
		//			adminMapper.memberBan(str, period);

		//adminMapper.addBanPeriod(str, period);
		// 이미 정지 중인 사람들은 뽑아내고 새로 정지되는 사람은 정지
		for(String str : list) {
			int addcnt = 0;
			for(String str2 : banList) {
				if(str.equals(str2)) {
					addcnt++;
					being.add(str);
				}
			}
			if(addcnt > 0) {
				continue;
			}else {
				result += adminMapper.memberBan(str, period);
			}
		}
		
		// 정지되어있던 사람들은 정지기간 추가
		if(being.size() > 0) {
			result += adminMapper.addBanPeriod(being, period);
		}
		System.out.println("zz"+result);
		System.out.println("zz"+cnt);
		return result;
	}

	// 회원삭제
	@Override
	public int deleteMember(List<String> list) {
		return adminMapper.deleteMember(list);
	}
	// 회원 정지해제
	@Override
	public int returnNorm(List<String> list) {
		return adminMapper.returnNorm(list);
	}
	// 신고글 리스트
	@Override
	public Map<String,Object> reportList(int page, int perPage) {
		Map<String,Object> map = new HashMap<>();
		map.put("reportList", adminMapper.reportList(page, perPage));
		map.put("reportTotal", adminMapper.reportCnt());
		return map;
	}
	
	// 피고 작성 정보
	@Override
	public Map<String, Object> reportInfo(String accused, String rprtNo) {
		Map<String, Object> map = new HashMap<>();
		String boardNo = adminMapper.rprtNoGetBNo(rprtNo);
		map.put("reportBoard", adminMapper.getReportBoard(boardNo));// 피고글
		map.put("reportComments",adminMapper.getReportComments(accused, boardNo)); // 피고댓글
		
		return map;
	}
	
	// 신고처리상태 변경
	@Override
	public int rprtStChange(String rprtNo) {
		System.out.println("svimpl "+ rprtNo);
		return adminMapper.rprtStChange(rprtNo);
	}
	// 신고글 삭제
	@Override
	public int deleteReport(List<String> list) {
		return adminMapper.deleteReport(list);
	}
	
	// 공지사항 리스트
	@Override
	public Map<String, Object> noticeList(int page, int perPage) {
		Map<String, Object> map = new HashMap<>();
		map.put("noticeList", adminMapper.noticeList(page, perPage));
		map.put("noticeTotal", adminMapper.noticeTotal());
		return map;
	}

	// 공지사항 단건조회
	@Override
	public Map<String , Object > noticeDetail(String notiNo) {
		Map<String , Object > map = new HashMap<String, Object>();
		map.put("noticeDetail", adminMapper.noticeDetail(notiNo));
		map.put("attachFileList", adminMapper.getNoticeAttachList(notiNo));
		return map;
	}
	// qna 리스트
	@Override
	public Map<String, Object> qnaList(int page, int perPage) {
		Map<String, Object> map = new HashMap<>();
		map.put("qnaList", adminMapper.qnaList(page, perPage));
		map.put("qnaTotal", adminMapper.qnaTotal());
		return map;
	}
	// faq 리스트
	@Override
	public Map<String, Object> faqList(int page, int perPage) {
		Map<String, Object> map = new HashMap<>();
		map.put("faqList", adminMapper.faqList(page, perPage));
		map.put("faqTotal", adminMapper.faqTotal());
		return map;
	}
	// board 리스트
	@Override
	public Map<String, Object> boardList(int page, int perPage) {
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", adminMapper.boardList(page, perPage));
		map.put("boardTotal", adminMapper.boardTotal());
		return map;
	}
	// chat 리스트
	@Override
	public Map<String, Object> chatList(int page, int perPage) {
		Map<String, Object> map = new HashMap<>();
		map.put("chatList", adminMapper.chatList(page, perPage));
		map.put("chatTotal", adminMapper.chatTotal());
		return map;
	}

	// 공지사항 추가
	@Override
	public Map<String , Object> addNotice(NoticeVO vo) {
		adminMapper.addNotice(vo);
		Map<String , Object> map = new HashMap<String, Object>();
		if(vo.getNotiNo() != null) {
			map.put("code", "success");
			map.put("notiNo", vo.getNotiNo());
			map.put("notiVO", adminMapper.noticeDetail(vo.getNotiNo()));
		}else {
			map.put("code", "fail");
		}
		
		return map;
	}
	
	// 공지사항 수정
	@Override
	public Map<String , Object> modifyNotice(NoticeVO vo) {
		Map<String , Object> map = new HashMap<String, Object>();
		int result = adminMapper.modifyNotice(vo);
		if(result > 0) {
			map.put("code", "success");
			map.put("notiNo", vo.getNotiNo());
			
		}else {
			map.put("code", "fail");
		}
		return map;
	}
	// 공지사항 삭제
	@Override
	public int deleteNotice(List<String> list) {
		return adminMapper.deleteNotice(list);
	}

	// faq 글 조회
	@Override
	public FaqVO faqDetail(String faqNo) {
		return adminMapper.faqDetail(faqNo);
	}

	// qna 글 조회
	@Override
	public QuestionVO qnaDetail(String qstNo) {
		return adminMapper.qnaDetail(qstNo);
	}
	// faq 수정
	@Override
	public Map<String , Object> modifyFaq(FaqVO vo) {
		Map<String , Object> map = new HashMap<String, Object>();
		System.out.println("svimpl : " + vo);
		int result = adminMapper.modifyFaq(vo);
		if(result > 0) {
			map.put("code", "success");
		}else {
			map.put("code", "fail");
		}
		return map;
	}
	// qna 수정
	@Override
	public Map<String , Object> modifyQna(QuestionVO vo) {
		Map<String , Object> map = new HashMap<String, Object>();
		System.out.println("svimpl : " + vo);
		int result = adminMapper.modifyQna(vo);
		if(result > 0) {
			map.put("code", "success");
		}else {
			map.put("code", "fail");
		}
		return map;
	}
	// faq 삭제
	@Override
	public int deleteFaq(List<String> list) {
		return adminMapper.deleteFaq(list);
	}
	// qna 삭제
	@Override
	public int deleteQna(List<String> list) {
		return adminMapper.deleteQna(list);
	}
	
	// faq 작성
	@Override
	public int addFaq(FaqVO vo) {
		return adminMapper.addFaq(vo);
	}
	// board 삭제
	@Override
	public int deleteBoard(List<String> list) {
		return adminMapper.deleteBoard(list);
	}
	// board 조회
	@Override
	public BoardVO boardDetail(String boardNo) {
		return adminMapper.boardDetail(boardNo);
	}
	// room list 불러오기
	@Override
	public List<String> getRoomList() {
		return adminMapper.getRoomList();
	}

	// order 리스트
	@Override
	public Map<String, Object> orderList(int page, int perPage) {
		Map<String,Object> map = new HashMap<>();
		map.put("orderList", adminMapper.orderList(page, perPage));
		map.put("orderTotal", adminMapper.orderTotal());
		
		return map;
		
	}
	
	// product 리스트
	@Override
	public Map<String, Object> productList(int page, int perPage) {
		Map<String,Object> map = new HashMap<>();
		map.put("productList", adminMapper.productList(page, perPage));
		map.put("productTotal", adminMapper.productTotal());
		
		return map;
	}
	
	// 상품 삭제
	@Override
	public int deleteProduct(List<String> list) {
		return adminMapper.deleteProduct(list);
	}
	
	//상품 등록
	@Override
	public Map<String , Object> addProduct(ProductVO prdtVO) {
		adminMapper.addProduct(prdtVO);
		Map<String , Object> map = new HashMap<String, Object>();
		if(prdtVO.getPrdtNo() != null) {
			map.put("code", "success");
			map.put("prdtNo", prdtVO.getPrdtNo());
			//map.put("notiVO", adminMapper.noticeDetail(prdtVO.getNotiNo()));
		}else {
			map.put("code", "fail");
		}
		
		return map;
	}
	
	// 주문 수정
	@Override
	public Map<String, Object> modifyOrder(OrderVO ordVO) {
		Map<String , Object> map = new HashMap<String, Object>();
		System.out.println("svimpl : " + ordVO);
		int result = adminMapper.modifyOrder(ordVO);
		if(result > 0) {
			map.put("code", "success");
		}else {
			map.put("code", "fail");
		}
		return map;
	}
	
	// 상품 수정
	@Override
	public Map<String, Object> modifyProduct(ProductVO prdtVO) {
		Map<String , Object> map = new HashMap<String, Object>();
		System.out.println("svimpl : " + prdtVO);
		int result = adminMapper.modifyProduct(prdtVO);
		if(result > 0) {
			map.put("code", "success");
		}else {
			map.put("code", "fail");
		}
		return map;
	}

	@Override
	public Map<String, Object> productDetail(String prdtNo) {
		Map<String , Object > map = new HashMap<String, Object>();
		map.put("productDetail", adminMapper.productDetail(prdtNo));
		//map.put("attachFileList", adminMapper.getNoticeAttachList(notiNo));
		return map;
	}

	//공통코드 등록
	@Override
	public void addCommonCd(ProductVO prdtVO) {
		adminMapper.addCommonCd(prdtVO);
	}
	// 응답안한 신고 개수
	@Override
	public int nonChkRptCnt() {
		return adminMapper.nonChkRptCnt();
	}
	
}
