package orre.gameWorld.core;

import org.apache.log4j.Logger;

public class Message<PayloadDataType> {
	public final MessageType type;
	private final PayloadDataType payloadObject;
	private static final Logger logger = Logger.getLogger(Message.class);

	public Message(MessageType type) {
		this.type = type;
		payloadObject = null;
		if(!(type.dataType instanceof Object)) {
			refuseConstruction();
		}
	}
	
	public Message(MessageType type, PayloadDataType object) {
		this.type = type;
		this.payloadObject = object;
		if(!(object.getClass().isAssignableFrom(type.dataType))) {
			refuseConstruction();
		}
	}
	
	public boolean hasPayload() {
		return payloadObject == null;
	}
	
	public PayloadDataType getPayload() {
		return payloadObject;
	}
	
	private void refuseConstruction() {
		String message = "You can not create a message of type " + type + " without attaching an object of type " + type.dataType;
		RuntimeException exception = new RuntimeException(message);
		logger.error(message, exception);
		throw exception;
	}
}
