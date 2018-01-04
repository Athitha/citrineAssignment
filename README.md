
#Prerequsites 
1. Install Java environment 
2. Install and setup MySQl work bench
3. Create a schema named ChemicalData

IDE - IntelliJ

#How to Run  
1. git clone the application.
2. In Intellij go to preferences--> navigate to annotation prosessors ->enable annotation processing
3. Change the credentials of database in `application.properties`
4. Run the application until the server is up.
5. Search based on the chemical formula name or color or bandgap value
   For single parameter based search Navigate to http://localhost:8080/searchbyone?searchTerm==1.05
    **Note:** If search is based on the Band Gap Value specify with the operator like searchTerm=**=2.3** (equal to),
    searchTerm=**>2.3** (greater than), searchTerm=**<2.3** (less than).
   WildCard: Only for chemical formula and color
   http://localhost:8080/searchbyone?searchTerm=bl
   (It is best practice to give atleast 3 char of color or chemical formula names )
6. For Multi parameter based search Navigate 
       to http://localhost:8080/searchbytwo?bandgap=>2.5&color=Yellow
       **Note:** If search is based on the Band Gap Value specify with the operator like bandgap=**=2.3** (equal to),
       bandgap=**>2.3** (greater than), bandgap=**<2.3** (less than).
       Give the parameters with & symbol without spaces (like color=ye&bandgap=<2.9)
       other example:
       http://localhost:8080/searchbytwo?chemicalFormula=Ga2S3&bandgap==2.85
       http://localhost:8080/searchbytwo?color=orange-red&bandgap=<2.9
       http://localhost:8080/searchbytwo?chemicalFormula=hg1O1&color=red
       http://localhost:8080/searchbytwo?color=yellow&chemicalFormula=Hg1I2&bandgap=<5.5
       Wildcard: for chemicalformula and color matches that start with the letter or sequence 
       (example if given color=ye ; it will match and return all the data where color value starts with ye; it matches yellow, yellow-green, yellow-red and likewise)
       http://localhost:8080/searchbytwo?chemicalFormula=a&color=ye&bandgap=>2.2
       http://localhost:8080/searchbytwo?chemicalFormula=A&color=O
       http://localhost:8080/searchbytwo?chemicalFormula=A&bandgap=>3.3
       http://localhost:8080/searchbytwo?color=A&bandgap=>3.3
    
#Project Details
1. The CSV file is added to the `src/main/resources/data.csv` with out the header row,
    the csv has the header Chemical formula, Property 1 name, Property 1 value, Property 2 name, Property 2 value.
2. write a SQL script to create a table to store the data.  
   `CREATE TABLE Data  (
        entry_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
        chemical_formula VARCHAR(20),
        property1_name VARCHAR(20),
        property1_value VARCHAR(20),
        property2_name VARCHAR(20),
        property2_value VARCHAR(20)
    )ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;`
3. The class `Entry` defines the model, i have used Lombok java library that take care of getters and setters.
4. The class `BatchConfiguration` handles the job which reads CSV file and write into mysql database.
5. Default Port used is 8080 for the application and for SQL it is 3306
6. The dependencies and the plugin used in the project are at `build.gradle`
7. The class `EntryController` has the two REST end points:
    /searchbyone - used for filtering based on a single parameter
    /searchbytwo- used for filtering based on a two or more parameters.
    if only single parameter or no parameter is given it returns the whole data.
    `CitrineassignmentApplication` runs the entire project.
    


It would be easy for the end user to be provided with an UI to enter and filter the search.
