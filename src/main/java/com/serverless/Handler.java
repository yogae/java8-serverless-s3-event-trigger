package com.serverless;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class Handler implements RequestHandler<S3Event, String> {
	Regions clientRegion = Regions.US_EAST_1;
	S3Object s3Object = null;
	private static final Logger LOG = Logger.getLogger(Handler.class);

	@Override
	public String handleRequest(S3Event input, Context context) {
		S3EventNotificationRecord record = input.getRecords().get(0);
		String bucketName = record.getS3().getBucket().getName();
		String key = record.getS3().getObject().getKey();
		LOG.info("bucketName: " + bucketName + " key: " + key);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
		try {
			s3Object = s3Client.getObject(getObjectRequest);
			displayTextInputStream(s3Object.getObjectContent());
		} catch (IOException e) {
			LOG.error(e);
		} catch (AmazonS3Exception e) {
			LOG.error(e);
		} finally {
			try {
				if (s3Object != null) {
					s3Object.close();
				}
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		return "OK";
	}

	private static void displayTextInputStream(InputStream input) throws IOException {
		// Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = null;
		while ((line = reader.readLine()) != null) {
			LOG.info(line);
		}
	}
}
