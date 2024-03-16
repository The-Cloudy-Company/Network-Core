package cc.leafed.tcc_core.common;

public class NetworkBroadcast {
    // The sender of the broadcast, for now, unused but still written into the protocol for later use
    private String sender;
    // The message to be broadcasted to all players
    private String message;

    public NetworkBroadcast(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }
    public String getMessage() {
        return message;
    }
}
