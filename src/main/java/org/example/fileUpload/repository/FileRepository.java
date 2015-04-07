package org.example.fileUpload.repository;

import java.util.Collection;

import org.example.fileUpload.client.UserSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(path = UserSvcApi.VIDEO_SVC_PATH)
public interface FileRepository extends CrudRepository<File, Long>{

	public Collection<File> findByName(
			@Param(UserSvcApi.TITLE_PARAMETER) String title);
	
	public Collection<File> findByDurationLessThan(
			@Param(UserSvcApi.DURATION_PARAMETER) long maxduration);
}
