package com.yedam.app.stock.service;

import lombok.Data;

@Data
public class PageDTO {
	   
	   private int startPage;//현재 페이지기준으로 젤 처음나오는 페이지
	   private int endPage;   //
	   private boolean prev; //이전페이지 여부
	   private boolean next; //이후페이지 여부
	   private int pageNum;
	   
	   public PageDTO(int pageNum, int total) {
	      System.out.println("PageDTO-pageNum : "+pageNum+", total : "+total);
	      this.pageNum = pageNum;
	      //Math.ceil > 올림연산
	      //start,end page 계산
	      this.endPage = (int)Math.ceil(this.pageNum/10.0)*10;
	      this.startPage = this.endPage-9;
	      
	      int realEnd = (int)(Math.ceil(total/10.0));
	      
	      if(realEnd<this.endPage) {
	         this.endPage = realEnd;
	      }
	      
	      this.prev = this.startPage > 1;
	      this.next = this.endPage < realEnd;
	   }
	   
	}
