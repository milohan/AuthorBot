
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint gives the relative name for the end point This will be
 * accessed via ws://localhost:8080/EchoChamber/echo Where "localhost" is the
 * address of the host, "EchoChamber" is the name of the package and "echo" is
 * the address to access this class from the server
 */
@ServerEndpoint("/echo")
public class EchoServer {

    private TextGenerator tg = new TextGenerator();
    /**
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the method onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println(session.getId() + " has opened a connection");
//        try {
//            session.getBasicRemote().sendText("Connection Established");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            // get author
            // get text
            // get url
            switch(message) {
            case "author":
                session.getBasicRemote().sendText(tg.getAuthor());
                break;
            case "text":
                session.getBasicRemote().sendText(tg.getText());
                break;
            case "url":
                session.getBasicRemote().sendText(tg.getUrl());
                break;
            default:
                break;
            } 
//            if (message.equals("new")) {
//                tg.findNew();
//            }
//            tg.findNew();
//            session.getBasicRemote().sendText(tg.getString());
//            session.getBasicRemote().sendText(message);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");
    }
}
