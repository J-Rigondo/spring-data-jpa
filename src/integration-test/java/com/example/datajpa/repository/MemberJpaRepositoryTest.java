package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;
    
    @Test
    public void testMember() throws Exception {
        //given
        Member member = Member.builder()
                .username("jun")
                .build();
        //when
        Member save = memberJpaRepository.save(member);

        Member find = memberJpaRepository.find(save.getId());

        //then
        Assertions.assertThat(save.getUsername()).isEqualTo(find.getUsername());

    }
}
