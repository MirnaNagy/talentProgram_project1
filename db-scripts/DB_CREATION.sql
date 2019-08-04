
-- Author: WebSphere Education
DROP SCHEMA USERSDB;
CREATE SCHEMA USERSDB;

-- Create and populate the tables used in the AUTHDB database

CREATE TABLE USERSDB.USER (
                              USERID BIGINT AUTO_INCREMENT PRIMARY KEY,
                              USERNAME VARCHAR(60) NOT NULL,
                              PASSWORD VARCHAR(128) NOT NULL,
                              EMAIL CHAR(40),
                              ROLE varchar(64),
                              DEL BOOLEAN DEFAULT 0
);


INSERT INTO USERSDB.USER (USERID, USERNAME, EMAIL, PASSWORD, ROLE) VALUES (1, 'Ahmed', 'ahmed@gmail.com', 'ahmed', 'admin');
# INSERT INTO USERSDB.USER (USERID, USERNAME, EMAIL, PASSWORD, ROLE) VALUES (2, 'Amr', 'amr@gmail.com', 'amr', 'User');
 UPDATE USERSDB.USER SET PASSWORD = SHA2(PASSWORD,256);
# SELECT * FROM USERSDB.USER;

CREATE TABLE USERSDB._GROUP (
                                GROUP_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                GROUPNAME VARCHAR(60) NOT NULL
);

INSERT INTO USERSDB._GROUP (GROUP_ID, GROUPNAME) VALUES (1, 'Group1');
INSERT INTO USERSDB._GROUP (GROUP_ID, GROUPNAME) VALUES (2, 'Group2');


CREATE TABLE USERSDB.GROUP_USERS(
                                    USERID BIGINT NOT NULL,
                                    GROUP_ID BIGINT NOT NULL,
                                    FOREIGN KEY (USERID)  REFERENCES USERSDB.USER(USERID),
                                    FOREIGN KEY (GROUP_ID) REFERENCES USERSDB._GROUP(GROUP_ID),
                                    PRIMARY KEY (USERID, GROUP_ID)
);

INSERT INTO USERSDB.GROUP_USERS(USERID, GROUP_ID) VALUES (1,1);
INSERT INTO USERSDB.GROUP_USERS(USERID, GROUP_ID) VALUES (1,2);






