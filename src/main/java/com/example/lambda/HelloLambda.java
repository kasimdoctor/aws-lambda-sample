package com.example.lambda;

import java.util.UUID;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.spoonshift.domain.dao.UserDAO;
import io.spoonshift.domain.model.User;
import io.spoonshift.utils.db.DynamoDbUtils;


public class HelloLambda implements RequestHandler<Request, Response> {

	private DynamoDB dynamoDB;
	private UserDAO userDAO;

	public HelloLambda() {
		System.out.println("Default No-Args Constructor Called...");
		dynamoDB = new DynamoDB(DynamoDbUtils.getDynamoDbClient(Regions.US_EAST_1));
		userDAO = new UserDAO(dynamoDB);
	}

	@Override
	public Response handleRequest(Request request, Context context) {
		LambdaLogger logger = context.getLogger();
		logger.log(String.format("Received request %s with UUID: %s ", request.toString(), UUID.randomUUID()));

		System.out.println("Inserting into Table Users");
		User user = new User();
		user.setFirstName("Kasim");
		user.setLastName("Doctor");
		user.setActive(true);
		user.setId(1);
		
		//userDAO.addUser(User.builder().id(2).firstName("Tom").lastName("Cruise").active(true).build());
		//userDAO.addUser(User.builder().id(3).firstName("Shahrukh").lastName("Khan").active(true).build());

		System.out.println("Printing rows from User Table");
		System.out.println(userDAO.readById(1));
		System.out.println(userDAO.readById(2));
		System.out.println(userDAO.readById(3));

		return new Response("Hello, " + request.getFirstName() + " " + request.getLastName());
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
