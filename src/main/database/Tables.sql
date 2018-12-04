--drop table xSimpleTransactionProduct;

create table xSimpleTransactionProduct(
	xIdKey 				UUID primary key,
	xStatus 			character varying(20),
	xName 				character varying(200),
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp
);

--drop table xSimpleTransactionProductStep;

create table xSimpleTransactionProductStep(
	xIdKey 				UUID primary key,
	xSeqNo 				integer,
	xSimpleTransaction 	UUID references xSimpleTransactionProduct (xIdKey),
	xDecisionStatus 	character varying(200),
	xDelay				integer check(xDelay >= 0),
	xClass				character varying(200),
	xFunction			character varying(200),
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp  default current_timestamp,
	unique(xSeqNo, xSimpleTransaction)
);

--drop table xComplexTransactionProduct;

create table xComplexTransactionProduct (
	xIdKey 				UUID primary key,
	xType 				character varying(20),
	xStatus 			character varying(20),
	xName 				character varying(200),
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp,
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
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp,
	unique(xSeqNo, xComplexTransaction)
);

--drop table xSimpleTransaction;

create table xSimpleTransaction(
	xIdKey 				UUID primary key,
	xProduct 			UUID references xSimpleTransactionProduct(xidkey),
	xStatus 			character varying(20),
	xAdded 				timestamp,
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp
);

--drop table xSimpleTransactionStep;

create table xSimpleTransactionStep (
	xIdKey 				UUID primary key,
	xSeqNo 				integer,
	xSimpleTransaction 	UUID references xSimpleTransaction(xidkey),
	xClass				character varying(200),
	xFunction			character varying(200),
	xDecisionStatus 	character varying(200),
	xDelay				integer check (xdelay >= 0),
	xExecutionStatus 	character varying(200),
	xResultStatus 		character varying(200),
	xData 				character varying(2000),
	xadded 				timestamp,
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp,
	unique(xSimpleTransaction, xSeqNo)
);

--drop table xComplexTransaction;

create table xComplexTransaction(
	xIdKey 				UUID primary key,
	xProduct 			UUID references xComplexTransactionProduct(xidkey),
	xStatus 			character varying(20),
	xAdded 				timestamp,
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp
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
	xData 				bytea,
	xAdded 				timestamp,
	xversion 			integer NOT NULL,
	xlastupdate 		timestamp default current_timestamp,
	unique(xComplexTransaction, xSeqNo)
);

create table xUser(
	xidkey 				uuid NOT NULL primary key,
	xemailaddress 		varchar(200) NOT NULL,
	xfailloginattempts 	int4 NULL,
	xfirstname 			varchar(40) NULL,
	xlastname 			varchar(40) NULL,
	xlockuserindicator 	bool NOT NULL,
	xmobilenumber 		varchar(12) NULL,
	xpasswordhash 		varchar(200) NULL,
	xversion 			integer NOT NULL,
	xAdded				timestamp,
	xAddedBy			UUID references xUser(xidKey),
	xlastupdate 		timestamp NULL
);
--drop table xBranch;
create table xBranch(
	xCompany			character varying(20),
	xIdKey				character varying(20),
	xName				character varying(200),
	xversion 			integer NOT NULL,
	xAdded				timestamp,
	xAddedBy			UUID references xUser(xidKey),
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
	xAddedBy			UUID references xUser(xidKey),
	xlastupdate 		timestamp NULL	
);

--drop table xRole;
create table xRole(
	xCompany			character varying(20),
	xIdKey				character varying(200) primary key,
	xName				character varying(200),
	xversion 			integer NOT NULL,
	xAdded				timestamp,
	xAddedBy			UUID references xUser(xidKey),
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
	xAddedBy		UUID references xUser(xidKey),
	xlastupdate 	timestamp null,	
	unique (xRole, xMenu)
);

--drop table xEntity;
create table xEntity(
	xCompany		character varying(20),
	xIdKey			character varying(200) primary key,
	xName 			character varying(200),
	xversion 		integer NOT NULL,
	xAdded			timestamp,
	xAddedBy		UUID references xUser(xidKey),
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
	xAddedBy		UUID references xUser(xidKey),
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
	xAddedBy		UUID references xUser(xidKey),
	xlastupdate 	timestamp null,	
	unique (xUser, xCompany, xBranch, xRole)
);






create table xUserSession(

);


create table xCompany(
	xidkey 			uuid NOT NULL,
	xemail 			varchar(255) NULL,
	xname 			varchar(255) NOT NULL,
	xphonenumber 	varchar(255) NULL,
	xstatus 		bool NULL,
	xversion 		integer NOT NULL,
	xAdded			timestamp,
	xAddedBy		UUID references xUser(xidKey),
	xlastupdate 	timestamp NULL
);


create table xCBSIntegration(
xIdKey 				UUID primary key,
xOnline				boolean
);

create table xAuditLog (
	xIdKey 				UUID primary key,
	xPreviousState 		character varying(400),
	xCurrentState 		character varying(400),
	xVersion 			int,
	xEntityIdKey 		UUID,
	xEntityName 		character varying(400),
	xUser 				character varying(50),
	xlastupdate 		timestamp
);
