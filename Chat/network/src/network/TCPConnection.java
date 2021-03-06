package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TCPConnection {
    private final Socket socket;
    private final TCPConnectionObserver eventObserver;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private UUID id;
    private Boolean alreadyReading = false;

    private static ScheduledThreadPoolExecutor executorService = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(NetworkConstants.AMOUNT_OF_THREADS);
    private Runnable task;


    public TCPConnection(TCPConnectionObserver eventObserver, String idAddr, int port) throws IOException {
        this(eventObserver, new Socket(idAddr, port));
    }

    public TCPConnection(TCPConnectionObserver eventObserver, Socket socket) throws IOException {
        id = UUID.randomUUID();

        this.eventObserver = eventObserver;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());

        InputStream socketInputStream = socket.getInputStream();
        in = new ObjectInputStream(socketInputStream);

        eventObserver.onConnectionReady(TCPConnection.this);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    if (socketInputStream.available() == 0 || alreadyReading == true)
                        return;
                    alreadyReading = true;

                    Message msg = (Message) in.readObject();
                    if (msg.getType() == MessageType.TYPE_MESSAGE)
                        eventObserver.onReceiveMessage(TCPConnection.this, msg);
                    else if (msg.getType() == MessageType.TYPE_DISCONNECT) {
                        eventObserver.onDisconnect(TCPConnection.this);
                    } else
                        eventObserver.onInformation(TCPConnection.this, msg);
                } catch (Exception e) {
                    eventObserver.onException(TCPConnection.this, e);
                }
                alreadyReading = false;
            }

        };
        executorService.scheduleWithFixedDelay(task, NetworkConstants.INITIAL_DELAY, NetworkConstants.DELAY, NetworkConstants.TIME_UNIT);
    }

    public synchronized void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            eventObserver.onException(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        try {
            executorService.remove(task);
            socket.close();
        } catch (IOException e) {
            eventObserver.onException(TCPConnection.this, e);
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return NetworkConstants.TCP_CONNECTION + socket.getInetAddress() + NetworkConstants.SEPARATOR + socket.getPort();
    }
}
