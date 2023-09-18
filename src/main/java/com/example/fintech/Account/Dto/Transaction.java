package com.example.fintech.Account.Dto;

import lombok.Data;

@Data
public class Transaction {


    String tran_date;
    String tran_time;
    String inout_type;
    String tran_type;
    String printed_content;
    String tran_amt;
    String after_balance_amt;
    String branch_name;
}
