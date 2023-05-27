package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String name);

    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    //다대일에서 '다'의 total count를 조회할 때는 left outer join할 필요가 없다.
    @Query(value = "select m from Member m left join m.team t",
    countQuery = "select count(m.id) from Member m")
    Page<Member> findOptByAge(int age, Pageable pageable);
}
