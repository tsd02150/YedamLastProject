package com.yedam.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.yedam.app.admin.mapper.AdminMapper;
import com.yedam.app.admin.service.AdminService;
import com.yedam.app.admin.service.MembManageVO;
import com.yedam.app.stock.mapper.StockMapper;
import com.yedam.app.stock.service.ItemInfoVO;
import com.yedam.app.stock.service.StockService;


@SpringBootTest
class LastProjectApplicationTests {

	@Autowired
	StockService stockService;
	
	@Autowired
	StockMapper stockMapper;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AdminMapper adminMapper;
	
	private SimpMessagingTemplate template;
	
	@Autowired 
	public LastProjectApplicationTests(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	//@Test
	void stompJSTest() {
		this.template.convertAndSend("/stock/alarm/mem-2","test");
	}
	
	//@Test
	public void scheduler() {
		List<ItemInfoVO> list =stockMapper.todayItemInfo();
		List<String> intList = new ArrayList<>();
		List<Integer> itemNos = new ArrayList<>();
		boolean[] chk = new boolean[47];
		for(ItemInfoVO vo : list) {
			System.out.println("체결된것들기록" + vo.getItemNo().substring(4));
			intList.add(vo.getItemNo().substring(4));
		}
		
		for(int i = 0 ; i <intList.size() ; i++) {
			chk[Integer.parseInt(intList.get(i))] = true;
		}
		
		for(int i = 1 ; i < chk.length ; i++) {
			if(chk[i] == false) {
				itemNos.add(i);
			}
		}
		System.out.println(itemNos);
		
		List<ItemInfoVO> missingList = stockMapper.nonTaInfo(itemNos);
		// 정보가 있는 종목들은 insert
		for(ItemInfoVO vo : list) {
			stockMapper.insertItemInfo(vo);
		}
		System.out.println("체결안된것들"+missingList);
		
		for(ItemInfoVO vo : missingList) {
			stockMapper.insertItemInfo(vo);
		}
	}
	
	
	//@Test
	public void adminTest() {
		List<String> list = new ArrayList<>();
		list.add("mem-1");
		list.add("mem-2");
		list.add("mem-3");
		System.out.println("시작");
		System.out.println("list : " + list);
		List<String> banList = adminMapper.bannedMemb();
		System.out.println("banList : " +banList);
		List<String> nestList = new ArrayList<>();
		for(String str : list) {
			int cnt = 0;
			for(String str2 : banList) {
				if(str.equals(str2)) {
					cnt++;
					System.out.println("nest : "+str);
					nestList.add(str);
				}
			}
			System.out.println("after for2 cnt : "+ cnt);
			if(cnt > 0) {
				continue;
			}else {
				System.out.println("result : " + str);
			}
		}
		
		
	}
}
