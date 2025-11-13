package com.hotel.management.service.impl;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hotel.management.exception.OurException;


public class AwsS3Service {

	private final String bucketName="anshuman-hotel-images";
	
	@Value("${aws.s3.access.key}")
	private String awsS3accessKey;
	
	@Value("${aws.s3.secret.key}")
	private String awsS3secretKey;
	
	
		public String saveImageToS3(MultipartFile photo) {
			String s3LocationImage=null;
			
			try { 
				String s3FileName=photo.getOriginalFilename();
				
				BasicAWSCredentials  awsCredentials=new BasicAWSCredentials(awsS3accessKey,awsS3secretKey);
				   AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
		                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
		                    .withRegion("us-east-1") // explicitly set region
		                    .build();
				
				 InputStream inputStream=photo.getInputStream();
				 
				 ObjectMetadata metadata=new ObjectMetadata();
				 metadata.setContentType("image/jpeg");
				 
				 PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,s3FileName,inputStream,metadata);
				 s3Client.putObject(putObjectRequest);
				 s3LocationImage="https://"+bucketName+".s3.amazonaws.com/"+s3FileName;
				 
				 
				
			}catch(Exception e) {
				throw new OurException("Unable to upload images to S3 bucket"+e.getMessage());
			}
			return s3LocationImage;
		}
}
 