
/* xSimpleTransactionProduct */

insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('4c690fe6-6883-44f4-be3f-3b7cb220f500', 'OPEN', 'Ask Account', 0, current_timestamp);
insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('826b48d6-d112-4019-93f9-e0e53c2cc896', 'OPEN', 'Get Balance', 0, current_timestamp);
insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('30614b40-dea1-46f9-af41-30d65e1d233a', 'OPEN', 'Send Balance', 0, current_timestamp);

insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('530fa190-dc5e-11e8-b568-0800200c9a66', 'OPEN', 'Ask Account', 0, current_timestamp);
insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('530fa191-dc5e-11e8-b568-0800200c9a66', 'OPEN', 'Get Balance', 0, current_timestamp);
insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('530fa192-dc5e-11e8-b568-0800200c9a66', 'OPEN', 'Send Balance', 0, current_timestamp);

insert into xSimpleTransactionProduct (xidkey,xstatus,xname,xversion, xlastupdate) values ('f626ef32-dcdd-11e8-b568-0800200c9a66', 'OPEN', 'Get Account List', 0, current_timestamp);


/* xSimpleTransactionProductStep */

--Prod 1-Step 1
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('e52c68af-7ee6-45c9-8e73-236784554114', 1, '4c690fe6-6883-44f4-be3f-3b7cb220f500', 'SUCCESS', 0, 'com.company.business.AskAccount', 'execute', 0, current_timestamp);
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('09284752-d543-11e8-9f8b-f2801f1b9fd1', 2, '4c690fe6-6883-44f4-be3f-3b7cb220f500', 'SUCCESS', 0, 'com.company.business.ExtractAccount', 'execute', 0, current_timestamp);

--Prod 1-Step 2
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('bd26f1f7-7d64-4dba-bcb4-038ae00d7422', 1, '826b48d6-d112-4019-93f9-e0e53c2cc896', 'SUCCESS', 0, 'com.company.business.GetBalance', 'execute', 0, current_timestamp);
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('89607a88-4b1b-49d5-b7b8-52e56ba0b775', 2, '826b48d6-d112-4019-93f9-e0e53c2cc896', 'ERROR', 	0, 'com.company.business.SendDelay', 'execute', 0, current_timestamp);
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('30cec84b-4a82-49b5-8b40-2f8b7ee56827', 3, '826b48d6-d112-4019-93f9-e0e53c2cc896', 'SUCCESS', 1, 'com.company.business.GetBalance', 'execute', 0, current_timestamp);

--Prod 1-Step 3
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('616abaef-c49a-484a-8384-a83b1d6180c2', 1, '30614b40-dea1-46f9-af41-30d65e1d233a', 'SUCCESS', 0, 'com.company.business.SendInfo', 'execute', 0, current_timestamp);

--Prod 2-Step 1
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('acd24ca0-dc5e-11e8-b568-0800200c9a66', 1, '530fa190-dc5e-11e8-b568-0800200c9a66', 'SUCCESS', 0, 'com.company.business.CASA', 'fn_ask_ac', 0, current_timestamp);
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('acd24ca1-dc5e-11e8-b568-0800200c9a66', 2, '530fa190-dc5e-11e8-b568-0800200c9a66', 'SUCCESS', 0, 'com.company.business.CASA', 'fn_extract_ac', 0, current_timestamp);

--Prod 2-Step 2
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('acd24ca2-dc5e-11e8-b568-0800200c9a66', 1, '530fa191-dc5e-11e8-b568-0800200c9a66', 'SUCCESS', 0, 'com.appliedsni.channel.core.server.handler.FunctionHandler', 'fn_get_ac_balance', 0, current_timestamp);
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('acd24ca3-dc5e-11e8-b568-0800200c9a66', 2, '530fa191-dc5e-11e8-b568-0800200c9a66', 'ERROR', 	0, 'com.appliedsni.channel.core.server.handler.FunctionHandler', 'fn_send_delay', 0, current_timestamp);
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('acd24ca4-dc5e-11e8-b568-0800200c9a66', 3, '530fa191-dc5e-11e8-b568-0800200c9a66', 'SUCCESS', 1, 'com.appliedsni.channel.core.server.handler.FunctionHandler', 'fn_get_ac_balance', 0, current_timestamp);

--Prod 2-Step 3
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('acd24ca5-dc5e-11e8-b568-0800200c9a66', 1, '530fa192-dc5e-11e8-b568-0800200c9a66', 'SUCCESS', 0, 'com.appliedsni.channel.core.server.handler.FunctionHandler', 'fn_send_info', 0, current_timestamp);

--Prod 3-Step 1
insert into xSimpleTransactionProductStep (xidkey,xseqno,  xsimpletransaction,xdecisionstatus,xdelay, xclass,xfunction ,xversion,xlastupdate) values ('f626ef33-dcdd-11e8-b568-0800200c9a66', 1, 'f626ef32-dcdd-11e8-b568-0800200c9a66', 'SUCCESS', 0, 'com.company.business.CASA', 'fn_get_ac_list', 0, current_timestamp);

/* xComplexTransactionProduct */

insert into xComplexTransactionProduct (xidkey,  xtype, xstatus, xname,xversion,xlastupdate) values ('529aef14-bd0d-4e70-8066-441301ab7f1f', 'BALANCE_SUCCESS', 'OPEN', 'Balance Enquiry - Success', 0, current_timestamp);
insert into xComplexTransactionProduct (xidkey,  xtype, xstatus, xname,xversion,xlastupdate) values ('dedbe268-b660-4cfe-a883-5e8aa4d55355', 'BALANCE_FAIL', 'OPEN', 'Balance Enquiry - Fail', 0, current_timestamp);
insert into xComplexTransactionProduct (xidkey,  xtype, xstatus, xname,xversion,xlastupdate) values ('f626ef30-dcdd-11e8-b568-0800200c9a66', 'ACCOUNT_LIST', 'OPEN', 'List of accounts', 0, current_timestamp);

/* xComplexTransactionProductStep */



insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('740af554-4364-4ff9-9833-8c815d9701b9', 1, '529aef14-bd0d-4e70-8066-441301ab7f1f', 'SUCCESS', 0, '4c690fe6-6883-44f4-be3f-3b7cb220f500', 0, current_timestamp);
insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('5d98be7c-04e5-4e20-b7dc-26d5457881e2', 2, '529aef14-bd0d-4e70-8066-441301ab7f1f', 'SUCCESS', 0, '826b48d6-d112-4019-93f9-e0e53c2cc896', 0, current_timestamp);
insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('3cf19d5d-e322-451a-a908-7729ee404ed5', 3, '529aef14-bd0d-4e70-8066-441301ab7f1f', 'SUCCESS', 0, '30614b40-dea1-46f9-af41-30d65e1d233a', 0, current_timestamp);

insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('20c3795f-1b48-4d74-8390-e312b7e73acc', 1, 'dedbe268-b660-4cfe-a883-5e8aa4d55355', 'SUCCESS', 0, '530fa190-dc5e-11e8-b568-0800200c9a66', 0, current_timestamp);
insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('0bfdce30-ac90-4928-be61-a6a7b73be6cf', 2, 'dedbe268-b660-4cfe-a883-5e8aa4d55355', 'SUCCESS', 0, '530fa191-dc5e-11e8-b568-0800200c9a66', 0, current_timestamp);
insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('04a3577f-3318-42f5-bae7-5c7784dd9ffb', 3, 'dedbe268-b660-4cfe-a883-5e8aa4d55355', 'SUCCESS', 0, '530fa192-dc5e-11e8-b568-0800200c9a66', 0, current_timestamp);

insert into xComplexTransactionProductStep (xidkey,xseqno ,xcomplextransaction , xdecisionstatus, xdelay , xsimpletransaction,xversion,xlastupdate) values ('f626ef31-dcdd-11e8-b568-0800200c9a66', 1, 'f626ef30-dcdd-11e8-b568-0800200c9a66', 'SUCCESS', 0, 'f626ef32-dcdd-11e8-b568-0800200c9a66', 0, current_timestamp);


