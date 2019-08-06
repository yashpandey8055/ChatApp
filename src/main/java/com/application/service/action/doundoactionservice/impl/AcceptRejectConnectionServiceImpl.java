package com.application.service.action.doundoactionservice.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.application.data.dao.FollowCollectionDAOImpl;
import com.application.data.dao.documents.FollowDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.IDoUndoAction;

@Service("ConnectionAcceptRequest")
public class AcceptRejectConnectionServiceImpl implements IDoUndoAction{
	
	private MongoTemplate template;

	@Autowired
	public AcceptRejectConnectionServiceImpl(MongoTemplate template){
		this.template = template;
	}

	@Override
	public void doAction(String followedBy,String type,String followed) {
	FollowCollectionDAOImpl followDocument = (FollowCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.FOLLOW_DOCUMENT_COLLECTION
				, template);
	
	String[] connection = {followedBy,followed};
	Map<String,Object[]> criteria = new HashMap<>(1);
	criteria.put(DataAccessObjectConstants.CONNECT_PARTICIPANTS, connection);
	
	FollowDocument document = (FollowDocument) followDocument.findWithMutipleKeys(criteria);
	if(document!=null) {
		document.setRequestedTo(followed);
		document.setRequester(followedBy);
		document.setConnectiveActive(true);
		document.setAccepted(true);
	}
		followDocument.save(document);
	}

	@Override
	public void undoAction(String followedBy,String type,String followed) {
	FollowCollectionDAOImpl followDocument = (FollowCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.FOLLOW_DOCUMENT_COLLECTION
				, template);
	
	String[] connection = {followedBy,followed};
	Map<String,Object[]> criteria = new HashMap<>(1);
	criteria.put(DataAccessObjectConstants.CONNECT_PARTICIPANTS, connection);
	
	FollowDocument document = (FollowDocument) followDocument.findWithMutipleKeys(criteria);
	if(document!=null) {
		document.setConnectiveActive(false);
		document.setAccepted(false);
	}
		followDocument.save(document);
	}	

}
