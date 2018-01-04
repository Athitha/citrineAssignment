DROP TABLE IF EXISTS Data;

CREATE TABLE Data  (
    entry_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    chemical_formula VARCHAR(20),
    property1_name VARCHAR(20),
    property1_value VARCHAR(20),
    property2_name VARCHAR(20),
    property2_value VARCHAR(20)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;