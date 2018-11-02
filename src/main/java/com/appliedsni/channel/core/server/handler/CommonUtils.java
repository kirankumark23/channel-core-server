package com.appliedsni.channel.core.server.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.dao.ServerDao;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;

public class CommonUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	private ServerDao mServerDao;
	
	public CommonUtils(ServerDao pServerDao){
		mServerDao = pServerDao;
	}
	
	public static CommonUtils get(){
		return ChannelApplicationContext.get().getBean("commonUtils", CommonUtils.class);
	}
	
	public List<ComplexTransactionEntity> getCTList(){
		
		List<Object> objList = mServerDao.find("from ComplexTransactionEntity order by mAdded");
		
		List<ComplexTransactionEntity> ctList = new ArrayList<ComplexTransactionEntity>();
		
		for(Object obj : objList){
			ctList.add((ComplexTransactionEntity)obj);
		}
		
		return ctList;
	}
	
	public ComplexTransactionEntity getCT(UUID pIdKey){
		return (ComplexTransactionEntity)mServerDao.get(ComplexTransactionEntity.class, pIdKey);
	}
	
	public ComplexTransactionStepEntity getCTS(UUID pIdKey){
		return (ComplexTransactionStepEntity)mServerDao.get(ComplexTransactionStepEntity.class, pIdKey);
	}

	public List<ComplexTransactionStepEntity> getCTSteps(UUID pCT){
		List<Object> objList = mServerDao.find("from ComplexTransactionStepEntity where mComplexTransaction = ? order by mSeqNo", getCT(pCT));
		
		List<ComplexTransactionStepEntity> ctsList = new ArrayList<ComplexTransactionStepEntity>();
		
		for(Object obj : objList){
			ctsList.add((ComplexTransactionStepEntity)obj);
		}
		
		return ctsList;
	}

	public List<SimpleTransactionStepEntity> getSTSteps(UUID pCT, UUID pCTS){
		List<Object> objList = mServerDao.find("from SimpleTransactionStepEntity where mSimpleTransaction = ? order by mSeqNo", getCTS(pCTS).getSimpleTransaction());
		
		List<SimpleTransactionStepEntity> stsList = new ArrayList<SimpleTransactionStepEntity>();
		
		for(Object obj : objList){
			stsList.add((SimpleTransactionStepEntity)obj);
		}
		
		return stsList;
	}


}
