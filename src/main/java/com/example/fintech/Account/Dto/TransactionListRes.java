package com.example.fintech.Account.Dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionListRes {

    String api_tran_id;
    String api_tran_dtm;
    String rsp_code;
    String rsp_message;
    String bank_tran_id;
    String bank_tran_date;
    String bank_code_tran;
    String bank_rsp_code;
    String bank_rsp_message;
    String bank_name;
    String fintech_use_num;
    String balance_amt;
    String page_record_cnt;
    String next_page_yn;
    String befor_inquiry_trace_info;
    List<Transaction> res_list;

}
