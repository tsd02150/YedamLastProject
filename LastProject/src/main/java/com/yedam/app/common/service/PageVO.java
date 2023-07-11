package com.yedam.app.common.service;

import lombok.Data;

@Data
public class PageVO {
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private int pageNum;

	public PageVO(int pageNum, int total) {
		this.pageNum = pageNum;
		this.endPage = (int) Math.ceil(this.pageNum / 10.0) * 10;
		this.startPage = this.endPage - 9;

		int realEnd = (int) (Math.ceil(total / 10.0));
		this.endPage = realEnd < this.endPage ? realEnd : this.endPage;

		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;

	}
}
