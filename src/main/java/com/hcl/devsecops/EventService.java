package com.hcl.devsecops;

import java.util.List;
import com.hcl.devsecops.ContainerEvent;

public interface EventService {
    List<ContainerEvent> findAllContainerEvent();
    ContainerEvent findContainerEventByID(long id);
    void addContainerEvent(ContainerEvent b);
    }