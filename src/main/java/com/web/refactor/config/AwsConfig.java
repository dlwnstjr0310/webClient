package com.web.refactor.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AwsConfig {

	@Value("${AWS_ACCESS_KEY_ID}")
	private String accessKey;

	@Value("${AWS_SECRET_ACCESS_KEY}")
	private String secretKey;

	@Bean
	@Primary
	public AWSCredentialsProvider buildAWSCredentialsProvider() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return new AWSStaticCredentialsProvider(awsCredentials);
	}
}
