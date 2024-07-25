/* MySQL code to insert values to tables created in PracTest 3*/
-- Insert values to Faculty table
INSERT INTO Faculty VALUES
('A01','Faculty of Science and Engineering');
INSERT INTO Faculty VALUES
('A02','Faculty of Humanities');
INSERT INTO Faculty VALUES
('A03','Faculty of Health Sceiences');
INSERT INTO Faculty VALUES
('A04','Faculty of Management');
INSERT INTO Faculty VALUES
('A05','Faculty of Law');

-- Insert values to Student table
INSERT INTO Student VALUES
('000010','Christine','Haas','A01','2020-02-10',6);  
INSERT INTO Student VALUES
('000011','John','Geyer','A02','2021-02-10',2);
INSERT INTO Student VALUES
('000012','Eva','Pulaski','A01','2019-02-10',6); 
INSERT INTO Student VALUES
('000013','Vincenzo','Lucchesi','A03','2018-02-10',9); 
INSERT INTO Student VALUES
('000014','Heather','Nicholls','A01','2019-02-10',4); 
INSERT INTO Student VALUES
('000015','Bruce','Adamson','A01','2020-02-10',3); 
INSERT INTO Student VALUES
('000016','Aati','Awang','A02','2020-02-10',1); 
INSERT INTO Student VALUES
('000017','Ruchira','Patel','A02','2020-02-10',2); 
INSERT INTO Student VALUES
('000018','Adrian','Silva','A03','2017-02-10',4); 
INSERT INTO Student VALUES
('000019','Diya','Chauhan','A05','2018-02-10',7); 
INSERT INTO Student VALUES
('000020','Apurva','Banerjee','A03','2020-02-10',4); 
INSERT INTO Student VALUES
('000021','Sally','Kwan','A02','2016-02-10',7); 
INSERT INTO Student VALUES
('000022','Masatoshi','Yoshimura','A02','2018-02-10',5); 
INSERT INTO Student VALUES
('000023','David','Brown','A01','2020-02-10',2); 
INSERT INTO Student VALUES
('000024','Salvatore','Marino','A01','2016-02-10',5); 
INSERT INTO Student VALUES
('000025','Ethel','Schneider','A01','2016-02-10',8); 
INSERT INTO Student VALUES
('000026','Daniel','Smith','A05','2020-02-10',8); 
INSERT INTO Student VALUES
('000027','John','Parker','A02','2019-02-10',8); 
INSERT INTO Student VALUES
('000028','Wing','Lee','A03','2020-02-10',8); 
INSERT INTO Student VALUES
('000029','Irving','Stern','A01','2020-02-10',8); 

-- Insert values to Activity table
INSERT INTO Activity VALUES
('CO3100','Science for kids','A01','000012',10000,NULL,NULL);
INSERT INTO Activity VALUES
('CO3101','Daffodil day 2020','A02','000021',500,100,1);
INSERT INTO Activity VALUES
('CO3102','Sci Fic with Schools','A01','000014',5000,1000,6);
INSERT INTO Activity VALUES
('CO3103','Games Night -Sem1 ','A01','000015',500,NULL,3);
INSERT INTO Activity VALUES
('CO3104','Daffodil day 2021','A02','000016',600,NULL,1);
INSERT INTO Activity VALUES
('CO3105','Tax Clinic','A05','000019',1200,NULL,3); 
INSERT INTO Activity VALUES
('CO3106','Eating Healthy', 'A03','000018',8000,200,3);  

-- Insert values to ComWork table 
INSERT INTO CommWork VALUES('000012','CO3100',3);
INSERT INTO CommWork VALUES('000014','CO3102',1);
INSERT INTO CommWork VALUES('000015','CO3103',1);
INSERT INTO CommWork VALUES('000016','CO3104',2);
INSERT INTO CommWork VALUES('000019','CO3105',1);
INSERT INTO CommWork VALUES('000018','CO3106',3);
INSERT INTO CommWork VALUES('000012','CO3101',2);
INSERT INTO CommWork VALUES('000015','CO3102',1);
INSERT INTO CommWork VALUES('000015','CO3100',1);
INSERT INTO CommWork VALUES('000012','CO3104',1);
INSERT INTO CommWork VALUES('000023','CO3106',3);
INSERT INTO CommWork VALUES('000026','CO3105',2);
INSERT INTO CommWork VALUES('000027','CO3102',1);
INSERT INTO CommWork VALUES('000025','CO3100',1);
INSERT INTO CommWork VALUES('000028','CO3106',2); 
INSERT INTO CommWork VALUES('000021','CO3101',3);
                                          
