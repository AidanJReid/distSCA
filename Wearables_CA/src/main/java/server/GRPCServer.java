package server;

import java.io.IOException;

import com.distSCA.service3_calorie.CalculateRequest;
import com.distSCA.service3_calorie.CalculateResponse;
import com.distSCA.service3_calorie.CalculateRequest.Operation;

import com.distSCA.service3_calorie.MathServiceGrpc.MathServiceImplBase;
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

}
