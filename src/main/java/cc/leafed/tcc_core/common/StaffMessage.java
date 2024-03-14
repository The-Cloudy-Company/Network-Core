package cc.leafed.tcc_core.common;

public class StaffMessage {

    /**
     * StaffMessage is how the network arranges any messages sent to staff members on the network
     * @Author: Atticus Zambrana
     */

    // The player (or network miniplugin) who is sending the message
    private String sender;
    // The server that this message is coming from
    private String server;
    // The contents of the message
    private String message;

    public StaffMessage(String sender, String server, String message) {
        this.sender = sender;
        this.message = message;
        this.server = server;
    }

    public String getSender() {
        return sender;
    }
    public String getMessage() {
        return message;
    }
    public String getServer() {
        return server;
    }
}
