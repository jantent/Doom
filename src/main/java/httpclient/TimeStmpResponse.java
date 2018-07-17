package httpclient;

/**
 * Jsonʱ����ӿ���Ӧ
 * 
 * @author tangj
 *
 */
public class TimeStmpResponse {
	
	private String Timestamp;
	
	private String ErrorCode;
	
	private String ErrorString;

	public String getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorString() {
		return ErrorString;
	}

	public void setErrorString(String errorString) {
		ErrorString = errorString;
	}

	@Override
	public String toString() {
		return "TimeStmpResponse [Timestamp=" + Timestamp + ", ErrorCode=" + ErrorCode + ", ErrorString=" + ErrorString
				+ "]";
	}
	
}
