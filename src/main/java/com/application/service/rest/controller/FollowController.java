package com.application.service.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.bean.User;
import com.application.data.dao.FollowCollectionDAOImpl;
import com.application.data.dao.documents.ConnectionsDocument;
import com.application.data.dao.documents.MongoDocument;
import com.application.factory.MongoCollectionFactory;
import com.application.request.response.bean.GenericResponseBean;
import com.application.request.response.constants.DataAccessObjectConstants;
import com.application.request.response.constants.GeneralConstants;
import com.application.request.response.constants.RequestResponseConstant;
import com.application.service.DoUndoActionExecuteService;
import com.application.service.IDoUndoAction;
import com.application.service.action.doundoactionservice.impl.DoActionImpl;
import com.application.service.action.doundoactionservice.impl.UndoActionImpl;

@RestController
@RequestMapping("/user")
public class FollowController {

	@Autowired
	@Qualifier("UserFollowAction")
	IDoUndoAction userFollowUnfollowAction;
	
	@Autowired
	@Qualifier("ConnectionAcceptRequest")
	IDoUndoAction acceptRejectConnection;
	
	
	@Autowired
	MongoTemplate template;
	
	@GetMapping("/connect/{user}")
	public ResponseEntity<String> followUser(@AuthenticationPrincipal User currentUser, @PathVariable("user") String userName){
		DoUndoActionExecuteService followUser = new DoActionImpl(userFollowUnfollowAction);
		followUser.execute(currentUser.getUsername(),currentUser.getProfileUrl(),userName);
		return new ResponseEntity<>(GeneralConstants.CONNECTED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/disconnect/{user}")
	public ResponseEntity<String> unfollowUser(@AuthenticationPrincipal User currentUser,@PathVariable("user") String userName){
		DoUndoActionExecuteService unfollowUser = new UndoActionImpl(userFollowUnfollowAction);
		unfollowUser.execute(currentUser.getUsername(),currentUser.getProfileUrl(),userName);
		return new ResponseEntity<>(GeneralConstants.DISCONNECTED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/connect/accept/{user}")
	public ResponseEntity<String> acceptRequest(@AuthenticationPrincipal User currentUser,@PathVariable("user") String userName){
		DoUndoActionExecuteService unfollowUser = new DoActionImpl(acceptRejectConnection);
		unfollowUser.execute(currentUser.getUsername(),GeneralConstants.ACCEPT_CONNECT_TYPE,userName);
		return new ResponseEntity<>(GeneralConstants.CONNECTED_MSG,HttpStatus.OK);
	}
	
	@GetMapping("/disconnect/reject/{user}")
	public ResponseEntity<String> rejectRequest(@AuthenticationPrincipal User currentUser,@PathVariable("user") String userName){
		DoUndoActionExecuteService unfollowUser = new UndoActionImpl(userFollowUnfollowAction);
		unfollowUser.execute(currentUser.getUsername(),GeneralConstants.REJECT_CONNECT_TYPE,userName);
		return new ResponseEntity<>(GeneralConstants.DISCONNECTED_MSG,HttpStatus.OK);
	}
	
	  @GetMapping("/connect/status/{user}")
	  public ResponseEntity<GenericResponseBean> isFollowing(@AuthenticationPrincipal final User currentUser,@PathVariable("user") String userName) {
		  FollowCollectionDAOImpl followDocument = (FollowCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.FOLLOW_DOCUMENT_COLLECTION
					, template);
		String[] connection = {currentUser.getUsername(),userName};
		Map<String,Object[]> criteria = new HashMap<>(1);
		criteria.put(DataAccessObjectConstants.CONNECT_PARTICIPANTS, connection);
		ConnectionsDocument document = (ConnectionsDocument) followDocument.findWithMutipleKeys(criteria);
		String message = null;
		if(document!=null&&document.isConnectiveActive()) {
			if(document.isAccepted()) {
				message = RequestResponseConstant.CONNECT_MESSAGE_DISCONNECT;
			}else {
				message = RequestResponseConstant.CONNECT_MESSAGE_REQUESTED;
			}
		}else {
			message = RequestResponseConstant.CONNECT_MESSAGE_CONNECT;
		}
		GenericResponseBean response = new GenericResponseBean(HttpStatus.OK,  RequestResponseConstant.SUCCESS_RESPONSE, message);
		return new ResponseEntity<>(response,HttpStatus.OK);
	  }
	  
	  @GetMapping("/connectionrequests")
	  public ResponseEntity<GenericResponseBean> getConnectionRequests(@AuthenticationPrincipal final User currentUser) {
		  FollowCollectionDAOImpl followDocument = (FollowCollectionDAOImpl) MongoCollectionFactory.getInstance(DataAccessObjectConstants.FOLLOW_DOCUMENT_COLLECTION
					, template);

		  List<? extends MongoDocument> connectionRequests =   followDocument.executeQuery(Query.query(Criteria.where(DataAccessObjectConstants.REQUESTED_TO).is(currentUser.getUsername()).and(DataAccessObjectConstants.CONNECTION_ACCEPTED).is(false)
				  .and(DataAccessObjectConstants.CONNECTION_ACTIVE).is(true)));
		GenericResponseBean response = new GenericResponseBean(HttpStatus.OK,  RequestResponseConstant.SUCCESS_RESPONSE, connectionRequests);
		return new ResponseEntity<>(response,HttpStatus.OK);
	  }
}
