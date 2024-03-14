package com.thecloudyco.cc.membership;

public enum MembershipType {

    REGULAR("Regular"),
    PREMIUM("Premium"),
    
    REVOKED("Membership Revoked"),
    EMPLOYEE("Employee"),
    EMPLOYEE_PREMIUM("Employee Premium"),
    INTERNAL_REGULAR("Internal Regular"),
    COMPLIMENTARY_REGULAR("Complimentary Regular"),
    COMPLIMENTARY_PREMIUM("Complimentary Premium"),
    INTERNAL_PREMIUM("Internal Premium");

    String name;

    MembershipType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
