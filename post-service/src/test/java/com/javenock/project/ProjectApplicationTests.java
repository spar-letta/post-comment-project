package com.javenock.project;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.github.cmduque.amqp.mock.AMQPServerMock;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static io.github.cmduque.amqp.mock.dto.ServerConfig.defaultConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public abstract class ProjectApplicationTests {

	String accessToken = "eyJraWQiOiI2ZmVkYjZkOS1hNWQyLTRjZjYtOWEwZi1jNjdiZmJiYjBmZDEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI2MzE2MjIyLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgiLCJ1c2VyUHVibGljSWQiOiI0NDZhYzZmMC00Y2NiLTRkZDQtYWNhZS1kN2YxMmM5YmU1MjUiLCJleHAiOjE3MjY0MDI2MjIsImlhdCI6MTcyNjMxNjIyMiwianRpIjoiNTQyNjZjYzUtODQ0Mi00YjJlLTljNmItMDZiYmVmZmUxYjQ4IiwiYXV0aG9yaXRpZXMiOlsiQVNTSUdOX1BSSVZJTEVHRV9UT19ST0xFIiwiREVMRVRFX1BPU1QiLCJSRUFEX1JPTEVTIiwiUkVBRF9DT01NRU5UIiwiQ1JFQVRFX1BPU1QiLCJVUERBVEVfQ09NTUVOVCIsIkRFTEVURV9DT01NRU5UIiwiQ1JFQVRFX1BSSVZJTEVHRSIsIlVQREFURV9QT1NUIiwiQVJUSUNMRV9XUklURSIsIkNSRUFURV9ST0xFIiwiQ1JFQVRFX0NPTU1FTlQiLCJBUlRJQ0xFX1JFQUQiLCJSRUFEX1BPU1RTIl0sInVzZXJuYW1lIjoidGVzdHVzZXIifQ.gLlDYcc7VmT9O0AIHoVZGwtMYtJl2754KnUcbKVRlHQNt-Edp-XPThwv4ArazvTfqyj4IsjGijuJ-b74GFKCF62fLlpUO81yFQMv2rdgL9SvtlFmMUkqW_h8fz0MiseNkcNNQs7qeVfAwzVYLDXUcl611B5-Mv7LG3QQd09hHeryR8JJNrkjIKajuS7LddNWTuJiZGGwlW72T-zSh46lJW91e5O-ATNfiKpsAKKbbyeL9P7mzzV7VUFEAFzHMXWUsO1-0mur8EJvcr83ilaOgiQqEXU2RVquhGPxS43QMTXcrvpI1elX3FC3jZ1qxUTpWPEEiKg_Z41-FLECo8tPYg";

	@Value("${local.server.port}")
	public int port;

	@Rule
	public WireMockRule wireMockRuleUserManagementService = new WireMockRule(4562);

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUpGlobal() throws IOException {
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		String password = "password";

		try {
			//temp to allow testing with rabbitmq configs
			AMQPServerMock server = new AMQPServerMock(defaultConfig().withKnownHosts("local.vh"));
			server.start();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
