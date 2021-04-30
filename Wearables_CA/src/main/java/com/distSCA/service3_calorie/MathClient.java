package com.distSCA.service3_calorie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.distSCA.service3_calorie.MathServiceGrpc.MathServiceBlockingStub;
import com.distSCA.service3_calorie.MathServiceGrpc.MathServiceStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;


public class MathClient {

	private static MathServiceBlockingStub blockingStub;
	private static MathServiceStub asyncStub;


	public static void main(String[] args) throws Exception {

		ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 60601)
				.usePlaintext()
				.build();

		//stubs -- generate from proto
		blockingStub = MathServiceGrpc.newBlockingStub(channel);

		asyncStub = MathServiceGrpc.newStub(channel);


		
		//calculate();
		
		
		
		generateRandomNumbersAsyn();
		generateRandomNumbersBlocking();

				
		//averageValues();

		//convertBase();

	}

	/*
	//unary rpc
	public static void calculate() {
		int num1 = 10;
		int num2 = 20;

		CalculateRequest req = CalculateRequest.newBuilder().setNumber1(num1).setNumber2(num2).build();

		CalculateResponse response = blockingStub.calculate(req);

	//	System.out.println("res: " + response.getResult() + " mes: " + response.getMessage());
		System.out.println("HEY THERE!" + response);
	}
	*/

	//blocking server-streaming
	public static void generateRandomNumbersBlocking() throws Exception{
		RandomRequest request = RandomRequest.newBuilder()
				.setNumbers(5).setMin(0).setMax(100).build();

		try {
			Iterator<NumberResponse> responces = blockingStub.generateRandomNumbers(request);

			while(responces.hasNext()) {
				NumberResponse temp = responces.next();
				System.out.println(temp.getNumber());				
			}

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}

	}


	public static void generateRandomNumbersAsyn() {

		RandomRequest request = RandomRequest.newBuilder()
				.setNumbers(10).setMin(0).setMax(1000).build();


		StreamObserver<NumberResponse> responseObserver = new StreamObserver<NumberResponse>() {

			int count =0 ;

			@Override
			public void onNext(NumberResponse value) {
				System.out.println("receiving random num " + value.getNumber());
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("stream is completed ... received "+ count+ " rand numbers");
			}

		};

		asyncStub.generateRandomNumbers(request, responseObserver);

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void averageValues() {

		StreamObserver<CalculateResponse> responseObserver = new StreamObserver<CalculateResponse>() {

			@Override
			public void onNext(CalculateResponse msg) {
				System.out.println("receiving average number " + msg.getResult() );
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onCompleted() {
				System.out.println("stream is completed ... receiveing converted numbers");
			}

		};


		StreamObserver<NumberMessage> requestObserver = asyncStub.averageValues(responseObserver);
		try {
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(125).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(112).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(34).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(56.75f).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(34).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(200).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(40).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(85).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(90.25f).build());
			Thread.sleep(500);

			requestObserver.onNext(NumberMessage.newBuilder().setNumber(70).build());
			Thread.sleep(500);


			// Mark the end of requests
			requestObserver.onCompleted();

			
			Thread.sleep(10000);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}


	}	

	public static void convertBase() {


		StreamObserver<ConvertResponse> responseObserver = new StreamObserver<ConvertResponse>() {

			int count =0 ;

			@Override
			public void onNext(ConvertResponse msg) {
				System.out.println("receiving converted number " + msg.getNumber() + " base: "+ msg.getBase() );
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("stream is completed ... received "+ count+ " converted numbers");
			}

		};



		StreamObserver<ConvertMessage> requestObserver = asyncStub.convertBase(responseObserver);

		try {

			requestObserver.onNext(ConvertMessage.newBuilder().setNumber("100").setFromBase(10).setToBase(2).build());
			requestObserver.onNext(ConvertMessage.newBuilder().setNumber("177").setFromBase(8).setToBase(10).build());
			requestObserver.onNext(ConvertMessage.newBuilder().setNumber("10100010").setFromBase(2).setToBase(10).build());
			requestObserver.onNext(ConvertMessage.newBuilder().setNumber("AF45").setFromBase(16).setToBase(2).build());
			requestObserver.onNext(ConvertMessage.newBuilder().setNumber("AF45").setFromBase(16).setToBase(10).build());


			// Mark the end of requests
			requestObserver.onCompleted();


			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);


		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}



		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	



}
