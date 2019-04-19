package com.application.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ketu-default.properties")
public class DefaultApplicationProperties {

	private String defaultProfileUrl;

	public String getDefaultProfileUrl() {
		return defaultProfileUrl;
	}
	@Value("${initial.profilepictureUrl}")
	public void setDefaultProfileUrl(String defaultProfileUrl) {
		this.defaultProfileUrl = defaultProfileUrl;
	}


}
