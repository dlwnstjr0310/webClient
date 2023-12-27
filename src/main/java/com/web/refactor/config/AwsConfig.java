package com.web.refactor.config;

@Deprecated
//@Configuration
public class AwsConfig {

	//todo: 추후 aws 계정 만들고 다시 활성화 ( CLI 설정 해야함 )

//	@Value("${AWS_ACCESS_KEY_ID}")
//	private String accessKey;
//
//	@Value("${AWS_SECRET_ACCESS_KEY}")
//	private String secretKey;
//
//	@Bean
//	@Primary
//	public AWSCredentialsProvider buildAWSCredentialsProvider() {
//		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//		return new AWSStaticCredentialsProvider(awsCredentials);
//	}
}
