package com.example.fintech.Account.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountInfoListDto {

    String api_tran_id;
    String api_tran_dtm;
    String rsp_code;
    String rsp_message;
    String user_name;
    String res_cnt;
    List<AccountInfoDto> res_list;
}
