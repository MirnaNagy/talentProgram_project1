
-- Author: WebSphere Education

CREATE SCHEMA USERSDB;

-- Create and populate the tables used in the AUTHDB database

CREATE TABLE USERSDB.USER (
                              ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                              USERNAME VARCHAR(60) NOT NULL,
                              PASSWORD VARCHAR(128) NOT NULL,
                              EMAIL CHAR(40),
                              ROLE varchar(64),
                              DEL BOOLEAN DEFAULT 0
);


INSERT INTO USERSDB.USER (ID, USERNAME, EMAIL, PASSWORD, ROLE) VALUES (1, 'Ahmed', 'ahmed@gmail.com', 'ahmed', 'admin');
INSERT INTO USERSDB.USER (ID, USERNAME, EMAIL, PASSWORD, ROLE) VALUES (2, 'Amr', 'amr@gmail.com', 'amr', 'user');

CREATE TABLE USERSDB._GROUP (
                                ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                GROUPNAME VARCHAR(60) NOT NULL
);

INSERT INTO USERSDB._GROUP (ID, GROUPNAME) VALUES (1, 'Group1');


CREATE TABLE USERSDB.GROUP_USERS(
                                    USER_ID BIGINT NOT NULL,
                                    GROUP_ID BIGINT NOT NULL,
                                    FOREIGN KEY (USER_ID)  REFERENCES USERSDB.USER(ID),
                                    FOREIGN KEY (GROUP_ID) REFERENCES USERSDB._GROUP(ID),
                                    PRIMARY KEY (USER_ID, GROUP_ID)
);

INSERT INTO USERSDB.GROUP_USERS(USER_ID, GROUP_ID) VALUES (1,1);





