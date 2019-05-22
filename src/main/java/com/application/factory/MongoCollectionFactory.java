package com.application.factory;


import org.springframework.data.mongodb.core.MongoTemplate;

import com.application.data.dao.CommentCollectionDAOImpl;
import com.application.data.dao.ConversationCollectionDAOImpl;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.LikesCollectionDAOImpl;
import com.application.data.dao.MessageCollectionDAOImpl;
import com.application.data.dao.PostCollectionDAOImpl;
import com.application.data.dao.UsersCollectionDAOImpl;
import com.application.request.response.constants.DataAccessObjectConstants;

public class MongoCollectionFactory {
	
	private MongoCollectionFactory() {
		/**
		 * No object of Factory Class
		 */
	}

	/**
	 * Factory Pattern for Creating Instances of Data Access Object classes which 
	 * performs operations for a particular collection.
	 * @param Class of collection for which instance needs to be obtained. 
	 * @param Mongotemplate 
	 * @return Instance of class mapped to passed collection class which implements MongoCollectionDAO
	 */
	public static IMongoCollection getInstance(String collectionName,MongoTemplate template) {
		
		IMongoCollection collection;
		switch(collectionName) {
			case DataAccessObjectConstants.USER_DOCUMENT_COLLECTION:
					collection = new UsersCollectionDAOImpl(template);break;
			
			case DataAccessObjectConstants.COMMENT_DOCUMENT_COLLECTION:
					collection = new CommentCollectionDAOImpl(template);break;

			case DataAccessObjectConstants.POST_DOCUMENT_COLLECTION:
				collection = new PostCollectionDAOImpl(template);break;
			case DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION:
				collection = new LikesCollectionDAOImpl(template);break;
			case DataAccessObjectConstants.MESSAGE_DOCUMENT_COLLECTION:
				collection = new MessageCollectionDAOImpl(template);break;
			case DataAccessObjectConstants.CONVERSATION_DOCUMENT_COLLECTION:
				collection = new ConversationCollectionDAOImpl(template);break;
			default: collection = null;
					
		}
		return collection;
		
	}
}
