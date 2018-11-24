package com.appliedsni.channel.core.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
@MappedSuperclass
public abstract class AbstractEntity {
	
	@Column(name = "XVERSION", nullable = false)
    private int mVersion;
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "xlastupdate", nullable = true)
    private Date mLastUpdate;
    public AbstractEntity(){}
	@Transient
	private List<ActionEntity> mActions;
	
	public List<ActionEntity> getActions() {
		return mActions;
	}

	public void setActions(List<ActionEntity> pActions) {
		mActions = pActions;
	}
	
	public void addAction(ActionEntity pAction){
		if(mActions == null){
			mActions = new ArrayList<ActionEntity>();
		}
		mActions.add(pAction);
	}

	public int getVersion() {
		return mVersion;
	}

	public void setVersion(int pVersion) {
		mVersion = pVersion;
	}

	public Date getLastUpdate() {
		return mLastUpdate;
	}

	public void setLastUpdate(Date pLastUpdate) {
		mLastUpdate = pLastUpdate;
	}
	

}
