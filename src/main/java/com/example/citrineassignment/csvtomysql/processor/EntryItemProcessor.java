package com.example.citrineassignment.csvtomysql.processor;


import com.example.citrineassignment.csvtomysql.model.Entry;
import com.oracle.tools.packager.Log;
import org.springframework.batch.item.ItemProcessor;
//use this to transform the data before loading to the database (like convert the data to lower case or manipulate the string before loding it to the database)

public class EntryItemProcessor implements ItemProcessor<Entry, Entry>{
    @Override
    public Entry process(Entry entry) throws Exception {
        final String chemicalFormula = entry.getChemicalFormula();
        final String property1Name = entry.getProperty1Name();
        final String property2Name = entry.getProperty2Name();
        final String property1Value = entry.getProperty1Value();
        final String property2Value = entry.getProperty2Value();
        final Entry modifiedEntry = new Entry(chemicalFormula, property1Name, property1Value, property2Name, property2Value);
        //Log.info("Converting (" + entry + ") into (" + modifiedEntry + ")");

        return modifiedEntry;
    }
}