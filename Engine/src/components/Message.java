package components;

public class Message {
	private MessageId id;

	public Message(MessageId id) {
		this.id = id;
	}

	public MessageId getId() {
		return id;
	}
}
