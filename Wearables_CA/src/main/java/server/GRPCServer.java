package server;

import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import com.distSCA.service3_calorie.CalculateRequest;
import com.distSCA.service3_calorie.CalculateResponse;
import com.distSCA.service3_calorie.CalculateRequest.Operation;
import com.distSCA.service3_calorie.MathServiceGrpc.MathServiceImplBase;

//import ds.examples.maths.MathServer;
//import com.distSCA.service2_prox.MathServiceGrpc.MathServiceImplBase;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

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

	// Server set up default message for all connections
	public static void main(String args[]) throws IOException, InterruptedException {
		
		GRPCServer grpcserver = new GRPCServer();

		Properties prop = grpcserver.getProperties();
		
		grpcserver.registerService(prop);
		 
		 System.out.println("starting GRPC Server");
		 Server server = ServerBuilder.forPort(60151).addService(

				 new UserService()).build();
		 
		 server.start(); // kickstart server
		 
		 System.out.println("Server starting at "+ server.getPort()); // 60151 for demo purposes
	        server.awaitTermination();
	 }
	
private Properties getProperties() {
		
		Properties prop = null;		
		
		 try (InputStream input = new FileInputStream("src/main/resources/grpc.properties")) {

	            prop = new Properties();

	            // load the properties file in resources
	            prop.load(input);

	            // get the property value and print it out
	            System.out.println("Loading GRPC properies ...");
	            System.out.println("\t service_type: " + prop.getProperty("service_type"));
	            System.out.println("\t service_name: " +prop.getProperty("service_name"));
	            System.out.println("\t service_description: " +prop.getProperty("service_description"));
		        System.out.println("\t service_port: " +prop.getProperty("service_port"));

	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	
		 return prop;
	}
	
	
	private  void registerService(Properties prop) {
		
		 try {
	            // Create a JmDNS instance
	            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
	            
	            String service_type = prop.getProperty("service_type") ;//_maths._tcp.local.;
	            String service_name = prop.getProperty("service_name")  ;// "wearables"
	            int service_port = Integer.valueOf( prop.getProperty("service_port") );// #.60151;

	            
	            String service_description_properties = prop.getProperty("service_description")  ;
	            
	            // Register a service
	            ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_description_properties);
	            jmdns.registerService(serviceInfo);
	            
	            System.out.printf("registering service with type %s and name %s \n", service_type, service_name);
	            
	            // Wait
	            Thread.sleep(1000);

	            // Unregister all services
	            //jmdns.unregisterAllServices();

	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	}
	
	// Calculate Request relevant to Service 3 Calorie counter
	public void calculate(CalculateRequest request, 
			StreamObserver<CalculateResponse> responseObserver) {

		System.out.println("receiving calculate method " + request.getNumber1() + " , " + request.getNumber2() + " " + request.getOperation() );

		float value = Float.NaN;
		String msg= "ok " + request.getOperation().name() + " result ";

		if(	request.getOperation()==Operation.ADDITION)
			value = request.getNumber1() + request.getNumber2();
		else if(	request.getOperation()==Operation.SUBTRACTION) // mainly for demo and test purposes - serving no real function
			value = request.getNumber1() - request.getNumber2();
		else if(	request.getOperation()==Operation.MULTIPLICATION) // this is the primary one used to calc calories times no. days
			value = request.getNumber1() * request.getNumber2();
		else if(	request.getOperation()==Operation.DIVISION) // mainly for demo and test purposes - serving no real function
			value = request.getNumber1() / request.getNumber2();
		else {
			value = Float.NaN;
			msg = "no supported/implemented operation";
		}		

		CalculateResponse reply = CalculateResponse.newBuilder().setResult(value).setMessage(msg).build();

		responseObserver.onNext(reply);

		responseObserver.onCompleted();
	}
	
	
	// Random request used for Service 2 proximity values
	public void generateRandomNumbers(RandomRequest request,
			StreamObserver<NumberResponse> responseObserver) {


		System.out.printf("Receiving generateRandom method num: %d from: %d to: %d \n",
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

	// Average values used in Method 4.1.1 bidirectional steaming factoring for weather
	public StreamObserver<NumberMessage> averageValues(
			StreamObserver<CalculateResponse> responseObserver) {


		return new StreamObserver<NumberMessage>() {

			ArrayList<Float> list = new ArrayList();

			@Override
			public void onNext(NumberMessage request) {

				System.out.println("Receiving averageValues method num: "+ request.getNumber()  );

				list.add(request.getNumber());		           

			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method

			}

			@Override
			public void onCompleted() {
				System.out.printf("Receiving averageValues method complete \n" );

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
	
	// potentially extraneous code - putting in as placeholder as it may be used
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
