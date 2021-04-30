package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.distSCA.service3_calorie.CalculateRequest;
import com.distSCA.service3_calorie.CalculateResponse;
import com.distSCA.service3_calorie.CalculateRequest.Operation;

import com.distSCA.service3_calorie.MathServiceGrpc.MathServiceImplBase;

import com.distSCA.service3_calorie.ConvertMessage;
import com.distSCA.service3_calorie.ConvertResponse;
import com.distSCA.service3_calorie.NumberMessage;
import com.distSCA.service3_calorie.NumberResponse;
import com.distSCA.service3_calorie.RandomRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import user.UserService;

public class GRPCServer extends MathServiceImplBase {

	public static void main(String args[]) throws IOException, InterruptedException {
		 
		 System.out.println("starting GRPC Server");
		 Server server = ServerBuilder.forPort(60601).addService(

				 new UserService()).build();
		 
		 server.start();
		 System.out.println("Server starting at "+ server.getPort());
	        server.awaitTermination();
	 }
	
	public void calculate(CalculateRequest request, 
			StreamObserver<CalculateResponse> responseObserver) {

		System.out.println("receiving calculate method " + request.getNumber1() + " , " + request.getNumber2() + " " + request.getOperation() );

		float value = Float.NaN;
		String msg= "ok " + request.getOperation().name() + " result ";

		if(	request.getOperation()==Operation.ADDITION)
			value = request.getNumber1() + request.getNumber2();
		else if(	request.getOperation()==Operation.SUBTRACTION)
			value = request.getNumber1() - request.getNumber2();
		else if(	request.getOperation()==Operation.MULTIPLICATION)
			value = request.getNumber1() * request.getNumber2();
		else if(	request.getOperation()==Operation.DIVISION)
			value = request.getNumber1() / request.getNumber2();
		else {
			value = Float.NaN;
			msg = "no supported/implemented operation";
		}		

		CalculateResponse reply = CalculateResponse.newBuilder().setResult(value).setMessage(msg).build();

		responseObserver.onNext(reply);

		responseObserver.onCompleted();
	}
	
	public void generateRandomNumbers(RandomRequest request,
			StreamObserver<NumberResponse> responseObserver) {


		System.out.printf("receiving generateRandom method num: %d from: %d to: %d \n",
				request.getNumbers(), request.getMin(), request.getMax()  );

		Random rand = new Random();

		for(int i=0; i<request.getNumbers(); i++) {

			int random_value = rand.nextInt(request.getMax() - request.getMin()) + request.getMin() + 1;

			NumberResponse reply = NumberResponse.newBuilder().setNumber(random_value).build();

			responseObserver.onNext(reply);

			try {
				//wait for a second
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		responseObserver.onCompleted();


	}


	public StreamObserver<NumberMessage> averageValues(
			StreamObserver<CalculateResponse> responseObserver) {


		return new StreamObserver<NumberMessage>() {

			ArrayList<Float> list = new ArrayList();

			@Override
			public void onNext(NumberMessage request) {

				System.out.println("receiving averageValues method num: "+ request.getNumber()  );

				list.add(request.getNumber());		           

			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stubal

			}

			@Override
			public void onCompleted() {
				System.out.printf("receiving averageValues method complete \n" );

				// System.out.println("mean "+ list.size());

				double temp = 0;	
				for(float v:  list) {
					temp = temp + v;
				}
				float mean = (float) (temp/list.size());

				CalculateResponse reply = CalculateResponse.newBuilder().setResult(mean).build();

				// System.out.println("mean "+ mean);
				responseObserver.onNext(reply);

				responseObserver.onCompleted();

			}

		};

	}

	public StreamObserver<ConvertMessage> convertBase(StreamObserver<ConvertResponse> responseObserver) {
		
		return new StreamObserver<ConvertMessage> () {

			@Override
			public void onNext(ConvertMessage msg) {
				System.out.println("receiving convertBase method num: "+ msg.getNumber() + " from-base: "+ msg.getFromBase() + " to-base: "+ msg.getToBase()  );
				
				String converted =  Integer.toString(Integer.parseInt(msg.getNumber(), msg.getFromBase()), msg.getToBase());
				
				ConvertResponse reply = ConvertResponse.newBuilder().setNumber(converted).setBase(msg.getToBase()).build();
				
				responseObserver.onNext(reply);
				
			}

			@Override
			public void onError(Throwable t) {
				
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("receiving convertBase completed ");
				
				//completed too
				responseObserver.onCompleted();
			}
			
		};
	}
}
