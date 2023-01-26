package com.hcl.devsecops;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "events", path = "events")
public interface ContainerEventRepository extends PagingAndSortingRepository<ContainerEvent, Long>, CrudRepository<ContainerEvent,Long> {

	List<ContainerEvent> findByEventSource(@Param("eventSource") String eventSource);
	List<ContainerEvent> findByName(@Param("name") String name);
	List<ContainerEvent> findAll();

}
