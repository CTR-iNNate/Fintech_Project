package com.example.fintech.User.Controller;

import com.example.fintech.Exception.BusinessException;
import com.example.fintech.Response.ResponseService;
import com.example.fintech.Response.SingleResult;
import com.example.fintech.User.Dto.UserInfoAndAccountList;
import com.example.fintech.User.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final MemberService memberService;

    private final ResponseService responseService;

    @GetMapping("/me")
    public SingleResult<UserInfoAndAccountList> getUserInfo(
            @RequestParam("userId") String userId
    ) {
        try {
            return responseService.getSingleResult(memberService.getUserInfo(userId));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

}
