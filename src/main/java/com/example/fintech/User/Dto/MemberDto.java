package com.example.fintech.User.Dto;

import com.example.fintech.User.Entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

    private String userId;
    private String userName;
    private String userMobile;
    private Long userSeqNo;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .userName(userName)
                .userMobile(userMobile)
                .userSeqNo(userSeqNo)
                .build();
    }
}
