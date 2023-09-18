package com.example.fintech.User.Repository;

import com.example.fintech.User.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByUserSeqNo(Long userSeqNo);

    Optional<Member> findByUserSeqNo(Long userSeqNo);

    Optional<Member> findByUserId(String userid);



}
