package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String name);

    Page<Member> findByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    //다대일에서 '다'의 total count를 조회할 때는 left outer join할 필요가 없다.
    @Query(value = "select m from Member m left join m.team t",
    countQuery = "select count(m.id) from Member m")
    Page<Member> findOptByAge(int age, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    //jpql fetch join을 편리하게 이용
    @EntityGraph(attributePaths = {"team"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findFetchByUsername(String username);

    //변경 감지 안함
    // 영속선 컨텍스트에 스냅샷 안찍음
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly",value = "true"))
    Optional<Member> findReadOnlyByUsername(String username);



}
