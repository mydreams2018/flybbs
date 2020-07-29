package cn.kungreat.flybbs.query;

import lombok.Getter;

@Getter
public class Paging {
	private Long totalRow;			//读取到数据-的总行数
	private Long totalPage;			//按分页计算的-总页数
	private Long pageSize = 10L;			//每页显示的-行数
	private Long currentPage=1L;		//当前所在的页数
	private Long lastPage;			//上一页
	private Long nextPage;			//下一页
	private Long topPage =1L;		//第一页
	private Long endPage;			//最后一页
									//下面是计算显示页面的结果
	public void setData(Long totalrow,Long pagesize,Long currentpage) {
		this.totalRow = totalrow;		
		this.pageSize = pagesize;
		if (totalrow <= pagesize) {
			this.totalPage=1L;
			this.endPage =1L;
			this.currentPage =1L;
		}else {
			this.totalPage= totalrow % pagesize == 0L? totalrow / pagesize:totalrow / pagesize + 1L;
			this.endPage = this.totalPage;
			this.currentPage = currentpage > this.endPage?this.endPage:currentpage;
		}
//		this.lastPage = (currentpage-1L) > 0?currentpage-1L :currentpage ;
//		this.nextPage = currentpage < this.totalPage?currentpage+1L:currentpage;
		this.lastPage = (this.currentPage-1L) > 0 ? this.currentPage-1L : this.currentPage;
		this.nextPage = this.currentPage < this.totalPage ? this.currentPage + 1L : this.currentPage;
	}
	
	public Long getStart() {
		return (this.currentPage-1L)*this.pageSize;
	}

	public void setTotalRow(Long totalRow) {
		this.totalRow = totalRow;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public void setPageSize(Long pageSize) {
		if (pageSize !=null){
			this.pageSize = pageSize;
		}

	}

	public void setCurrentPage(Long currentPage) {
		if (currentPage !=null){
			this.currentPage = currentPage;
		}
	}

	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;
	}

	public void setNextPage(Long nextPage) {
		this.nextPage = nextPage;
	}

	public void setTopPage(Long topPage) {
		this.topPage = topPage;
	}

	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}
}
