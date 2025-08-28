package com.disuraaberathna.globemed.model.service.composite;

import com.disuraaberathna.globemed.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class StaffGroup implements StaffMember{
    private final String name;
    private final List<StaffMember> members;

    public StaffGroup(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(StaffMember member){
        members.add(member);
    }

    public void removeMember(StaffMember member){
        members.remove(member);
    }

    public void removeMember(User user){
        members.removeIf(member-> member instanceof User && ((User)member).getId().equals(user.getId()));
    }

    @Override
    public String showDetails() {
        //
        System.out.println("Group: " + name);
        for (StaffMember member : members) {
            System.out.print("  ");
            member.showDetails();
        }

        return "";
    }

    @Override
    public String getName() {
        return name;
    }

    public List<StaffMember> getMembers() {
        return new ArrayList<>(members);
    }

    public int getMemberCount() {
        return members.size();
    }
}
