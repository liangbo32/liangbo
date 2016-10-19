package obor.entry;


public class Consign{
	private String fromTime;
	private String toTime;
	private String totalWaybillNumber;
	private String orderno;
	private int startIndex;
	private int end;
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getTotalWaybillNumber() {
		return totalWaybillNumber;
	}
	public void setTotalWaybillNumber(String totalWaybillNumber) {
		this.totalWaybillNumber = totalWaybillNumber;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
}