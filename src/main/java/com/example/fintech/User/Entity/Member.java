package com.example.fintech.User.Entity;

import com.example.fintech.User.Dto.MemberDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long seq;

    @Column
    private String userId;

    @Column
    private String userName;

    @Column
    private String userMobile;

    @Column
    private Long userSeqNo;

    public MemberDto toDto() {
        return MemberDto.builder()
                .userName(userName)
                .userMobile(userMobile)
                .userSeqNo(userSeqNo)
                .build();


    }
}
