package com.thecloudyco.cc.membership;

import com.thecloudyco.cc.database.CloverDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MembershipUtil {
    public static String generateID(int length) {
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            builder.append(r.nextInt(10));
        }
        return builder.toString();
    }

    public static boolean isEmployee(Membership member) {
        return member.isEmployee();
    }

    public static boolean isInternalMembership(Membership member) {
        return member.getType() == MembershipType.INTERNAL_REGULAR || member.getType() == MembershipType.INTERNAL_PREMIUM;
    }

    public static boolean isExpired(Membership member) {
        // Flags? Check if the membership is expired or revoked
        if(member.getExpiration() < System.currentTimeMillis()) {
            if(member.getType() != MembershipType.INTERNAL_REGULAR || member.getType() != MembershipType.INTERNAL_PREMIUM
                    // Employee memberships do not expire
                    // Complimentary Memberships do not expire
                    || member.getType() != MembershipType.EMPLOYEE || member.getType() != MembershipType.EMPLOYEE_PREMIUM
            || member.getType() != MembershipType.COMPLIMENTARY_REGULAR || member.getType() != MembershipType.COMPLIMENTARY_PREMIUM) {
                return true;
            }
        }
        return false;
    }

    public static Membership findMembership(String member_number) throws SQLException {
        Membership member = null;
        // Find the membership
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `mbr_number` = '" + member_number + "';");
        while(result.next()) {
            member = new Membership(member_number, result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft"));
        }
        return member;
    }

    /**
     * Find a membership from the given player's UUID
     * @param uuid
     * @return
     */
    public static Membership findMembership(UUID uuid) throws SQLException {
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `minecraft` = '" + uuid + "';");
        Membership membership = null;
        while (result.next()) {
            membership = new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft"));
        }
        return membership;
    }

    /***
     * Check to see if a membership is premium status through the membership system
     * Employee Memberships are automatically upgraded to premium
     * @param member
     * @return
     */
    public static boolean isExecutive(Membership member) {
        if(member.getType() == MembershipType.PREMIUM
                // Employee memberships do not expire
                // Complimentary Memberships do not expire
                || member.getType() == MembershipType.EMPLOYEE || member.getType() == MembershipType.EMPLOYEE_PREMIUM
                || member.getType() == MembershipType.COMPLIMENTARY_PREMIUM) {
            return true;
        }
        return false;
    }

    public static boolean isUpForRenewal(Membership membership) {
        long tenDaysInMillis = 10 * 24 * 60 * 60 * 1000; // WE ALWAYS ASSUME IF IT EXPIRES WITHIN 10 DAYS ITS "UP FOR RENEWAL"!!!
        if(membership.getExpiration() - System.currentTimeMillis() <= tenDaysInMillis) {
            return true;
        }
        return false;
    }

    public static boolean isRevoked(Membership member) {
        return member.getType() == MembershipType.REVOKED;
    }

    public static List<Membership> searchMembershipsByFullName(String first, String last) throws SQLException {
        Membership member = null;
        List<Membership> membersList = new ArrayList<>();
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `first_name` LIKE '%" + first + "%' AND `last_name` LIKE '%" + last + "%';");
        while(result.next()) {
            membersList.add(new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft")));
        }
        return membersList;
    }

    public static List<Membership> searchMembershipsByAddress(String address) throws SQLException {
        Membership member = null;
        List<Membership> membersList = new ArrayList<>();
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `address` LIKE '%" + address + "%';");
        while(result.next()) {
            membersList.add(new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft")));
        }
        return membersList;
    }
    public static List<Membership> searchMembershipsByPhone(String phone) throws SQLException {
        Membership member = null;
        List<Membership> membersList = new ArrayList<>();
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `phone_number` LIKE '%" + phone + "%';");
        while(result.next()) {
            membersList.add(new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft")));
        }
        return membersList;
    }
    public static List<Membership> searchMembershipsByParent(String parent) throws SQLException {
        Membership member = null;
        List<Membership> membersList = new ArrayList<>();
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `parent_mbrshp` LIKE '%" + parent + "%';");
        while(result.next()) {
            membersList.add(new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft")));
        }
        return membersList;
    }
    public static List<Membership> searchMembershipsByAdder(String added_by) throws SQLException {
        Membership member = null;
        List<Membership> membersList = new ArrayList<>();
        ResultSet result = CloverDatabase.query("SELECT * FROM `membership` WHERE `added_by` LIKE '%" + added_by + "%';");
        while(result.next()) {
            membersList.add(new Membership(result.getString("mbr_number"), result.getString("first_name"), result.getString("last_name"), result.getString("address"),
                    result.getString("address_two"), result.getString("city"), result.getString("state"), result.getString("zip_code"),
                    result.getString("phone_number"), result.getString("email"), result.getBoolean("is_employee"), result.getInt("points_balance"),
                    result.getString("parent_mbrshp"), MembershipType.valueOf(result.getString("type")), result.getLong("expiration"),
                    result.getLong("enrollment_date"), result.getString("added_by"), result.getLong("member_since"), result.getString("minecraft")));
        }
        return membersList;
    }

}
