package com.hcl.devsecops;


import com.hcl.devsecops.ContainerEventRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.Assert.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;


//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MSApplicationTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private ContainerEventRepository containerEventRepository;

    @BeforeEach
    public void deleteAllBeforeTests() throws Exception {
        containerEventRepository.deleteAll();
    }

    @Test
    void testaddEvent() throws Exception{
       /* ContainerEvent containerEvent = new ContainerEvent("EVENT_ADD", "IOT_DEVICE1");
        ContainerEvent event = containerEventRepository.save(containerEvent);
        assertEquals(event.getName(),"EVENT_ADD");
*/
            mockMvc.perform(post("/events/").content(
                            "{\"name\": \"DEVICE_ADD\", \"eventSource\":\"IoTDevice 1\"}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(
                    status().isOk());
    }

    @Test
    void testGetAllContainerEvent() throws Exception{

        ContainerEvent containerEvent1 = new ContainerEvent("EVENT_ADD","IOT_DEVICE1");
        containerEventRepository.save(containerEvent1);
        ContainerEvent containerEvent2 = new ContainerEvent("EVENT_UPDATE","IOT_DEVICE2");
        containerEventRepository.save(containerEvent2);
        List<ContainerEvent> allContainerEvent = containerEventRepository.findAll();

        mockMvc.perform(
                get("/events/").contentType(MediaType.APPLICATION_JSON)).andExpect(
                status().isOk());




    }

}
