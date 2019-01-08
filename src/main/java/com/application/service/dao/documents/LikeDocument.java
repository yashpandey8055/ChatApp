package com.application.service.dao.documents;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="likes")
public class LikeDocument {
	private String _id ;
	private String postId;
	private List<String> likedBy;
	private long netCount;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
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
	public long getNetCount() {
		return netCount;
	}
	public void setNetCount(long netCount) {
		this.netCount = netCount;
	}
}
