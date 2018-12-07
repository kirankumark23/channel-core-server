package com.appliedsni.channel.core.server.common.utils;

import org.hibernate.SQLQuery;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;

import channel.client.dao.ServerDao;
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

}
