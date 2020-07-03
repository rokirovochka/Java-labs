package network;

public interface TCPConnectionObserver {
    void onConnectionReady(TCPConnection tcpConnection);

    void onReceiveMessage(TCPConnection tcpConnection, Message message);

    void onDisconnect(TCPConnection tcpConnection);

    void onException(TCPConnection tcpConnection, Exception e);

    void onInformation(TCPConnection tcpConnection, Message message);

}
