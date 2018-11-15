package com.appliedsni.channel.core.server.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

public abstract class AbstractEntity {

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

}
