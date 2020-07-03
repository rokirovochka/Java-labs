package chat.client;

import network.Message;
import network.MessageType;
import network.TCPConnection;
import network.TCPConnectionObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionObserver {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();
            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField();
    private final JTextField fieldInput = new JTextField();
    private final JButton disconnectButton = new JButton(ClientConstants.DISCONNECT_BUTTON);

    private DefaultListModel<String> dlm = new DefaultListModel<String>();
    private JList listOfNicknames = new JList<String>(dlm);
    private static Boolean firstTime = true;

    private TCPConnection connection;

    private ClientWindow() {
        setWindow();
        setTextArea();
        setLayouts();

        setupActionListeners();

        setVisible(true);
        try {
            connection = new TCPConnection(this, ClientConstants.IP_ADDR, ClientConstants.PORT);
        } catch (IOException e) {
            printMessage(new Message(ClientConstants.CONNECTION_E + e));
        }
    }

    private void setWindow() {
        setTitle(ClientConstants.NAME_OF_CHAT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(ClientConstants.WIDTH, ClientConstants.HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    private void setTextArea() {
        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);
    }

    private void setLayouts() {
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);
        add(listOfNicknames, BorderLayout.WEST);
        listOfNicknames.setLayoutOrientation(JList.VERTICAL);

        add(disconnectButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = fieldInput.getText();
        if (text.equals("")) return;
        fieldInput.setText(null);
        connection.sendMessage(new Message(fieldNickname.getText() + ClientConstants.SEPARATOR + text));
    }

    public void setupActionListeners() {
        fieldInput.addActionListener(this);
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection.sendMessage(new Message(ClientConstants.STATUS_WHEN_DISCONNECT, MessageType.TYPE_DISCONNECT));
                System.exit(ClientConstants.EXIT_SUCCESS);
            }
        });
        fieldNickname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection.sendMessage(new Message(fieldNickname.getText(), MessageType.TYPE_NICKNAME));
            }
        });
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage(new Message(ClientConstants.CONNECTION_READY));
    }

    @Override
    public void onReceiveMessage(TCPConnection tcpConnection, Message message) {
        printMessage(message);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage(new Message(ClientConstants.CONNECTIONE_CLOSE));
    }

    @Override
    public void onInformation(TCPConnection tcpConnection, Message message) {
        String[] nicknames = message.getData().split(ENDL);
        dlm.clear();
        for (int i = 0; i < dlm.size(); i++) {
            dlm.set(i, nicknames[i]);
        }
        for (int i = dlm.size(); i < nicknames.length; i++) {
            dlm.addElement(nicknames[i]);
        }
        if (firstTime) {
            fieldNickname.setText(dlm.getElementAt(dlm.size() - 1));
            firstTime = false;
        }
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMessage(new Message(ClientConstants.CONNECTION_E + e));
    }

    private synchronized void printMessage(Message msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg.getData() + ENDL);
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    public static final String ENDL = "\n";
}
