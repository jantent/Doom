package httpclient;

/**
 * Jsonʱ����ӿ�����
 * 
 * @author tangj
 *
 */
public class TimeStampRequest {

	private String Type;
	
	private String Source;
	
	private String TimeStamp;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "TimeStampRequest [Type=" + Type + ", Source=" + Source + ", TimeStamp=" + TimeStamp + "]";
	}
	
	
}
