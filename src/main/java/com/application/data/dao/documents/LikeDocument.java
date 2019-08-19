package com.application.data.dao.documents;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="likes")
public class LikeDocument extends MongoDocument {
	private String _id;

	private String postId;
	private Set<String> likedBy;
	private String type;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public Set<String> getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(Set<String> likedBy) {
		this.likedBy = likedBy;
	}
	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}
	
}
