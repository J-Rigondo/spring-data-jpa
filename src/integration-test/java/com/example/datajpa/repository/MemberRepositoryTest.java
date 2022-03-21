package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = Member.builder()
                .username("jun")
                .build();
        //when
        Member save = memberRepository.save(member);

        Member find = memberRepository.findById(save.getId()).get();

        //then
        Assertions.assertThat(save.getUsername()).isEqualTo(find.getUsername());

    }
}
