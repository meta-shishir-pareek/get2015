SET SQL_SAFE_UPDATES = 0;
Update members SET addressline2 = "Jaipur";
Update members SET addressline1 = "EPIP Sitapura" WHERE category = "F";
DELETE FROM publishers;
SET @publisher_id = "1" ;
SET @publisher_name="Oxford University Press";
INSERT INTO publishers(publishers_id,publisher_nm) VALUES (@publisher_id,@publisher_name);
SET @publisher_id="12";
SET @publisher_name="O'Rilley Publication";
INSERT INTO publishers(publishers_id,publisher_nm) VALUES (@publisher_id,@publisher_name);
SET @publisher_id="15";
SET @publisher_name="Pearson Publication";
INSERT INTO publishers(publishers_id,publisher_nm) VALUES (@publisher_id,@publisher_name);
SET @publisher_id="20";
SET @publisher_name="Owl India";
INSERT INTO publishers(publishers_id,publisher_nm) VALUES (@publisher_id,@publisher_name);
SET @publisher_id="10";
SET @publisher_name="Diamond Comics";
INSERT INTO publishers(publishers_id,publisher_nm) VALUES (@publisher_id,@publisher_name);
Delete FROM titles where publishers_id = "1";