package com.application;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class HandshakeInterceptorIml extends DefaultHandshakeHandler implements HandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest
             = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest
              .getServletRequest().getSession();
            
            attributes.put("sessionId", session.getId());
            
            System.out.println("Use is: "+request.getPrincipal());
        }
        
        
		return true;
	}

	@Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, 
                                      Map<String, Object> attributes) {
        if(request.getPrincipal()==null) {
        	Principal user = new Principal() {
				
				@Override
				public String getName() {
					
					return "yash";
				}
			};
			
			return user;
        }
        return request.getPrincipal();
    }
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
