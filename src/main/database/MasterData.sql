/*	xBranch		*/
insert into xBranch values ('BRN001','Mumbai');
insert into xBranch values ('BRN002','Bangalore');

/*	xMenu		*/
insert into xMenu values ('1bc601c4-274e-432c-b623-bf92816aa10e', 1, 'Request', 'Balance Enquiry', 'Get balance', 'URL');
insert into xMenu values ('1bc601c5-274e-432c-b623-bf92816aa10e', 2, 'Request', 'Fund Transfer', 'Transfer Funds', 'URL');
insert into xMenu values ('1bc601c6-274e-432c-b623-bf92816aa10e', 3, 'History', 'Requests', 'Request History', 'URL');

/*	xRole		*/
insert into xRole values ('db82d2a2-6df3-4dc7-8cbb-c9ea057fcaa6', 'Consultant');
insert into xRole values ('db82d2a3-6df3-4dc7-8cbb-c9ea057fcaa6', 'Branch Manager');
insert into xRole values ('db82d2a4-6df3-4dc7-8cbb-c9ea057fcaa6', 'Admin');

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

/*	
