/**
 * 存储分页查询返回信息.
 */
package obor.entry;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {
	/** 当前分页号. */
	private int currentPage;

	/** 总信息条数. */
	private long totalCount;

	/** 总页数. */
	private int pages;

	/** 查询结果. */
	@SuppressWarnings("unchecked")
	private List result;

	/** 一页显示条数. */
	private int pageSize;

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the pages.
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @param pages
	 *            The pages to set.
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * @return Returns the totalCount.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            The totalCount to set.
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		// 计算总页数
		if (0 < this.pageSize) {
			if (0 == this.totalCount % this.pageSize) {
				this.pages = (int) (this.totalCount / this.pageSize);
			} else {
				this.pages = (int) (this.totalCount / this.pageSize + 1);
			}
		}
	}

	/**
	 * @return Returns the result.
	 */
	@SuppressWarnings("unchecked")
	public List getResult() {
		return result;
	}

	/**
	 * @param result
	 *            The result to set.
	 */
	@SuppressWarnings("unchecked")
	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
