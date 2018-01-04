package com.example.citrineassignment.csvtomysql.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Entry {


    private String chemicalFormula;
    private String property1Name;
    private String property1Value;
    private String property2Name;
    private String property2Value;

}
