package com.jag.ExpenseTracker.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCustom {

    private String menssage;
    private int statusCode;
}
