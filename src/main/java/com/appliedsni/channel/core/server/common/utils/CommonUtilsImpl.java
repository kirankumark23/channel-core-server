package com.appliedsni.channel.core.server.common.utils;

import java.util.List;
import java.util.UUID;

import org.hibernate.SQLQuery;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.CustomerEntity;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;

import channel.client.dao.ServerDao;
import channel.client.function.CommonConstants;
import channel.client.function.CustomThreadLocal;
import channel.client.function.Status;
import channel.client.function.common.CommonUtils;

public class CommonUtilsImpl extends CommonUtils{
	private ServerDao mServerDao;
	
	public CommonUtilsImpl(ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public static CommonUtilsImpl get(){
		return ChannelApplicationContext.get().getBean("commonUtilsImpl", CommonUtilsImpl.class);
	}

	@Override
	public String getData(int pComplexStep, int pSimpleStep) {
		StringBuffer queryString = new StringBuffer();
		queryString.append(" select xdata from vw_simpletransactionstep ");
		queryString.append(" where xidkey = :CTIdKey ");
		queryString.append(" and xCTSSeqNo = :CTSSeqNo ");
		queryString.append(" and xSTSSeqNo = :STSSeqNo ");
		
		SQLQuery query = mServerDao.getSessionFactory().getCurrentSession().createSQLQuery(queryString.toString());
		query.setParameter("CTIdKey", CustomThreadLocal.get("CT").toString());
		query.setParameter("CTSSeqNo", pComplexStep);
		query.setParameter("STSSeqNo", pSimpleStep);
		
		String data = null;
		try{
			data = (String)query.list().get(0);
		}catch(IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("No record found for CT : "+ CustomThreadLocal.get("CT").toString() 
					+ ", pComplexStep : " + pComplexStep + ", pSimpleStep : " + pSimpleStep);
		}
		
		return data;
	}

	@Override
	public Status getResultStatus(int pComplexStep, int pSimpleStep) {
		StringBuffer queryString = new StringBuffer();
		queryString.append(" select xresultstatus from vw_simpletransactionstep ");
		queryString.append(" where xidkey = :CTIdKey ");
		queryString.append(" and xCTSSeqNo = :CTSSeqNo ");
		queryString.append(" and xSTSSeqNo = :STSSeqNo ");
		
		SQLQuery query = mServerDao.getSessionFactory().getCurrentSession().createSQLQuery(queryString.toString());
		query.setParameter("CTIdKey", CustomThreadLocal.get("CT").toString());
		query.setParameter("CTSSeqNo", pComplexStep);
		query.setParameter("STSSeqNo", pSimpleStep);
		
		String status = null;
		try{
			status = (String)query.list().get(0);
		}catch(IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("No record found for CT : "+ CustomThreadLocal.get("CT").toString() 
					+ ", pComplexStep : " + pComplexStep + ", pSimpleStep : " + pSimpleStep);
		}
		
		return Status.valueOf(status);
	}

	@Override
	public Status getExecutionStatus(int pComplexStep, int pSimpleStep) {
		
		StringBuffer queryString = new StringBuffer();
		queryString.append(" select xexecutionstatus from vw_simpletransactionstep ");
		queryString.append(" where xidkey = :CTIdKey ");
		queryString.append(" and xCTSSeqNo = :CTSSeqNo ");
		queryString.append(" and xSTSSeqNo = :STSSeqNo ");
		
		SQLQuery query = mServerDao.getSessionFactory().getCurrentSession().createSQLQuery(queryString.toString());
		query.setParameter("CTIdKey", CustomThreadLocal.get("CT").toString());
		query.setParameter("CTSSeqNo", pComplexStep);
		query.setParameter("STSSeqNo", pSimpleStep);
		
		String status = null;
		try{
			status = (String)query.list().get(0);
		}catch(IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("No record found for CT : "+ CustomThreadLocal.get("CT").toString() 
					+ ", pComplexStep : " + pComplexStep + ", pSimpleStep : " + pSimpleStep);
		}
		
		return Status.valueOf(status);
	}

	@Override
	public String getData(int pComplexStep) {
		String data = null;
		try{
			Object dataObj = mServerDao.find("select mData from ComplexTransactionStepEntity "
					+ " where mComplexTransaction.mIdKey = ? "
					+ " and mSeqNo = ? ", CustomThreadLocal.get("CT"), pComplexStep).get(0);
			
			data = (String)dataObj;
		} catch(Exception e){
			throw new NullPointerException("No data found for " + pComplexStep);
		}
		
		return data;
	}

	@Override
	public Status getResultStatus(int pComplexStep) {
		Object dataObj = mServerDao.find("select mResultStatus from ComplexTransactionStepEntity "
				+ " where mComplexTransaction.mIdKey = ? "
				+ " and mSeqNo = ? ", CustomThreadLocal.get("CT"), pComplexStep).get(0);
		
		String data = (String)dataObj;
		return Status.valueOf(data);
	}

	@Override
	public Status getExecutionStatus(int pComplexStep) {
		Object dataObj = mServerDao.find("select mExecutionStatus from ComplexTransactionStepEntity "
				+ " where mComplexTransaction.mIdKey = ? "
				+ " and mSeqNo = ? ", CustomThreadLocal.get("CT"), pComplexStep).get(0);
		
		String data = (String)dataObj;
		return Status.valueOf(data);
	}

	@Override
	public boolean checkMandate() {		
		CustomerEntity customer = (CustomerEntity)mServerDao.get(CustomerEntity.class, UUID.fromString(CustomThreadLocal.get("CUSTOMER").toString()));
		ComplexTransactionEntity ct = (ComplexTransactionEntity)mServerDao.get(ComplexTransactionEntity.class, UUID.fromString(CustomThreadLocal.get("CT").toString()));
		RoleEntity role = getUserRole();
		
		List<Object> result =  mServerDao.find("from CustomerMandateServiceEntity a inner join a.mMandate b "
				+ " where b.mCustomer = ? "
				+ " and a.mProduct = ? "
				+ " and a.mChannel = ? ", customer, ct.getProduct(), role);
		
		return result.size() > 0;
	}

	public RoleEntity getUserRole(){
		UUID user = UUID.fromString(CustomThreadLocal.get(CommonConstants.CURRENT_USER).toString());
		return (RoleEntity)mServerDao.find("select a.mRole from UserRoleEntity a inner join a.mUserByUser b where b.mIdkey = ?", user).get(0);
	}

	

}
