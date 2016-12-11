package com.example.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


public final class DynamoDbUtils {

	private static AmazonDynamoDBClient client;

	public static AmazonDynamoDBClient getDynamoDbClient(Regions awsRegion) {
		if(awsRegion == null) {
			awsRegion = Regions.US_EAST_1;
		}

		if(client == null) {
			client = new AmazonDynamoDBClient();
			client.withRegion(awsRegion);
		}
		return client;
	}
}
