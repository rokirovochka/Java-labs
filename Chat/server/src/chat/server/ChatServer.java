package chat.server;

import network.Message;
import network.MessageType;
import network.TCPConnection;
import network.TCPConnectionObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionObserver {
    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    private ChatServer() {
        System.out.println(ServerConstants.SERVER_RUNNING);
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    System.out.println(ServerConstants.TCP_CONNECTION_E + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserNicknames() {
        String result = "";
        for (int i = 0; i < clients.size(); i++) {
            result += clients.get(i).getNickname() + ENDL;
        }
        return result;
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        ClientHandler clientHandler = new ClientHandler(tcpConnection);
        clients.add(clientHandler);
        clientHandler.setNickname(ClientHandler.DEFAULT_NICKNAME+clients.size());

        sendToAllConnections(new Message(getUserNicknames(), MessageType.TYPE_NICKNAME));
        sendToAllConnections(new Message(ServerConstants.CLIENT_CONNECTED + clientHandler.getNickname()));
    }

    @Override
    public synchronized void onReceiveMessage(TCPConnection tcpConnection, Message message) {
        sendToAllConnections(message);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getTcpConnection().getId().equals(tcpConnection.getId())) {
                sendToAllConnections(new Message(ServerConstants.CLIENT_DISCONNECTED + clients.get(i).getNickname()));
                clients.remove(i);
                break;
            }
        }
        sendToAllConnections(new Message(getUserNicknames(), MessageType.TYPE_NICKNAME));
    }

    @Override
    public void onInformation(TCPConnection tcpConnection, Message message) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getTcpConnection().getId().equals(tcpConnection.getId())) {
                clients.get(i).setNickname(message.getData());
                break;
            }
        }
        sendToAllConnections(new Message(getUserNicknames(), MessageType.TYPE_NICKNAME));
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println(ServerConstants.TCP_CONNECTION_E + e);
    }

    private void sendToAllConnections(Message message) {
        System.out.println(message.getData());
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).getTcpConnection().sendMessage(message);
        }
    }

    private static final int SERVER_PORT = 1337;
    public static final String ENDL = "\n";
}
