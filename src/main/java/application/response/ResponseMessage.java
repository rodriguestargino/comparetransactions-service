package application.response;

/**
 * Response message after match records from csv file.
 * 
 * */

public class ResponseMessage {

	private String message;

	private int countUnMatchRecord1;
	
	private int countUnMatchRecord2;

	private int countMatchRecord;
	
	private long countRecordList1;

	private long countRecordList2;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(String message) {
		this.message = message;
	}

	public ResponseMessage(String message, int countUnMatchRecord1,int countUnMatchRecord2, int countMatchRecord, long countRecordList1,
			long countRecordList2) {
		super();
		this.message = message;
		this.countUnMatchRecord1 = countUnMatchRecord1;
		this.countUnMatchRecord2 = countUnMatchRecord2;
		this.countMatchRecord = countMatchRecord;
		this.countRecordList1 = countRecordList1;
		this.countRecordList2 = countRecordList2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCountMatchRecord() {
		return countMatchRecord;
	}

	public void setCountMatchRecord(int countMatchRecord) {
		this.countMatchRecord = countMatchRecord;
	}

	public long getCountRecordList1() {
		return countRecordList1;
	}

	public void setCountRecordList1(long countRecordList1) {
		this.countRecordList1 = countRecordList1;
	}

	public long getCountRecordList2() {
		return countRecordList2;
	}

	public void setCountRecordList2(long countRecordList2) {
		this.countRecordList2 = countRecordList2;
	}

	public int getCountUnMatchRecord1() {
		return countUnMatchRecord1;
	}

	public void setCountUnMatchRecord1(int countUnMatchRecord1) {
		this.countUnMatchRecord1 = countUnMatchRecord1;
	}

	public int getCountUnMatchRecord2() {
		return countUnMatchRecord2;
	}

	public void setCountUnMatchRecord2(int countUnMatchRecord2) {
		this.countUnMatchRecord2 = countUnMatchRecord2;
	}

}
