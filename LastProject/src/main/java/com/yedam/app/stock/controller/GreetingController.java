package com.yedam.app.stock.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import com.yedam.app.stock.service.StockService;

@Controller
public class GreetingController {

	@Autowired
	StockService stockService;
	//웹소켓
	
	private SimpMessagingTemplate template;
	
	@Autowired 
	public GreetingController(SimpMessagingTemplate template) {
		this.template = template;
	}
	
	//전체조회 페이지
	@GetMapping("/socket")
	public String empList(Model m) {
		String text = "[" + new Date() + "]:" + "사원목록조회";
        this.template.convertAndSend("/stock/alarm", text); //일반 컨트롤러에서 메세지 보내기 // sendtouser 사용
		return "stock/socketTest";
	}
	
	
  @MessageMapping("/hello") //클라이언트에서 app/hello 로 보내진 문자들이 이곳을 구독한 클라이언트의 화면으로 보내진다 
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }
  
  @GetMapping("/chatClient")
  public void chatClient() {}
}