package chat.server;

import network.Message;
import network.MessageType;
import network.TCPConnection;
import network.TCPConnectionObserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ChatServer implements TCPConnectionObserver {
    public static void main(String[] args) {
        new ChatServer();
    }

    private static Logger log = Logger.getLogger(ChatServer.class.getName());

    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private ArrayList<Message> history = new ArrayList<>();

    private ChatServer() {
        log.info(ServerConstants.SERVER_RUNNING);
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());
                } catch (IOException e) {
                    log.info(ServerConstants.TCP_CONNECTION_E + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserNicknames() {
        String result = ServerConstants.EMPTY_STRING;
        for (int i = 0; i < clients.size(); i++) {
            result += clients.get(i).getNickname() + ENDL;
        }
        return result;
    }

    public String getHistoryMessagesText() {
        String result = ServerConstants.EMPTY_STRING;
        for (int i = 0; i < history.size(); i++) {
            result += history.get(i).getData();
            if (i != history.size() - 1) result += ENDL;
        }
        return result;
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        ClientHandler clientHandler = new ClientHandler(tcpConnection);
        clients.add(clientHandler);
        clientHandler.setNickname(ClientHandler.DEFAULT_NICKNAME + clients.size());
        sendToAllConnections(new Message(getUserNicknames(), MessageType.TYPE_NICKNAME));

        if (!getHistoryMessagesText().isEmpty()) tcpConnection.sendMessage(new Message(getHistoryMessagesText()));

        Message message = new Message(ServerConstants.CLIENT_CONNECTED + clientHandler.getNickname());
        sendToAllConnections(message);
        history.add(message);
    }

    @Override
    public synchronized void onReceiveMessage(TCPConnection tcpConnection, Message message) {
        sendToAllConnections(message);
        history.add(message);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getTcpConnection().getId().equals(tcpConnection.getId())) {
                Message message = new Message(ServerConstants.CLIENT_DISCONNECTED + clients.get(i).getNickname());
                sendToAllConnections(message);
                history.add(message);
                tcpConnection.disconnect();
                clients.remove(i);
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
        log.info(ServerConstants.NICKNAME_CHANGED);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        log.info(ServerConstants.TCP_CONNECTION_E + e);
    }

    private void sendToAllConnections(Message message) {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).getTcpConnection().sendMessage(message);
        }
        log.info(message.getData() + ServerConstants.MESSAGE_DELIVERED);
    }

    private static final int SERVER_PORT = 1337;
    public static final String ENDL = "\n";
}
