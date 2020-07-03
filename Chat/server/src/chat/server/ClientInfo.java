package chat.server;

import network.TCPConnection;

import java.util.UUID;

public class ClientInfo {

    private final TCPConnection tcpConnection;
    private final UUID id;
    private String nickname;

    public ClientInfo(TCPConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
        id = UUID.randomUUID();
        nickname = DEFAULT_NICKNAME;
    }

    public TCPConnection getTcpConnection() {
        return tcpConnection;
    }

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static final String DEFAULT_NICKNAME = "user";
}
