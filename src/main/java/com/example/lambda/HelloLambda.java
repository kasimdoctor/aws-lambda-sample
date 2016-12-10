package com.example.lambda;

import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class HelloLambda implements RequestHandler<Request, Response> {
    
    public static int globalInt = 222;

    @Override
    public Response handleRequest(Request request, Context context) {
        LambdaLogger logger = context.getLogger();
        int localInt = 777;
        logger.log(String.format("Received request %s with UUID: %s ", request.toString(), UUID.randomUUID()));
        
        // This log line tests if the globalInt and localInt values remain same/change for subsequent invocations of the function on AWS.
        logger.log(String.format("Global Int++ = %d AND Local Int++ = %d ", ++globalInt, ++localInt));
        
        return new Response("Hello, " + request.getFirstName() + " " + request.getLastName());
    }
}
