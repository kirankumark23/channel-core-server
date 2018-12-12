/*	xBranch		*/
INSERT INTO xbranch (xcompany,xidkey,xname,xversion,xadded,xaddedby,xlastupdate) VALUES 
('COMP01','BRN001','Mumbai',0,'2018-12-12 14:17:45.173','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:17:45.173')
,('COMP01','BRN002','Bangalore',0,'2018-12-12 14:17:53.701','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:17:53.701')
;
/*	xMenu		*/
INSERT INTO xmenu (xcompany,xidkey,xseqno,xmainmenu,xsubmenu,xdescription,xurl,xversion,xadded,xaddedby,xlastupdate) VALUES 
('COMP01','1bc601c4-274e-432c-b623-bf92816aa10e',1,'Request','Balance Enquiry','Get balance','URL',0,'2018-12-12 14:25:21.688','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:25:21.688')
,('COMP01','1bc601c5-274e-432c-b623-bf92816aa10e',2,'Request','Fund Transfer','Transfer Funds','URL',0,'2018-12-12 14:25:47.088','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:25:47.088')
,('COMP01','1bc601c6-274e-432c-b623-bf92816aa10e',3,'History','Requests','Request History','URL',0,'2018-12-12 14:25:47.088','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:25:47.088')
;
/*	xRole		*/
INSERT INTO xrole (xcompany,xidkey,xname,xchannel,xversion,xadded,xaddedby,xlastupdate) VALUES 
('COMP01','8f48f22e-67ea-4053-9da9-b379ecced6eb','STAFF',false,0,'2018-12-06 15:38:40.913','464d5a07-5ee9-429b-852d-6a0e47bdc286',NULL)
,('COMP01','0f8bbd0e-4537-4279-bfe8-ae4a52d4bb94','MB',true,0,'2018-12-06 15:38:40.913','464d5a07-5ee9-429b-852d-6a0e47bdc286',NULL)
,('COMP01','c72f3b7e-bc27-467a-aadc-76b2b540bf7a','IB',true,0,'2018-12-06 15:38:40.913','464d5a07-5ee9-429b-852d-6a0e47bdc286',NULL)
;

/*	xRoleMenu	*/
INSERT INTO xrolemenu (xcompany,xidkey,xrole,xmenu,xversion,xadded,xaddedby,xlastupdate) VALUES 
('COMP01','8f48f22e-67ea-4053-9da9-b379ecced6eb','8f48f22e-67ea-4053-9da9-b379ecced6eb','1bc601c4-274e-432c-b623-bf92816aa10e',0,'2018-12-12 14:32:37.542','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:32:37.542')
,('COMP01','7fb52f8f-1d7e-4bc3-9faf-2660255ffc20','8f48f22e-67ea-4053-9da9-b379ecced6eb','1bc601c5-274e-432c-b623-bf92816aa10e',0,'2018-12-12 14:32:37.542','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:32:37.542')
,('COMP01','7fb52f1f-1d7e-4bc3-9faf-2660255ffc20','8f48f22e-67ea-4053-9da9-b379ecced6eb','1bc601c6-274e-432c-b623-bf92816aa10e',0,'2018-12-12 14:32:37.542','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-12 14:32:37.542')
;

/*	xCBSIntegration */
insert into xCBSIntegration values ('fee3aa47-3e60-420e-9025-23600acef3d4', true);

/*	xUser */
INSERT INTO xuser (xidkey,xemailaddress,xfailloginattempts,xfirstname,xlastname,xlockuserindicator,xmobilenumber,xpasswordhash,xversion,xadded,xaddedby,xlastupdate) VALUES 
('464d5a07-5ee9-429b-852d-6a0e47bdc286','staff@company.com',0,'Staff','Staff',false,'9999999999','$2a$10$8zeuMun9s1Gb7oNcbL6gaulwRDVkFKAFpSu3AGyXrubH0oonR.aZK',0,'2018-11-08 15:51:56.542',NULL,'2018-11-08 15:51:56.542')
,('464d5a08-5ee9-429b-852d-6a0e47bdc286','channel1@company.com',0,'Channel','One',false,'9999999999','$2a$10$8zeuMun9s1Gb7oNcbL6gaulwRDVkFKAFpSu3AGyXrubH0oonR.aZK',0,'2018-11-08 15:51:56.542',NULL,'2018-11-08 15:51:56.542')
,('464d5a09-5ee9-429b-852d-6a0e47bdc286','channel2@company.com',0,'Channel','Two',false,'9999999999','$2a$10$8zeuMun9s1Gb7oNcbL6gaulwRDVkFKAFpSu3AGyXrubH0oonR.aZK',0,'2018-12-10 12:31:59.944',NULL,'2018-12-10 12:31:59.944')
;

/*	xUserRole */
INSERT INTO xuserrole (xidkey,xuser,xcompany,xbranch,xrole,xversion,xadded,xaddedby,xlastupdate) VALUES 
('94760628-8bdc-48d6-9966-fef7633f8360','464d5a07-5ee9-429b-852d-6a0e47bdc286','COMP01','BRN001','8f48f22e-67ea-4053-9da9-b379ecced6eb',0,'2018-12-09 19:52:17.497','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-09 19:52:17.497')
,('94760630-8bdc-48d6-9966-fef7633f8360','464d5a08-5ee9-429b-852d-6a0e47bdc286','COMP01','BRN001','0f8bbd0e-4537-4279-bfe8-ae4a52d4bb94',0,'2018-12-10 12:35:52.237','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-10 12:35:52.237')
,('94760629-8bdc-48d6-9966-fef7633f8360','464d5a09-5ee9-429b-852d-6a0e47bdc286','COMP01','BRN001','c72f3b7e-bc27-467a-aadc-76b2b540bf7a',0,'2018-12-09 22:05:23.503','464d5a07-5ee9-429b-852d-6a0e47bdc286','2018-12-09 22:05:23.503')
;

/*	xcustomer */
INSERT INTO xcustomer (xidkey,xnumber,xfname,xlname,xversion,xadded,xaddedby,xlastupdate,xemail) VALUES 
('aaa4b88a-9c61-4c0a-bba0-8a778f81a90d',1000,'PP','',0,NULL,NULL,'2018-12-06 15:15:28.050','prashant.mp@appliedsni.com')
,('1fc6f346-e9da-4e49-84f0-d3c2d4018167',2000,'KG','',0,NULL,NULL,'2018-12-06 15:16:26.808','kg@appliedsni.com')
,('eb5bb7fa-9ed2-49c8-af9d-4e9a60d75778',3000,'SK','',0,NULL,NULL,'2018-12-06 15:16:26.808','sk@appliedsni.com')
;

/*	xProductRoleAccess */
insert into xProductRoleAccess values ('de3ec821-aaab-42e1-9701-55f5bf4f0aec', '529aef14-bd0d-4e70-8066-441301ab7f1f', '8f48f22e-67ea-4053-9da9-b379ecced6eb', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);
insert into xProductRoleAccess values ('918abca6-1130-4ab4-81f2-aa71eee5f754', 'dedbe268-b660-4cfe-a883-5e8aa4d55355', '8f48f22e-67ea-4053-9da9-b379ecced6eb', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);
insert into xProductRoleAccess values ('f83c56ed-8b1c-4207-9110-ee1863879e27', '529aef14-bd0d-4e70-8066-441301ab7f1f', '0cd6f4d5-c40d-44c3-94f5-bfdbca80afa7', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);
insert into xProductRoleAccess values ('35e4d5f4-2420-4162-a88c-0231f0f2090e', '529aef14-bd0d-4e70-8066-441301ab7f1f', '0f8bbd0e-4537-4279-bfe8-ae4a52d4bb94', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);
insert into xProductRoleAccess values ('03bfd668-3c81-4dd2-a953-49fb20e072ae', '529aef14-bd0d-4e70-8066-441301ab7f1f', 'c72f3b7e-bc27-467a-aadc-76b2b540bf7a', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);

/*	xCustomerMandate */
insert into xCustomerMandate values ('d601640e-fa25-4af7-9d0a-a928d38658e7', 'aaa4b88a-9c61-4c0a-bba0-8a778f81a90d', 'd8dfaa22-1be2-4819-9da6-f187559e0ec2', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);

/*	xCustomerMandateService */
insert into xCustomerMandateService values ('bff45b5b-44c4-4580-a39b-117794f77645', 'd601640e-fa25-4af7-9d0a-a928d38658e7', '8f48f22e-67ea-4053-9da9-b379ecced6eb', '529aef14-bd0d-4e70-8066-441301ab7f1f', 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286', current_timestamp);





