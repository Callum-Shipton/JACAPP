package components;

public class DataMessage<O extends Object> extends Message {

	O data;

	public DataMessage(MessageId id, O data) {
		super(id);
		this.data = data;
	}

	public O getData() {
		return data;
	}
}
