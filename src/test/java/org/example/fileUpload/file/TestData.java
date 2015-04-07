package org.example.fileUpload.file;

import java.util.UUID;

import org.example.fileUpload.repository.File;

import com.fasterxml.jackson.databind.ObjectMapper;
public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	public static File randomVideo() {
		String id = UUID.randomUUID().toString();
		String title = "File"+id;
		String url = "url"+id;
		long duration = 60 * (int)Math.rint(Math.random() * 60) * 1000; // random time up to 1hr
		return new File(title, url, duration);
	}
	
	public static String toJson(Object o) throws Exception{
		return objectMapper.writeValueAsString(o);
	}
}
