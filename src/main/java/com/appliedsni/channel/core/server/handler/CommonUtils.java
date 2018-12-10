package com.appliedsni.channel.core.server.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.appliedsni.channel.core.server.config.ChannelApplicationContext;
import com.appliedsni.channel.core.server.entity.ActionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.ComplexTransactionStepEntity;
import com.appliedsni.channel.core.server.entity.CustomerEntity;
import com.appliedsni.channel.core.server.entity.CustomerMandateEntity;
import com.appliedsni.channel.core.server.entity.CustomerMandateServiceEntity;
import com.appliedsni.channel.core.server.entity.ProductRoleAccessEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionProductStepEntity;
import com.appliedsni.channel.core.server.entity.SimpleTransactionStepEntity;
import com.appliedsni.channel.core.server.queue.SpringAMQPRabbitSender;
import com.appliedsni.channel.core.server.user.domain.RoleEntity;
import com.appliedsni.channel.core.server.user.domain.UserRoleEntity;

import channel.client.dao.ServerDao;
import channel.client.function.CommonConstants;
import channel.client.function.CustomThreadLocal;
import channel.client.function.Status;

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
	
	public ComplexTransactionProductEntity getCTP(UUID pIdKey){
		return (ComplexTransactionProductEntity)mServerDao.get(ComplexTransactionProductEntity.class, pIdKey);
	}

	public ComplexTransactionStepEntity getCTS(UUID pIdKey){
		return (ComplexTransactionStepEntity)mServerDao.get(ComplexTransactionStepEntity.class, pIdKey);
	}

	public ComplexTransactionProductStepEntity getCTPS(UUID pCTPS){
		return (ComplexTransactionProductStepEntity)mServerDao.get(ComplexTransactionProductStepEntity.class, pCTPS);
	}
	
	public SimpleTransactionProductEntity getSTP(UUID pIdKey){
		return (SimpleTransactionProductEntity)mServerDao.get(SimpleTransactionProductEntity.class, pIdKey);
	}

	public SimpleTransactionProductStepEntity getSTPS(UUID pIdKey){
		return (SimpleTransactionProductStepEntity)mServerDao.get(SimpleTransactionProductStepEntity.class, pIdKey);
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
	
	public List<ComplexTransactionProductEntity> getCTPList(){
		List<Object> objList = mServerDao.find("from ComplexTransactionProductEntity order by mIdKey");
		
		List<ComplexTransactionProductEntity> ctpList = new ArrayList<ComplexTransactionProductEntity>();
		
		for(Object obj : objList){
			ComplexTransactionProductEntity ctp = (ComplexTransactionProductEntity)obj;
			ctpList.add(ctp);
		}
		
		return ctpList;
	}
		
	public void create(ComplexTransactionProductEntity pCTP){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					ComplexTransactionProductEntity ctp = getCTP(pCTP.getIdKey());
					if(ctp == null){
						mServerDao.save(pCTP);						
					} else {
						ctp.setName(pCTP.getName());
						ctp.setType(pCTP.getType());
						ctp.setStatus(pCTP.getStatus());
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
	}
	
	public void create(ComplexTransactionProductStepEntity pCTPS){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					ComplexTransactionProductStepEntity ctps = getCTPS(pCTPS.getIdKey());
					if(ctps == null){
						mServerDao.save(pCTPS);
					} else {
						ctps.setDecisionStatus(pCTPS.getDecisionStatus());
						ctps.setDelay(pCTPS.getDelay());
						ctps.setSeqNo(pCTPS.getSeqNo());
						ctps.setSimpleTransaction(pCTPS.getSimpleTransaction());
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});		
	}
	
	public void create(SimpleTransactionProductEntity pSTP){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					SimpleTransactionProductEntity stps = getSTP(pSTP.getIdKey());
					if(stps == null){
						mServerDao.save(pSTP);
					} else {
						stps.setName(pSTP.getName());
						stps.setStatus(pSTP.getStatus());
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});				
	}
	
	public void create(SimpleTransactionProductStepEntity pSTPS){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					SimpleTransactionProductStepEntity stps = getSTPS(pSTPS.getIdKey());
					if(stps == null){
						mServerDao.save(pSTPS);
					} else {
						stps.setDelay(pSTPS.getDelay());
						stps.setFunction(pSTPS.getFunction());
						stps.setFunctionClass(pSTPS.getFunctionClass());
						stps.setSeqNo(pSTPS.getSeqNo());
						stps.setSimpleTransaction(pSTPS.getSimpleTransaction());
						stps.setStatus(pSTPS.getStatus());						
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});						
	}

	public void activateCTP(UUID pCTP){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					ComplexTransactionProductEntity ctp = getCTP(pCTP);
					ctp.setStatus(Status.CLOSE);
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
	}

	public List<ComplexTransactionProductStepEntity> getCTPSteps(UUID pCTP){
		List<Object> objList = mServerDao.find("from ComplexTransactionProductStepEntity where mComplexTransaction = ? order by mSeqNo", getCTP(pCTP));
		
		List<ComplexTransactionProductStepEntity> ctpsList = new ArrayList<ComplexTransactionProductStepEntity>();
		
		for(Object obj : objList){
			ctpsList.add((ComplexTransactionProductStepEntity)obj);
		}
		
		return ctpsList;
	}
	
	public List<SimpleTransactionProductEntity> getSTPList(){
		List<Object> objList = mServerDao.find("from SimpleTransactionProductEntity order by mIdKey");
		
		List<SimpleTransactionProductEntity> stpList = new ArrayList<SimpleTransactionProductEntity>();
		
		for(Object obj : objList){
			stpList.add((SimpleTransactionProductEntity)obj);
		}
		
		return stpList;
	}

	public List<SimpleTransactionProductStepEntity> getSTPSteps(UUID pSTP){
		List<Object> objList = mServerDao.find("from SimpleTransactionProductStepEntity where mSimpleTransaction.mIdKey = ? order by mSeqNo", pSTP);
		
		List<SimpleTransactionProductStepEntity> stpsList = new ArrayList<SimpleTransactionProductStepEntity>();
		
		for(Object obj : objList){
			stpsList.add((SimpleTransactionProductStepEntity)obj);
		}
		
		return stpsList;
	}

	
	public List<RoleEntity> getRoles(){
		List<Object> objList = mServerDao.find("from RoleEntity");
		
		List<RoleEntity> roleList = new ArrayList<RoleEntity>();
		
		for(Object obj : objList){
			roleList.add((RoleEntity)obj);
		}
		
		return roleList;		
	}

	public List<RoleEntity> getChannels(){
		List<Object> objList = mServerDao.find("from RoleEntity where mChannel = true");
		
		List<RoleEntity> roleList = new ArrayList<RoleEntity>();
		
		for(Object obj : objList){
			roleList.add((RoleEntity)obj);
		}
		
		return roleList;		
	}
	
	public List<ComplexTransactionProductEntity> getChannelProductList(UUID pRole){
		List<Object> objList = mServerDao.find("select a.mComplexProduct from ProductRoleAccessEntity a inner join a.mRole b where b.mChannel = true and b.mIdKey = ?", pRole);
		
		List<ComplexTransactionProductEntity> resultList = new ArrayList<ComplexTransactionProductEntity>();
		
		for(Object obj : objList){
			resultList.add((ComplexTransactionProductEntity)obj);
		}
		
		return resultList;
	}

	/**
	 * NOTE : To be used by Channel
	 * <p>List of Products accessing to a customer through a channel
	 * 
	 * @param pRole
	 * @return List<ComplexTransactionProductEntity>
	 */
	public List<ComplexTransactionProductEntity> getAllowdProducts(UUID pCustomer){
		
		RoleEntity channelRole = getUserRole();
		
		List<Object> objList = mServerDao.find(
				" select a.mProduct "
				+ " from CustomerMandateServiceEntity a inner join a.mMandate b "
				+ " where b.mCustomer.mIdKey = ? "
				+ " and a.mChannel = ? ", pCustomer, channelRole);
		
		List<ComplexTransactionProductEntity> resultList = new ArrayList<ComplexTransactionProductEntity>();
		
		for(Object obj : objList){
			resultList.add((ComplexTransactionProductEntity)obj);
		}
		
		return resultList;
	}
	
	
	public List<RoleEntity> getProductRoles(UUID pCTPIdKey){
		List<Object> objList = mServerDao.find("select b from ProductRoleAccessEntity a inner join a.mRole b where b.mChannel = true and a.mComplexProduct.mIdKey = ?", pCTPIdKey);
		
		List<RoleEntity> roleList = new ArrayList<RoleEntity>();
		
		for(Object obj : objList){
			roleList.add((RoleEntity)obj);
		}
		
		return roleList;
	}
	
	public void createProductRoleAccess(UUID pCTP, UUID pRole){
		ComplexTransactionProductEntity ctp = (ComplexTransactionProductEntity)mServerDao.get(ComplexTransactionProductEntity.class, pCTP);
		RoleEntity role = (RoleEntity)mServerDao.get(RoleEntity.class, pRole);
		
		ProductRoleAccessEntity pra = new ProductRoleAccessEntity();
		pra.setComplexProduct(ctp);
		pra.setRole(role);
				
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					mServerDao.save(pra);
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});								
	}
	
	public List<CustomerEntity> getCustomerList(){
		List<Object> objList = mServerDao.find("from CustomerEntity");
		
		List<CustomerEntity> resultList = new ArrayList<CustomerEntity>();
		
		for(Object obj : objList){
			resultList.add((CustomerEntity)obj);
		}
		
		return resultList;
	}
	
	public List<CustomerMandateEntity> getCustomerMandateList(){
		List<Object> objList = mServerDao.find("from CustomerMandateEntity");
		
		List<CustomerMandateEntity> resultList = new ArrayList<CustomerMandateEntity>();
		
		for(Object obj : objList){
			resultList.add((CustomerMandateEntity)obj);
		}
		
		return resultList;
	}

	public CustomerMandateEntity getCustomerMandate(UUID pMandate){
		return (CustomerMandateEntity)mServerDao.get(CustomerMandateEntity.class, pMandate);
	}

	public CustomerMandateEntity createMandate(CustomerMandateEntity pMandate){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					CustomerMandateEntity mandate = getCustomerMandate(pMandate.getIdKey());
					if(mandate == null){
						mServerDao.save(pMandate);
					} else {
						//	TODO : Edit Mandate 
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
		
		return pMandate;
	}
	
	public CustomerEntity getCustomer(String pEmail){
		
		try{
			CustomerEntity customer = (CustomerEntity)mServerDao.find("from CustomerEntity where mEmail = ?", pEmail).get(0);
			return customer;
		}catch(Exception e){
			LOGGER.error("Could not find CIF", e);
		}
		
		return null;		
	}
	
	public List<CustomerMandateServiceEntity> getCustomerMandateServiceList(UUID pMandate){
		List<Object> objList = mServerDao.find("from CustomerMandateServiceEntity where mMandate.mIdKey = ?", pMandate);
		
		List<CustomerMandateServiceEntity> resultList = new ArrayList<CustomerMandateServiceEntity>();
		
		for(Object obj : objList){
			resultList.add((CustomerMandateServiceEntity)obj);
		}
		
		return resultList;
	}
	
	public CustomerMandateServiceEntity getCustomerMandateService(UUID pMandate, UUID pService){
		return (CustomerMandateServiceEntity)mServerDao.get(CustomerMandateServiceEntity.class, pService);
	}

	public CustomerMandateServiceEntity createMandateService(UUID pMandate, UUID pService, CustomerMandateServiceEntity pMandateService){
		ChannelApplicationContext.get().getBean("transactionTemplate", TransactionTemplate.class).execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus pStatus) {
				try{
					CustomerMandateServiceEntity mandateService = getCustomerMandateService(pMandate, pService);
					if(mandateService == null){
						mServerDao.save(pMandateService);
					} else {
						//	TODO : Edit Mandate 
					}
				} catch(Exception e) {
					LOGGER.error("Operation failed", e);
					pStatus.setRollbackOnly();
				}		
			}
		});
		
		return pMandateService;
	}
	
	public RoleEntity getUserRole(){
		UUID user = UUID.fromString(CustomThreadLocal.get(CommonConstants.CURRENT_USER).toString());
		return (RoleEntity)mServerDao.find("select a.mRole from UserRoleEntity a inner join a.mUserByUser b where b.mIdkey = ?", user).get(0);
	}
	
	public List<ComplexTransactionProductEntity> getAllowdProducts(){
		
		RoleEntity role = getUserRole(); 
		
		return getChannelProductList(role.getIdKey());
	}

	
}
