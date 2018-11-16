package com.example.board.dto;

import lombok.Data;

@Data
public class Pager {
	private int page; // current page, 현재 페이지 즉 사용자가 보고자 하는 페이지 
	private int size; // rows per page, 페이지당 표시해야 하는 row의 개수 
	private int bsize; // pages per block, 페이지 하단에 표시해야 하는 페이징 number의 개수 

	private int rows; // total elements, 테이블이 갖고 있는 모든 row의 개수 
	private int pages; // total pages, 전체 페이지 수 
	private int blocks; // total blocks, 전체 block 수 

	private int block; // current block, 현재 block
	private int bspage; // current block start page, 현재 block에서 시작 페이지 번호 
	private int bepage; // current block end page, 현재 block에서 종료 페이지 번호 

	public Pager(int page, int size, int bsize, int rows) {
		this.page = page;
		this.size = size;
		this.bsize = bsize;
		this.rows = rows;

		pages = (int) Math.ceil((double) this.rows / this.size);
		blocks = (int) Math.ceil((double) pages / this.bsize);

		block = (int) Math.ceil((double) this.page / this.bsize);
		bepage = block * this.bsize;
		bspage = bepage - this.bsize + 1;
	}
	/*
	 * 전체 글 21개 
	 * 페이지당 표시 row:  5개 
	 * 전체 페이지 수 : 21/5 = 4.X 올림해서 5
	 * 전체 block 사이즈 수 : 4
	 * block : 2
	 */
}
