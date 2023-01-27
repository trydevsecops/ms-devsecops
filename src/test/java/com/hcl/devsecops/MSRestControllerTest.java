package com.hcl.devsecops;


import com.hcl.devsecops.ContainerEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
class MSApplicationTest {


    @Mock
    private ContainerEventRepository containerEventRepository;

    @Test
    void testaddEvent(){
        ContainerEvent containerEvent = new ContainerEvent("EVENT_ADD","IOT_DEVICE1");
        containerEventRepository.save(containerEvent);
        containerEventRepository.findByName("EVENT_ADD");
        Mockito.verify(containerEventRepository, Mockito.atLeastOnce()).findByName("EVENT_ADD");
    }

}
