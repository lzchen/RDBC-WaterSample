BEGIN TRANSACTION;
CREATE TABLE `water_samples` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`site`	TEXT DEFAULT NULL,
	`chloroform`	REAL DEFAULT NULL,
	`bromoform`	REAL DEFAULT NULL,
	`bromodichloromethane`	REAL DEFAULT NULL,
	`dibromichloromethane`	REAL DEFAULT NULL
);
INSERT INTO `water_samples` (id,site,chloroform,bromoform,bromodichloromethane,dibromichloromethane) VALUES (1,'LA Aquaduct Filteration Plant Effluent',0.00104,0.0,0.00149,0.00275);
INSERT INTO `water_samples` (id,site,chloroform,bromoform,bromodichloromethane,dibromichloromethane) VALUES (2,'North Hollywood Pump Station (well blend)',0.00291,0.00487,0.00547,0.0109);
INSERT INTO `water_samples` (id,site,chloroform,bromoform,bromodichloromethane,dibromichloromethane) VALUES (3,'Jensen Plant Effluent',0.00065,0.00856,0.0013,0.00428);
INSERT INTO `water_samples` (id,site,chloroform,bromoform,bromodichloromethane,dibromichloromethane) VALUES (4,'Weymouth Plant Effluent',0.00971,0.00317,0.00931,0.0116);
INSERT INTO `water_samples` (id,site,chloroform,bromoform,bromodichloromethane,dibromichloromethane) VALUES (5,'Cleaner than soap',0.01,0.002,0.0003,4.0e-05);
INSERT INTO `water_samples` (id,site,chloroform,bromoform,bromodichloromethane,dibromichloromethane) VALUES (6,'Dirtiest Place Ever',0.9,0.09,0.009,0.0009);
CREATE TABLE `factor_weights` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`chloroform_weight`	REAL DEFAULT NULL,
	`bromoform_weight`	REAL DEFAULT NULL,
	`bromodichloromethane_weight`	REAL DEFAULT NULL,
	`dibromichloromethane_weight`	REAL DEFAULT NULL
);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (1,0.8,1.2,1.5,0.7);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (2,1.0,1.0,1.0,1.0);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (3,0.9,1.1,1.3,0.6);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (4,0.0,1.0,1.0,1.7);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (5,0.0,0.0,0.0,0.0);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (6,1.0,1.0,1.0,1.0);
INSERT INTO `factor_weights` (id,chloroform_weight,bromoform_weight,bromodichloromethane_weight,dibromichloromethane_weight) VALUES (7,0.5,0.5,0.5,0.5);
COMMIT;
