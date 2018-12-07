/*	xBranch		*/
insert into xBranch values ('BRN001','Mumbai');
insert into xBranch values ('BRN002','Bangalore');

/*	xMenu		*/
insert into xMenu values ('1bc601c4-274e-432c-b623-bf92816aa10e', 1, 'Request', 'Balance Enquiry', 'Get balance', 'URL');
insert into xMenu values ('1bc601c5-274e-432c-b623-bf92816aa10e', 2, 'Request', 'Fund Transfer', 'Transfer Funds', 'URL');
insert into xMenu values ('1bc601c6-274e-432c-b623-bf92816aa10e', 3, 'History', 'Requests', 'Request History', 'URL');

/*	xRole		*/
insert into xRole values ('COMP01', 'db82d2a2-6df3-4dc7-8cbb-c9ea057fcaa6', 'Consultant', false, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');
insert into xRole values ('COMP01', 'db82d2a3-6df3-4dc7-8cbb-c9ea057fcaa6', 'Branch Manager', false, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');
insert into xRole values ('COMP01', 'db82d2a4-6df3-4dc7-8cbb-c9ea057fcaa6', 'Admin', false, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');

insert into xRole values ('COMP01', '8f48f22e-67ea-4053-9da9-b379ecced6eb', 'STAFF', false, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');
insert into xRole values ('COMP01', '0cd6f4d5-c40d-44c3-94f5-bfdbca80afa7', 'ATM', true, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');
insert into xRole values ('COMP01', '0f8bbd0e-4537-4279-bfe8-ae4a52d4bb94', 'MB', true, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');
insert into xRole values ('COMP01', 'c72f3b7e-bc27-467a-aadc-76b2b540bf7a', 'IB', true, 0, current_timestamp, '464d5a07-5ee9-429b-852d-6a0e47bdc286');


/*	xRoleMenu	*/
insert into xRoleMenu values ('7fb52f5f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a2-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c4-274e-432c-b623-bf92816aa10e');
insert into xRoleMenu values ('7fb52f6f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a2-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c5-274e-432c-b623-bf92816aa10e');
insert into xRoleMenu values ('7fb52f7f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a2-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c6-274e-432c-b623-bf92816aa10e');

insert into xRoleMenu values ('7fb52f8f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a3-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c4-274e-432c-b623-bf92816aa10e');
insert into xRoleMenu values ('7fb52f9f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a3-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c5-274e-432c-b623-bf92816aa10e');
insert into xRoleMenu values ('7fb52f0f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a3-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c6-274e-432c-b623-bf92816aa10e');

insert into xRoleMenu values ('7fb52f1f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a4-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c4-274e-432c-b623-bf92816aa10e');
insert into xRoleMenu values ('7fb52f2f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a4-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c5-274e-432c-b623-bf92816aa10e');
insert into xRoleMenu values ('7fb52f3f-1d7e-4bc3-9faf-2660255ffc20', 'db82d2a4-6df3-4dc7-8cbb-c9ea057fcaa6', '1bc601c6-274e-432c-b623-bf92816aa10e');

/*	xCBSIntegration */
insert into xCBSIntegration values ('fee3aa47-3e60-420e-9025-23600acef3d4', true);

/*	xUser */
insert into xuser values ('464d5a07-5ee9-429b-852d-6a0e47bdc286', 'user@company.com', 0, 'User', 'User', false, '9999999999', '$2a$10$8zeuMun9s1Gb7oNcbL6gaulwRDVkFKAFpSu3AGyXrubH0oonR.aZK', 0, current_timestamp, null, current_timestamp);

/*	xcustomer */
insert into xcustomer values ('aaa4b88a-9c61-4c0a-bba0-8a778f81a90d', '1000', 'Prashant', '', 0, null, null, current_timestamp);
insert into xcustomer values ('1fc6f346-e9da-4e49-84f0-d3c2d4018167', '2000', 'KG', '', 0, null, null, current_timestamp);
insert into xcustomer values ('eb5bb7fa-9ed2-49c8-af9d-4e9a60d75778', '3000', 'SK', '', 0, null, null, current_timestamp);
insert into xcustomer values ('6df5c3b1-d0df-474b-9543-c8153b6b2e3d', '4000', 'AD', '', 0, null, null, current_timestamp);

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
