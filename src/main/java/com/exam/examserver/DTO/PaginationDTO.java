package com.exam.examserver.DTO;

import java.util.List;

import com.exam.examserver.models.exam.Quiz;

public class PaginationDTO {
	
	private List<Quiz> content;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean lastPage;
	
	public PaginationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Quiz> getContent() {
		return content;
	}

	public void setContent(List<Quiz> content) {
		this.content = content;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	@Override
	public String toString() {
		return "PaginationDTO [content=" + content + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize
				+ ", totalElement=" + totalElement + ", totalPages=" + totalPages + ", lastPage=" + lastPage + "]";
	}
	
	
	

}
