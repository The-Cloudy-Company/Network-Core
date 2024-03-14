package com.thecloudyco.cc.membership;

public enum BlockType {
    ADMINISTRATIVE("Administrative Block", true, true),
    NO_CHECKS("No Checks", true, false),
    NSF_BLOCK("NSF Block", true, true),
    REFUND_OWED("Refund Owed Block", true, true),
    EXECUTIVE_INVITATION("Executive Invitation Block", true, false),
    VERIFY_ADDRESS("Verify Address", true, false);

    private String desc;
    // Whether to display the block on POS
    private boolean show;
    // Whether to require a manager's override to bypass the block
    private boolean managers_override;

    BlockType(String desc, boolean show, boolean managers_override) {
        this.desc = desc;
        this.show = show;
        this.managers_override = managers_override;
    }

    public String getDesc() {
        return desc;
    }
    public boolean doWeDisplay() {
        return show;
    }
    public boolean requireManagersOverride() {
        return managers_override;
    }
}
