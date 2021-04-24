package com.distSCA.service1_login;

import com.distSCA.service1_login.User.APIResponse;
import com.distSCA.service1_login.User.LoginRequest;
import com.distSCA.service1_login.userGrpc;
import com.distSCA.service1_login.userGrpc.userBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

	public static void main(String[] args) {
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090).usePlaintext().build();
		
		// stubs - generate from proto
		
		userBlockingStub userStub = userGrpc.newBlockingStub(channel);
		
		LoginRequest loginrequest = LoginRequest.newBuilder().setUsername("Aidan").setPassword("Aidan").build();
		
		APIResponse response = userStub.login(loginrequest);
		
		System.out.println(response.getResponsemessage());
		
		channel.shutdown();

	}

}
