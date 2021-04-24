package user;

import com.distSCA.service1_login.User.APIResponse;
import com.distSCA.service1_login.User.Empty;
import com.distSCA.service1_login.User.LoginRequest;
import com.distSCA.service1_login.userGrpc.userImplBase;
import io.grpc.stub.StreamObserver;

public class UserService extends userImplBase {
	
	@Override
	public void login(LoginRequest request, StreamObserver<APIResponse> responseObserver) {

		System.out.println("Inside login");

		String username = request.getUsername();
		String password = request.getPassword();

		APIResponse.Builder response = APIResponse.newBuilder();
		if (username.equals(password)) {

			// return success message

			response.setResponseCode(0).setResponsemessage("SUCCESS");

		} else {
			response.setResponseCode(100).setResponsemessage("INVALID PASSWORD");
		}

		responseObserver.onNext(response.build());
		responseObserver.onCompleted();

	}

	@Override
	public void logout(Empty request, StreamObserver<APIResponse> responseObserver) {

	}

}
