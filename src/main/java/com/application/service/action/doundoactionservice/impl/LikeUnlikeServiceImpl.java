package com.application.service.action.doundoactionservice.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.application.data.dao.LikesCollectionDAOImpl;
import com.application.data.dao.documents.LikeDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.IDoUndoAction;

@Service("PostLikeAction")
public class LikeUnlikeServiceImpl implements IDoUndoAction{
	
	private MongoTemplate template;

	@Autowired
	public LikeUnlikeServiceImpl(MongoTemplate template){
		this.template = template;
	}

	@Override
	public void doAction(String id,String type,String username) {
		LikesCollectionDAOImpl likeCollection = (LikesCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION
					, template);
			
		LikeDocument document = (LikeDocument) likeCollection.findOne(DataAccessObjectConstants.POST_ID, id);
		if(document!=null) {
			document.getLikedBy().add(username);
		}else {
			document = new LikeDocument();
			document.setPostId(id);
			document.setType(type);
			Set<String> list = new HashSet<>(1);
			list.add(username);
			document.setLikedBy(list);
			
		}
		likeCollection.save(document);
	}

	@Override
	public void undoAction(String id,String type,String username) {
			LikesCollectionDAOImpl likeCollection = (LikesCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION
					, template);
			
		LikeDocument document = (LikeDocument) likeCollection.findOne(DataAccessObjectConstants.POST_ID, id);
		if(document!=null) {
			document.getLikedBy().remove(username);
			likeCollection.save(document);
		}		
	}
	

}
