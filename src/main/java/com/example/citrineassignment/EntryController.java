package com.example.citrineassignment;


import com.example.citrineassignment.csvtomysql.model.Entry;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.hibernate.hql.internal.antlr.SqlTokenTypes.LIKE;

@Controller
public class EntryController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/searchbyone")
    public
    @ResponseBody
    HashMap<Integer, Entry> searchItem(@RequestParam String searchTerm) {
        String query1 = "SELECT * from Data " + "where chemical_Formula LIKE" + '\'' + searchTerm+"%" + '\'' + " OR property2_Value LIKE "+ '\'' + searchTerm+"%" + '\'' ;

        String result = searchTerm.replaceAll("[><>=<==]","");
        System.out.println(result);
        if (searchTerm.contains(">")){
            query1 += " OR property1_Value > "+ '\'' + result + '\'';
            System.out.println(query1);
            return this.queryDatabase(query1);
        }
        else if (searchTerm.contains(">=")){
            query1 +=  " OR property1_Value >= "+ '\'' + result + '\'';
            System.out.print(query1);
            return this.queryDatabase(query1);
        }
        else if (searchTerm.contains("<")){
            query1 +=  " OR property1_Value < "+ '\'' + result + '\'';
            System.out.print(query1);
            return this.queryDatabase(query1);
        }
        else if (searchTerm.contains("=")){
            query1 += " OR property1_Value LIKE "+ '\'' + result + '\'';
            System.out.print(query1);
            return this.queryDatabase(query1);
        }
        else if (searchTerm.contains("<=")){
            query1 +=" OR property1_Value <= "+ '\'' + result + '\'';
            System.out.print(query1);
            return this.queryDatabase(query1);
        }



        System.out.print(query1);
        return this.queryDatabase(query1);
    }

    @RequestMapping(value = "/searchbytwo")
    public
    @ResponseBody
    HashMap<Integer, Entry> searchItems(@RequestParam(value = "bandgap", required = false) String bandgap,
                                        @RequestParam(value = "chemicalFormula", required = false) String chemicalFormula,
                                        @RequestParam(value = "color", required = false) String color ) {
        String query1 = "SELECT * from Data ";
        String result="";
        if(bandgap!=null) {
             result = bandgap.replaceAll("[><>=<==]", "");
            //System.out.println(result);
        }
        //System.out.println(chemicalFormula);
        //System.out.println(color);

        if (bandgap!=null&&chemicalFormula!=null&&color!=null){
            String query2=" AND chemical_Formula LIKE "+ '\'' + chemicalFormula+"%" + '\''+ " AND property2_Value LIKE "+ '\'' + color +"%"+ '\'';

            if (bandgap.contains(">")){
                query1 += " where property1_Value > "+ '\'' + result + '\''+query2;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains(">=")){
                query1 += " where property1_Value >= "+ '\'' + result + '\''+query2;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("<")){
                query1 += " where property1_Value < "+ '\'' + result + '\''+query2;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("<=")){
                query1 += " where property1_Value <= "+ '\'' + result + '\''+query2;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("=")){
                query1 += " where property1_Value LIKE "+ '\'' + result + '\''+query2;
                return this.queryDatabase(query1);
            }

        }


        if (bandgap!=null&&chemicalFormula!=null){

            if (bandgap.contains(">")){
                query1 +=  "where property1_Value >" + '\''
                        + result + '\'' + " AND chemical_Formula LIKE "+ '\'' + chemicalFormula+"%" + '\'' ;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains(">=")){
                query1 +=  "where property1_Value >=" + '\''
                        + result + '\'' + " AND chemical_Formula LIKE "+ '\'' + chemicalFormula +"%"+ '\'';
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("<")){
                query1 +=  "where property1_Value <" + '\''
                        + result + '\''+ " AND chemical_Formula LIKE "+ '\'' + chemicalFormula +"%"+ '\'' ;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("=")){
                query1 += "where property1_Value LIKE" + '\''
                        + result + '\'' + " AND chemical_Formula LIKE "+ '\'' + chemicalFormula +"%"+ '\'';
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("<=")){
                query1 += "where property1_Value <=" + '\''
                        + result + '\'' + " AND chemical_Formula LIKE "+ '\'' + chemicalFormula +"%"+ '\'';
                return this.queryDatabase(query1);
            }

        }



        if (bandgap!=null&&color!=null){

            if (bandgap.contains(">")){
                query1 +=  "where property1_Value >" + '\''
                        + result + '\'' + " AND property2_Value LIKE "+ '\'' + color +"%"+ '\'' ;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains(">=")){
                query1 +=  "where property1_Value >=" + '\''
                        + result + '\'' + " AND property2_Value LIKE "+ '\'' + color +"%"+ '\'';
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("<")){
                query1 +=  "where property1_Value <" + '\''
                        + result + '\''+ " AND property2_Value LIKE "+ '\'' + color+"%" + '\'' ;
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("=")){
                query1 += "where property1_Value LIKE" + '\''
                        + result + '\'' + " AND property2_Value LIKE "+ '\'' + color+"%" + '\'';
                return this.queryDatabase(query1);
            }
            else if (bandgap.contains("<=")){
                query1 += "where property1_Value <=" + '\''
                        + result + '\'' + " AND property2_Value LIKE "+ '\'' + color +"%"+ '\'';
                return this.queryDatabase(query1);
            }

        }

        if (chemicalFormula!=null&&color!=null){
            query1 += "where chemical_Formula LIKE" + '\''
                    + chemicalFormula +"%"+ '\'' + " AND property2_Value LIKE "+ '\'' + color +"%"+ '\'';
            return this.queryDatabase(query1);
        }


        return this.queryDatabase(query1);

    }


    private HashMap<Integer, Entry> queryDatabase(String query) {
        HashMap<Integer, Entry> mapRet = new HashMap<Integer, Entry>();
        jdbcTemplate.query(query, new ResultSetExtractor<Map>() {
            @Override
            public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
                int i = 0;

                while (rs.next()) {

                    mapRet.put(i, new Entry(rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getString(6)));

                    i++;
                }
                return mapRet;
            }
        });
        return mapRet;
    }
}

