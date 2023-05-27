package com.example.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "team_id") name은 외래키 이름, refrenced가 참조 컬럼, 생략하면 Id로 자동 매칭
    private Team team;

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="member_roles",
            joinColumns = {@JoinColumn(name="member_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
    )
    private List<Roles> roles = new ArrayList<>();

    @Builder
    public Member(Long id, String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public Member(String username) {
        this(username, 0);
    }
    public Member(String username, int age) {
        this(username, age, null);
    }
    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
//        this.team = team;
        if (team != null) {
            changeTeam(team);
        }
    }

    public Member(String username, int age, Team team, List<Roles> roles) {
        this.username = username;
        this.age = age;
//        this.team = team;
        if (team != null) {
            changeTeam(team);
        }
        this.roles = roles;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
