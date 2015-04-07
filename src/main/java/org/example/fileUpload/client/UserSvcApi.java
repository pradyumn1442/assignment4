package org.example.fileUpload.client;

import java.util.Collection;

import org.example.fileUpload.repository.File;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;


public interface UserSvcApi {
	
	public static final String PASSWORD_PARAMETER = "password";
public static final String USERNAME_PARAMETER = "username";
	public static final String TITLE_PARAMETER = "title";
	public static final String DURATION_PARAMETER = "duration";
	public static final String TOKEN_PATH = "/oauth/token";
	public static final String VIDEO_SVC_PATH = "/video";
	public static final String VIDEO_TITLE_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByName";
	public static final String VIDEO_DURATION_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByDurationLessThan";
	
	@GET(VIDEO_SVC_PATH)
	public Collection<File> getVideoList();
	
	@POST(VIDEO_SVC_PATH)
	public Void addVideo(@Body File v);
	
	@GET(VIDEO_TITLE_SEARCH_PATH)
	public Collection<File> findByTitle(@Query(TITLE_PARAMETER) String title);
	
	@GET(VIDEO_DURATION_SEARCH_PATH)
	public Collection<File> findByDurationLessThan(@Query(DURATION_PARAMETER) String title);
	
}
