package com.application.data.dao;

import java.util.List;

import com.mongodb.client.result.DeleteResult;

public interface MongoCollectionDAO<E> {
	/**
	 * Find a document from the MongoDB Collection.
	 * The Key can any of the field of document which is bind strictly to String Type. 
	 * The Value can be Numeric or String type. 
	 * 
	 * The instance of the Implementation of Interface MongoCollectionDAO<E> should include the
	 *  Document (@Document(<collectionName>)) 
	 * class referring to the collection needed;
	 * A document is returned if the Key-Value pair matches with any document and the first match is returned. 
	 * 
	 * For a list of document , See findList(String key,T value) method
	 * @param key
	 * @param value
	 * @return Document
	 */
	public E findOne(String key,Object value);
	
	/**
	 * Find a list of  document from the MongoDB Collection specified by the document type in generic E.
	 * 
	 * The instance of the Implementation of Interface MongoCollectionDAO<E> should include the
	 *  Document (@Document(<collectionName>)) 
	 * class referring to the collection needed;
	 * 
	 * The Key can any of the field of document which is bind strictly to String Type. 
	 * The Value can be Numeric or String type. 
	 * 
	 * List of specified document is returned if the Key-Value pair matches with any document and the first match is returned. 
	 * 
	 * For a unique document with unique key-pair of document , See find(String key,T value) method
	 * @param <E>
	 * @param key
	 * @param value
	 * @return Document
	 */
	public List<E> findList(String key,Object value);
	
	/**
	 * Insert a document into the MongoDB Collection.
	 * 
	 * The instance of the Implementation of Interface MongoCollectionDAO<E> should include the
	 *  Document (@Document(<collectionName>)) 
	 * class referring to the collection needed;
	 * 
	 * The document is returned with Mongo ObjectId created after inserting.   
	 * @param <E>

	 * @param Object of Document of Generic parameter E
	 * @return Object of Document of Generic parameter E with ObjectId Included
	 */
	public E insert(E document);
	
	/**
	 * Updates a document into the MongoDB Collection.
	 * 
	 * The instance of the Implementation of Interface MongoCollectionDAO<E> should include the
	 *  Document (@Document(<collectionName>)) 
	 * class referring to the collection needed;
	 * 
	 * The document is updated based upon the ObjectId of the document. If document needs to be updated 
	 * with another property, annotate it with @Id.   
	 * @param <E>
	 * @param Object of Document of Generic parameter E with ObjectId( or Other Id) Included
	 * @return Object of Document of Generic parameter E with ObjectId Included
	 */
	public E update(E document);
	
	/**
	 * Deletes a document into the MongoDB Collection.
	 * 
	 * The instance of the Implementation of Interface MongoCollectionDAO<E> should include the
	 *  Document (@Document(<collectionName>)) 
	 * class referring to the collection needed;
	 * 
	 * The document/documents is/are deleted based upon the key-value pair
	 * @param key
	 * @param value
	 * @return DeleteResult
	 */
	public  DeleteResult delete(String key, Object value);
	
	
}
