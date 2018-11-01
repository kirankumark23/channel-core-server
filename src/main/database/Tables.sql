--drop table xSimpleTransactionProduct;

create table xSimpleTransactionProduct(
	xIdKey 	UUID primary key,
	xStatus character varying(20),
	xName 	character varying(200)
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
	xIdKey 	UUID primary key,
	xType 	character varying(20),
	xStatus character varying(20),
	xName 	character varying(200),
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
	xIdKey 		UUID primary key,
	xProduct 	UUID references xSimpleTransactionProduct(xidkey),
	xStatus 	character varying(20),
	xAdded 		timestamp
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
	xIdKey 		UUID primary key,
	xProduct 	UUID references xComplexTransactionProduct(xidkey),
	xStatus 	character varying(20),
	xAdded 		timestamp
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
