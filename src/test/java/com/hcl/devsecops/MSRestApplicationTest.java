/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hcl.devsecops;


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

@SpringBootTest
@AutoConfigureMockMvc
public class MSRestApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ContainerEventRepository containerEventRepository;

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		containerEventRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.events").exists());
	}

/*
	@Test
	public void shouldCreateEntity() throws Exception {

		mockMvc.perform(post("/events/").content(
				"{\"name\": \"DEVICE_ADD\", \"eventSource\":\"IoTDevice 1\"}"))
				.andExpect(status().isCreated())
				.andReturn();
	}
*/

	@Test
	public void shouldRetrieveEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/events").content(
				"{\"name\": \"DEVICE_LOAD\", \"eventSource\":\"IoTDevice 2\"}")).andExpect(
						status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("DEVICE_LOAD")).andExpect(
						jsonPath("$.eventSource").value("IoTDevice 2"));
	}

	@Test
	public void shouldQueryEntity() throws Exception {

		mockMvc.perform(post("/events").content(
				"{ \"name\": \"DEVICE_CLOSED\", \"eventSource\":\"IoTDevice 44\"}")).andExpect(
						status().isCreated());

		mockMvc.perform(
				get("/events/search/findByName?name={name}", "DEVICE_CLOSED")).andExpect(
						status().isOk()).andExpect(
								jsonPath("$._embedded.events[0].name").value(
										"DEVICE_CLOSED"));
	}

	@Test
	public void shouldUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/events").content(
				"{\"name\": \"DEVICE_HOLD\", \"eventSource\":\"DEVICE 33\"}")).andExpect(
						status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(put(location).content(
				"{\"name\": \"DEVICE_HOLD\", \"eventSource\":\"DEVICE 33\"}")).andExpect(
						status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("DEVICE_HOLD")).andExpect(
						jsonPath("$.eventSource").value("DEVICE 33"));
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/events").content(
				"{\"name\": \"DEVICE_UPDATE\", \"eventSource\":\"DEVICE 3\"}")).andExpect(
						status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");

		mockMvc.perform(
				patch(location).content("{\"name\": \"DEVICE_UPDATE\"}")).andExpect(
						status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("DEVICE_UPDATE")).andExpect(
						jsonPath("$.eventSource").value("DEVICE 3"));
	}

	@Test
	public void shouldDeleteEntity() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/events").content(
				"{ \"name\": \"DEVICE_DELETE\", \"eventSource\":\"DEVICE 4\"}")).andExpect(
						status().isCreated()).andReturn();

		String location = mvcResult.getResponse().getHeader("Location");
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}
}
