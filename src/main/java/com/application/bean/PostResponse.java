package com.application.bean;

import com.application.service.dao.documents.PostDocument;

import java.util.List;

import com.application.service.dao.documents.CommentDocument;
import com.application.service.dao.documents.UserDocument;

public class PostResponse {
	PostDocument post;

	UserDocument user;
	
	List<CommentDocument> comments;
	
	public List<CommentDocument> getComments() {
		return comments;
	}
	public void setComments(List<CommentDocument> comments) {
		this.comments = comments;
	}
	public PostDocument getPost() {
		return post;
	}
	public void setPost(PostDocument post) {
		this.post = post;
	}
	public UserDocument getUser() {
		return user;
	}
	public void setUser(UserDocument user) {
		this.user = user;
	}
}
