package com.application.service.likeunlikeservice.impl;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.application.data.dao.LikesCollectionDAOImpl;
import com.application.data.dao.documents.LikeDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.service.ILikeUnlikeAction;

@Service("PostLikeAction")
public class LikeUnlikeServiceImpl implements ILikeUnlikeAction{
	
	private MongoTemplate template;

	@Autowired
	public LikeUnlikeServiceImpl(MongoTemplate template){
		this.template = template;
	}

	@Override
	public void likePost(String id,String type,String username) {
		LikesCollectionDAOImpl likeCollection = (LikesCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION
					, template);
			
		LikeDocument document = (LikeDocument) likeCollection.findOne(DataAccessObjectConstants.POST_ID, id);
		if(document!=null) {
			document.getLikedBy().add(username);
		}else {
			document = new LikeDocument();
			document.setPostId(id);
			document.setType(type);
			document.setLikedBy(Lists.newArrayList(username));
			
		}
		likeCollection.save(document);
	}

	@Override
	public void unlikePost(String id,String type,String username) {
			LikesCollectionDAOImpl likeCollection = (LikesCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION
					, template);
			
		LikeDocument document = (LikeDocument) likeCollection.findOne(DataAccessObjectConstants.POST_ID, id);
		if(document!=null) {
			document.getLikedBy().remove(username);
			likeCollection.save(document);
		}		
	}
	

}
