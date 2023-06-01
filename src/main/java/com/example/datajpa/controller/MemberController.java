package com.example.datajpa.controller;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user"+i, i));
        }
    }

    @GetMapping("/members")
    public Page<MemberDto> getMembers(
            //page, size, sort=id,desc query parameter
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {
        return memberRepository.findAll(pageable)
                .map(member -> MemberDto.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .age(member.getAge())
                        .build());

    }
}
