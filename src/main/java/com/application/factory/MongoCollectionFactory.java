package com.application.factory;


import org.springframework.data.mongodb.core.MongoTemplate;

import com.application.data.dao.CommentCollectionDAOImpl;
import com.application.data.dao.IMongoCollection;
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
					
			default: collection = null;
					
		}
		return collection;
		
	}
}
