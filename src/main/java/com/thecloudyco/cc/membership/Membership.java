package com.thecloudyco.cc.membership;

public class Membership {

    String mbr_number;
    String first_name;
    String last_name;
    String address;
    String address_two;
    String city;
    String state;
    String zip_code;
    String phone_number;
    String email;
    boolean is_employee;
    int points_balance;
    String parent_mbrshp;
    MembershipType type;
    long expiration;
    long enrollment_date;
    long member_since;
    String added_by;
    String minecraft;

    public Membership(String mbr_number, String first_name, String last_name, String address, String address_two, String city, String state,
                      String zip_code, String phone_number, String email, boolean is_employee, int points_balance, String parent_mbrshp,
                      MembershipType type, long expiration, long enrollment_date, String added_by, long member_since, String minecraft) {

        this.mbr_number = mbr_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_number = phone_number;
        this.is_employee = is_employee;
        this.points_balance = points_balance;
        this.parent_mbrshp = parent_mbrshp;
        this.type = type;
        this.expiration = expiration;
        this.enrollment_date = enrollment_date;
        this.added_by = added_by;
        this.member_since = member_since;

        this.address_two = address_two;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;

        this.email = email;
        this.minecraft = minecraft;
    }

    public String getFullName() {
        return first_name.toUpperCase() + " " + last_name.toUpperCase();
    }

    public String getMembershipNumber() {
        return mbr_number;
    }

    public String getFirstName() {
        return first_name.toUpperCase();
    }

    public String getLastName() {
        return last_name.toUpperCase();
    }

    // Old Method, this one just uses address line 1
//    public String getAddress() {
//        return address.toUpperCase();
//    }

    // New getAddress(), uses ALL of the address lines all together
    public String getAddress() {
        return address.toUpperCase() + "  " + getAddressTwo().toUpperCase() + "  " + getCity().toUpperCase() + "  " + getState().toUpperCase() + "  " + getZipCode();
    }

    public String getAddressOne() {
        return address;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public boolean isEmployee() {
        return is_employee;
    }

    public int getPointsBalance() {
        return points_balance;
    }

    public String getParentMembership() {
        return parent_mbrshp;
    }

    public MembershipType getType() {
        return type;
    }
    public long getEnrollmentDate() {
        return enrollment_date;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getAddedBy() {
        return added_by.toUpperCase();
    }

    public long getMemberSince() { return member_since; }
    public String getAddressTwo() {
        if(address_two == null) {
            return "";
        }
        return address_two;
    }
    public String getCity() {
        if(city == null) {
            return "";
        }
        return city;
    }
    public String getState() {
        if(state == null) {
            return "";
        }
        return state;
    }
    public String getZipCode() {
        if(zip_code == null) {
            return "";
        }
        return zip_code;
    }
    public String getEmail() {
        if(email == null) {
            return "";
        }
        return email;
    }
    public String getMinecraftUsername() {
        return minecraft;
    }
}
