package org.example.agroptima.Modele.Member;

import java.time.LocalDate;
import java.util.List;

public class Member extends MemberInformation {
    private String id;
    private List<Member> referees;

    public Member(String id, List<Member> referees) {
        this.id = id;
        this.referees = referees;
    }


    public Member() {
        super();
    }

    public String getId() {
        return id;
    }

    public List<Member> getReferees() {
        return referees;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReferees(List<Member> referees) {
        this.referees = referees;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", referees=" + referees +
                '}';
    }
}