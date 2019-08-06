package com.application.data.dao;

import java.util.List;
import org.springframework.data.mongodb.core.query.Query;

import com.application.data.dao.documents.MongoDocument;
import com.mongodb.client.result.DeleteResult;

public interface IMongoCollection {
	
	/**
	 * Find a document from the MongoDB Collection.
	 * The Key can any of the field of document which is bind strictly to String Type. 
	 * The Value can be Numeric or String type. 
	 * A document is returned if the Key-Value pair matches with any document and the first match is returned. 
	 * 
	 * For a list of document , See findList(String key,Object value) method
	 * @param key
	 * @param value
	 * @return MongoDocument
	 */
	
	public MongoDocument findOne(String key,Object value);
	
	/**
	 * Find a list of  document from the MongoDB Collection specified by the document type in generic E.
	 * 
	 * 
	 * The Key can any of the field of document which is bind strictly to String Type. 
	 * The Value can be Numeric or String type. 
	 * 
	 * List of specified document is returned if the Key-Value pair matches with any document and the first match is returned. 
	 * 
	 * For a unique document with unique key-pair of document , See find(String key,Object value) method
	 * @param key
	 * @param value
	 * @return List<MongoDocument>
	 */
	public List<? extends MongoDocument> findList(String key,Object value);
	
	
	/**
	 * Insert/Update a document into the MongoDB Collection.
	 *
	 * The document is returned with MongoDb ObjectId created after inserting.
	 * For Update, set the MongoDocument with objectId (Or Property annotated with @Id) first.   
	 * @param <E>

	 * @param MongoDocument
	 * @return MongoDocument
	 */
	public MongoDocument save(MongoDocument document);
	
	
	/**
	 * Deletes a document into the MongoDB Collection.
	 * 
	 * 
	 * The document/documents is/are deleted based upon the key-value pair
	 * @param key
	 * @param value
	 * @return DeleteResult
	 */
	public  DeleteResult delete(String key, Object value);
	
	
	/**
	 * Counts the Number of Document with given Query
	 * 
	 * 
	 * @param Query
	 * @return Number of Records matching with Query 
	 */
	public Long count(Query query);
	/**
	 * Execute the Query on given Collection. 
	 * 
	 * 
	 * The document/documents is/are deleted based upon the key-value pair
	 * @param key
	 * @param value
	 * @return DeleteResult
	 */
	public List<? extends MongoDocument> executeQuery(Query query);
	
	
}
