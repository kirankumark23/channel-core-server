--drop table xSimpleTransactionProduct;

create table xSimpleTransactionProduct(
	xIdKey 				UUID primary key,
	xStatus 			character varying(20),
	xName 				character varying(200)
);

--drop table xSimpleTransactionProductStep;

create table xSimpleTransactionProductStep(
	xIdKey 				UUID primary key,
	xSeqNo 				integer,
	xSimpleTransaction 	UUID references xSimpleTransactionProduct (xIdKey),
	xDecisionStatus 	character varying(200),
	xDelay				integer check(xDelay >= 0),
	xFunction			character varying(200),
	unique(xSeqNo, xSimpleTransaction)
);

--drop table xComplexTransactionProduct;

create table xComplexTransactionProduct (
	xIdKey 				UUID primary key,
	xType 				character varying(20),
	xStatus 			character varying(20),
	xName 				character varying(200),
	unique(xType, xStatus)
);

--drop table xComplexTransactionProductStep;

create table xComplexTransactionProductStep (
	xIdKey 				UUID primary key,
	xSeqNo 				integer,
	xComplexTransaction UUID references xComplexTransactionProduct(xIdKey),
	xDecisionStatus 	character varying(200),
	xDelay				integer check (xdelay >= 0),
	xSimpleTransaction 	UUID references xSimpleTransactionProduct(xidkey),
	unique(xSeqNo, xComplexTransaction)
);

--drop table xSimpleTransaction;

create table xSimpleTransaction(
	xIdKey 				UUID primary key,
	xProduct 			UUID references xSimpleTransactionProduct(xidkey),
	xStatus 			character varying(20),
	xAdded 				timestamp
);

--drop table xSimpleTransactionStep;

create table xSimpleTransactionStep (
	xIdKey 				UUID primary key,
	xSeqNo 				integer,
	xSimpleTransaction 	UUID references xSimpleTransaction(xidkey),
	xFunction			character varying(200),
	xDecisionStatus 	character varying(200),
	xDelay				integer check (xdelay >= 0),
	xExecutionStatus 	character varying(200),
	xResultStatus 		character varying(200),
	xData 				character varying(2000),
	xadded 				timestamp,
	unique(xSimpleTransaction, xSeqNo)
);

--drop table xComplexTransaction;

create table xComplexTransaction(
	xIdKey 				UUID primary key,
	xProduct 			UUID references xComplexTransactionProduct(xidkey),
	xStatus 			character varying(20),
	xAdded 				timestamp
);


--drop table xComplexTransactionStep;

create table xComplexTransactionStep(
	xIdKey 				UUID primary key,
	xSeqNo 				integer,
	xComplexTransaction UUID references xComplexTransaction(xIdKey),
	xSimpleTransaction 	UUID references xSimpleTransaction(xidkey),
	xDecisionStatus 	character varying(200),
	xDelay				integer check (xdelay >= 0),
	xExecutionStatus 	character varying(200),
	xResultStatus 		character varying(200),
	xData 				character varying(2000),
	xAdded 				timestamp,
	unique(xComplexTransaction, xSeqNo)
);

--drop table xBranch;
create table xBranch(
	xCompany			character varying(20),
	xIdKey				character varying(20),
	xName				character varying(200),
	xversion 			integer NOT NULL,
	xAdded				timestamp,
	xAddedBy			UUID,
	xlastupdate 		timestamp NULL,	
	primary key (xCompany, xIdKey)
);

--drop table xMenu;
create table xMenu(
	xCompany			character varying(20),
	xIdKey				UUID primary key,
	xSeqNo				integer,
	xMainMenu			character varying(200),
	xSubMenu			character varying(200),
	xDescription 		character varying(200),
	xURL				character varying(200),
	xversion 			integer NOT NULL,
	xAdded				timestamp,
	xAddedBy			UUID,
	xlastupdate 		timestamp NULL	
);

--drop table xRole;
create table xRole(
	xCompany			character varying(20),
	xIdKey				character varying(200) primary key,
	xName				character varying(200),
	xversion 			integer NOT NULL,
	xAdded				timestamp,
	xAddedBy			UUID,
	xlastupdate 		timestamp NULL	
);

--drop table xRoleMenu;
create table xRoleMenu(
	xCompany		character varying(20),
	xIdKey			UUID primary key,
	xRole			character varying(200) references xrole(xidkey),
	xMenu			UUID references xmenu(xidkey),
	xversion 		integer NOT NULL,
	xAdded			timestamp,
	xAddedBy		UUID,
	xlastupdate 	timestamp null,	
	unique (xRole, xMenu)
);

drop table xEntity;
create table xEntity(
	xCompany		character varying(20),
	xIdKey			character varying(200) primary key,
	xName 			character varying(200),
	xversion 		integer NOT NULL,
	xAdded			timestamp,
	xAddedBy		UUID,
	xlastupdate 	timestamp NULL	
);

create table xRoleActions(
	xCompany		character varying(20),
	xIdKey			UUID primary key,
	xRole			character varying(200),
	xEntity			character varying(200) references xEntity(xIdKey),
	xAction			character varying(200),
	xversion 		integer NOT NULL,
	xAdded			timestamp,
	xAddedBy		UUID,
	xlastupdate 	timestamp NULL	
);

create table xUserRole(
	xIdKey			UUID primary key,
	xUser			UUID references xUser(xIdkey),
	xCompany		character varying(20),
	xBranch			character varying(200),
	xRole			character varying(200) references xRole(xIdKey),
	xversion 		integer NOT NULL,
	xAdded			timestamp,
	xAddedBy		UUID,
	xlastupdate 	timestamp null,	
	unique (xUser, xCompany, xBranch, xRole)
);


create table xUser(

);

create table xUserSession(

);

create table xUserSession(

);

create table xCompany(

);



