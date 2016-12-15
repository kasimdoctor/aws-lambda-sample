package com.example.lambda;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import io.spoonshift.domain.dao.UserDAO;
import io.spoonshift.domain.model.User;
import io.spoonshift.utils.db.DynamoDbUtils;


public class HelloLambda implements RequestHandler<S3Event, Response> {

	private DynamoDB dynamoDB;
	private UserDAO userDAO;

	public HelloLambda() {
		System.out.println("Default No-Args Constructor Called...");
		dynamoDB = new DynamoDB(DynamoDbUtils.getDynamoDbClient(Regions.US_EAST_1));
		userDAO = new UserDAO(dynamoDB);
	}

	@Override
	public Response handleRequest(S3Event s3event, Context context) {
		LambdaLogger logger = context.getLogger();

		processS3Event(s3event, logger);
		create3Users();

		System.out.println("Printing rows from User Table");
		System.out.println(userDAO.readById(1));
		System.out.println(userDAO.readById(2));
		System.out.println(userDAO.readById(3));

		return new Response("Hello, Kasim Doctor");
	}
	
	private void create3Users() {
		System.out.println("Creating 3 Test Users");
		User user = new User();
		user.setFirstName("Kasim");
		user.setLastName("Doctor");
		user.setActive(true);
		user.setId(1);

		User user2 = new User();
		user2.setFirstName("Queen");
		user2.setLastName("Elizabeth");
		user2.setActive(true);
		user2.setId(2);

		User user3 = new User();
		user3.setFirstName("Adolf");
		user3.setLastName("Hitler");
		user3.setActive(false);
		user3.setId(3);

		userDAO.addUser(user);
		userDAO.addUser(user2);
		userDAO.addUser(user3);
	}
	
	private void processS3Event(S3Event s3Event, LambdaLogger logger) {
		List<S3EventNotification.S3EventNotificationRecord> records = s3Event.getRecords();
		logger.log("Number of records in the S3Event =  " + records.size() + "\n");
		logger.log("The S3Event looks like this: \n");
		S3EventNotification.S3EventNotificationRecord s3EventNotificationRecord = records.get(0);
		logger.log(s3Event.toJson());
	}

	public void inspectContextObject(Context context) {
		System.out.println();
		System.out.println("Context Object Details: ");
		System.out.println("Max mem allocated: " + context.getMemoryLimitInMB());
		System.out.println("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());
		System.out.println("CloudWatch log stream name: " + context.getLogStreamName());
		System.out.println("CloudWatch log group name: " + context.getLogGroupName());
	}

}
