CREATE USER 'TDuser'@'localhost' IDENTIFIED BY 'TravelDreamAgent';

CREATE SCHEMA TravelDream;

GRANT ALL ON TravelDream.* TO 'TDuser'@'localhost';