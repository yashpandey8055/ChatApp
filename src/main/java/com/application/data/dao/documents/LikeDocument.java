package com.application.data.dao.documents;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="likes")
public class LikeDocument extends MongoDocument {
	private String postId;
	private List<String> likedBy;
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
	public List<String> getLikedBy() {
		return likedBy;
	}
	public void setLikedBy(List<String> likedBy) {
		this.likedBy = likedBy;
	}
	
}
