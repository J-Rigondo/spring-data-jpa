package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void before(){
        Team prism = Team.builder().name("PRISM").build();
        teamRepository.save(prism);
        Member jun = new Member("jun", 33, prism);
        memberRepository.save(jun);
    }

    @Test
    public void testMember() throws Exception {
        Team team = teamRepository.findByName("PRISM")
                .orElseThrow(() -> new RuntimeException("not found"));
        System.out.println("==============TEST START===============");
        System.out.println(team.getMembers().size());
        team.getMembers().forEach(member->{
            System.out.println(member);
        });
        System.out.println("=============TEST END================");




        //then
//        Assertions.assertThat(save.getUsername()).isEqualTo(find.getUsername());

    }
}
