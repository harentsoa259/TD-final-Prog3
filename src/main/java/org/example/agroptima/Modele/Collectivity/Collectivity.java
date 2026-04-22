package org.example.agroptima.Modele.Collectivity;

import org.example.agroptima.Modele.Member.Member;

import java.util.List;

public class Collectivity {
    private String id;
    private String name;
    private String number;
    private String location;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity(){

    }
    public Collectivity(String id, String name, String number, String location, CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.location = location;
        this.structure = structure;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CollectivityStructure structure) {
        this.structure = structure;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Collectivity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", location='" + location + '\'' +
                ", structure=" + structure +
                ", members=" + members +
                '}';
    }


}