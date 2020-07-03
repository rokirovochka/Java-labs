package network;

import java.io.Serializable;

public class Message implements Serializable {
    private String data;
    private MessageType type;

    public Message(String data) {
        this.data = data;
        this.type = MessageType.TYPE_MESSAGE;
    }

    public Message(String data, MessageType type) {
        this.data = data;
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
