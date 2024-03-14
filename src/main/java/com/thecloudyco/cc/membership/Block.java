package com.thecloudyco.cc.membership;

public class Block {

    private String added_by;
    private boolean active;
    private String activation_message;
    private String inactivation_message; // Only used if the block is inactivated, since blocks remain on accounts forever
    private String removed_by;
    private long timestamp_added;
    private long timestamp_removed;

    private BlockType type;

    public Block(String added_by, boolean active, String activation_message, long timestamp_added) {
        this.added_by = added_by;
        this.active = active;
        this.activation_message = activation_message;
        this.timestamp_added = timestamp_added;
    }

    public String getAddedBy() {
        return added_by;
    }
    public boolean isActive() {
        return active;
    }
    public String getActivationMessage() {
        return activation_message;
    }
    public String getInactivationMessage() {
        return inactivation_message;
    }
    public String getRemovedBy() {
        return removed_by;
    }
    public void setInactivationMessage(String f) {
        this.inactivation_message = f;
    }
    public void setRemovedBy(String f) {
        this.removed_by = f;
    }
    public void setTimestampRemoved(long f) {
        this.timestamp_removed = f;
    }
    public long getTimestampRemoved() {
        return timestamp_removed;
    }
    public BlockType getType() {
        return type;
    }
    public long getTimestampAdded() {
        return timestamp_added;
    }
}
