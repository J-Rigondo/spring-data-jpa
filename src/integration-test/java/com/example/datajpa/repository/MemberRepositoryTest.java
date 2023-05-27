package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Roles;
import com.example.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    RolesRepository rolesRepository;

    @BeforeEach
    void before(){
        Roles user = Roles.builder().name("USER").build();
        Roles premium = Roles.builder().name("PREMIUM").build();
        Roles silver = Roles.builder().name("SILVER").build();
        rolesRepository.save(user);
        rolesRepository.save(premium);
        rolesRepository.save(silver);

        Team prism = Team.builder().name("PRISM").build();
        teamRepository.save(prism);

        Member jun = new Member("jun", 33, prism, List.of(user,premium,silver));
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

        Member member = memberRepository.findByUsername("jun")
                .orElseThrow(() -> new RuntimeException("not found"));

        member.getRoles().forEach(roles -> System.out.println(roles.getName()));

        member.setRoles(new ArrayList<>());

        System.out.println("=============TEST END================");




        //then
//        Assertions.assertThat(save.getUsername()).isEqualTo(find.getUsername());

    }

    @Test
    public void paging() {
        memberRepository.save(new Member("jun", 20));
        memberRepository.save(new Member("jun1", 20));
        memberRepository.save(new Member("jun2", 20));
        memberRepository.save(new Member("jun3", 20));
        memberRepository.save(new Member("jun4", 20));

        int age = 20;
        int offset = 0;
        int size = 3;

        //Pageble 구현체
        //total count도 저절로 조회함
        PageRequest pageRequest = PageRequest.of(offset, size, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member> members = memberRepository.findByAge(20, pageRequest);


        int totalPages = members.getTotalPages();
        System.out.println(totalPages);

        long totalElements = members.getTotalElements();
        System.out.println(totalElements);

        int number = members.getNumber();
        System.out.println(number);

        List<Member> content = members.getContent();

        content.forEach(member -> System.out.println(member));

    }

    @Test
    public void sliceTest() {
        memberRepository.save(new Member("jun", 20));
        memberRepository.save(new Member("jun1", 20));
        memberRepository.save(new Member("jun2", 20));
        memberRepository.save(new Member("jun3", 20));
        memberRepository.save(new Member("jun4", 20));

        int age = 20;
        int offset = 0;
        int size = 3;

        PageRequest pageRequest = PageRequest.of(offset, size, Sort.by(Sort.Direction.DESC, "username"));
        Slice<Member> members = memberRepository.findSliceByAge(20, pageRequest);

        List<Member> content = members.getContent();

        Page<Member> optByAge = memberRepository.findOptByAge(20, pageRequest);
    }
}
