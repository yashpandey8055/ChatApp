package com.application.service.displaypost.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.application.bean.PostResponse;
import com.application.bean.ViewPostBean;
import com.application.data.dao.IMongoCollection;
import com.application.data.dao.documents.CommentDocument;
import com.application.data.dao.documents.LikeDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.data.dao.documents.PostDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.bean.UserActivityReqResBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.DisplayPostService;
import com.application.service.userservice.impl.FollowingUserServiceImpl;
import com.application.utils.DateUtils;

@Service("UserSpecific")
public class UserSpecificPostServiceImpl implements DisplayPostService {

	private MongoTemplate template;
	
	@Autowired
	FollowingUserServiceImpl followingUser;
	
	@Autowired
	public UserSpecificPostServiceImpl(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public GenericResponseBean viewPost(ViewPostBean viewPostBean) {
		String username = viewPostBean.getUsernameForPost();
		IMongoCollection postCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.POST_DOCUMENT_COLLECTION
				, template);
		
		IMongoCollection likesCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.LIKE_DOCUMENT_COLLECTION
				, template);
		
		IMongoCollection commentCollection = MongoCollectionFactory.getInstance(DataAccessObjectConstants.COMMENT_DOCUMENT_COLLECTION
				, template);
		
		List<PostResponse> response = new ArrayList<>();

		
		//Fetch all the post of user this user is following updated in last 3 days, 
		Query postQuery = Query.query(Criteria
				.where(DataAccessObjectConstants.USERNAME).is(username));

		List<? extends MongoDocument> posts = postCollection.executeQuery(postQuery);
		
		UserActivityReqResBean userDetail = (UserActivityReqResBean) followingUser.getUserDetails(username).getData();
		
		for(MongoDocument postDocument:posts) {
			PostDocument post  = (PostDocument) postDocument;
			LikeDocument postLikeDocument =  (LikeDocument) likesCollection.findOne(DataAccessObjectConstants.POST_ID,post.getId());
			PostResponse postResponse = new PostResponse();
			postResponse.setDaysAgo(DateUtils.calculateTimeDifference(post.getUpdationDate()));
			postResponse.setLikedByUser(Optional.ofNullable(postLikeDocument).map(x-> x.getLikedBy().contains(username)).orElse(false));
			postResponse.setLikesCount(Optional.ofNullable(postLikeDocument).map(x-> x.getLikedBy().size()).orElse(0));
			postResponse.setUser(userDetail);
			List<? extends MongoDocument> comments = commentCollection.executeQuery(Query.query(Criteria
					.where(DataAccessObjectConstants.POST_ID).is(post.getId())).limit(GeneralConstants.COMMENT_LIMIT_ON_VIEW_POST));
			
			comments.stream().forEach(x->{((CommentDocument)x).setDaysAgo(DateUtils.calculateTimeDifference(((CommentDocument)x).getCreationDate()));
			
			LikeDocument commentLikeDocument = (LikeDocument) likesCollection.findOne(DataAccessObjectConstants.POST_ID,((CommentDocument)x).getId());
				((CommentDocument) x).setLikedByUser(Optional.ofNullable(commentLikeDocument).map(it-> it.getLikedBy().contains(viewPostBean.getActiveUser().getUsername())).orElse(false));
			});
			postResponse.setComments(comments);
			postResponse.setPost(post);
			
			response.add(postResponse);
		}
		GenericResponseBean responseBean = new GenericResponseBean();
		responseBean.setCode(HttpStatus.OK);
		responseBean.setData(response);
		responseBean.setType(RequestResponseConstant.SUCCESS_RESPONSE);
		return responseBean;
	}
	
	
}
