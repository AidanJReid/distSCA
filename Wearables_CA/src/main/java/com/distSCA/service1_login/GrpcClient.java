package com.distSCA.service1_login;

import com.distSCA.service1_login.User.APIResponse;
import com.distSCA.service1_login.User.LoginRequest;
//import com.distSCA.service1_login.userGrpc;
import com.distSCA.service1_login.userGrpc.userBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import io.grpc.StatusRuntimeException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {
	
	private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());

	public static void main(String[] args) throws Exception {
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",60601).usePlaintext().build();
		
		// stubs - generate from proto
		
		userBlockingStub userStub = userGrpc.newBlockingStub(channel);
		
		try {
			String name = "Aidan";
			String password = "Aidan";
		LoginRequest loginrequest = 
				LoginRequest.newBuilder()
				.setUsername(name)
				.setPassword(password)
				.build();
		
		APIResponse response = userStub.login(loginrequest);
		
		logger.info(response.getResponsemessage());
		
		} catch (StatusRuntimeException e) {
		    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		    return;		
		    
	    } finally {
		
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	    }
	}

}
