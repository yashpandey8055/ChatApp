package com.application.request.response.constants;

public class DataAccessObjectConstants {
	
	private DataAccessObjectConstants() {
		/**
		 * No Constructor for Constant File
		 */
	}

	/**
	 * Collections
	 */
	public static final String USER_DOCUMENT_COLLECTION = "UserDocument";
	public static final String COMMENT_DOCUMENT_COLLECTION = "CommentDocument";
	public static final String POST_DOCUMENT_COLLECTION = "PostDocument";
	public static final String LIKE_DOCUMENT_COLLECTION = "LikeDocument";
	public static final String MESSAGE_DOCUMENT_COLLECTION = "MessageDocument";
	public static final String CONVERSATION_DOCUMENT_COLLECTION = "ConversationDocument";
	public static final String FOLLOW_DOCUMENT_COLLECTION = "FollowDocument";
	
	/**
	 * Fields
	 */
	public static final String USERNAME = "username";
	public static final String ID_FIELD = "_id";
	public static final String POST_ID = "postId";
	public static final String CREATION_DATE = "creationDate";
	public static final String UPDATION_DATE = "updationDate";
	public static final String PARTICIPANTS = "participants";
	public static final String SENDER = "sender";
	public static final String RECEIVER = "receiver";
	public static final String CONVERSATION_ID = "conversationId";
	public static final String CONNECT_PARTICIPANTS = "connection";
	public static final String REQUESTED_TO = "requestedTo";
	public static final String CONNECTION_ACCEPTED = "accepted";
	public static final String CONNECTION_ACTIVE = "connectiveActive";
	public static final String CONNECTION_DOCUMENT_FIELD = "connection";



	
	
}
