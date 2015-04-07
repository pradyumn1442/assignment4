package org.example.fileUpload.test;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.UUID;

import org.example.fileUpload.client.SecuredRestBuilder;
import org.example.fileUpload.client.SecuredRestException;
import org.example.fileUpload.client.UserSvcApi;
import org.example.fileUpload.file.TestData;
import org.example.fileUpload.repository.File;
import org.junit.Test;

import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.ApacheClient;

import com.google.gson.JsonObject;
public class UserSvcClientApiTest {

	private final String USERNAME = "admin";
	private final String PASSWORD = "pass";
	private final String CLIENT_ID = "mobile";
	private final String READ_ONLY_CLIENT_ID = "mobileReader";

	private final String TEST_URL = "https://localhost:8443";

	private UserSvcApi videoService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + UserSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(UserSvcApi.class);

	private UserSvcApi readOnlyVideoService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + UserSvcApi.TOKEN_PATH)
			.setUsername(USERNAME)
			.setPassword(PASSWORD)
			.setClientId(READ_ONLY_CLIENT_ID)
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(UserSvcApi.class);

	private UserSvcApi invalidClientVideoService = new SecuredRestBuilder()
			.setLoginEndpoint(TEST_URL + UserSvcApi.TOKEN_PATH)
			.setUsername(UUID.randomUUID().toString())
			.setPassword(UUID.randomUUID().toString())
			.setClientId(UUID.randomUUID().toString())
			.setClient(new ApacheClient(UnsafeHttpsClient.createUnsafeClient()))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(UserSvcApi.class);

	private File file = TestData.randomVideo();
	@Test
	public void testVideoAddAndList() throws Exception {
		// Add the file
		videoService.addVideo(file);

		// We should get back the file that we added above
		Collection<File> files = videoService.getVideoList();
		assertTrue(files.contains(file));
	}
	@Test
	public void testAccessDeniedWithIncorrectCredentials() throws Exception {

		try {
			invalidClientVideoService.addVideo(file);

			fail("The server should have prevented the client from adding a file"
					+ " because it presented invalid client/user credentials");
		} catch (RetrofitError e) {
			assert (e.getCause() instanceof SecuredRestException);
		}
	}
	@Test
	public void testReadOnlyClientAccess() throws Exception {

		Collection<File> files = readOnlyVideoService.getVideoList();
		assertNotNull(files);
		
		try {
			readOnlyVideoService.addVideo(file);

			fail("The server should have prevented the client from adding a file"
					+ " because it is using a read-only client ID");
		} catch (RetrofitError e) {
			JsonObject body = (JsonObject)e.getBodyAs(JsonObject.class);
			assertEquals("insufficient_scope", body.get("error").getAsString());
		}
	}


}
