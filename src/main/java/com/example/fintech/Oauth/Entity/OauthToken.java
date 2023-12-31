package com.example.fintech.Oauth.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long seq;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String userMobile;

    @Column(columnDefinition = "TEXT", length = 400)
    private String accessToken;

    @Column
    private String tokenType;

    @Column
    private String expiresIn;

    @Column(columnDefinition = "TEXT", length = 400)
    private String refreshToken;

    @Column
    private String scope;

    @Column
    private Long userSeqNo;

    @Column
    private LocalDateTime regDate;
}
