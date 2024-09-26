package com.javenock.event_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public abstract class EventServiceApplicationTests {

    public String accessToken = "eyJraWQiOiIxZDlkYzA3Yy1iZTRjLTQ4NTQtYjRhZS1iZTM2MjhlN2RjYjciLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzI3MzU4OTYzLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgiLCJ1c2VyUHVibGljSWQiOiI0NDZhYzZmMC00Y2NiLTRkZDQtYWNhZS1kN2YxMmM5YmU1MjUiLCJleHAiOjE3Mjc0NDUzNjMsImlhdCI6MTcyNzM1ODk2MywianRpIjoiYzk0YzEyNDQtY2JiNC00MGI4LTlhZGYtZTM3YjA5NDU4NDMwIiwiYXV0aG9yaXRpZXMiOlsiQVNTSUdOX1BSSVZJTEVHRV9UT19ST0xFIiwiREVMRVRFX1BPU1QiLCJSRUFEX1JPTEVTIiwiUkVBRF9DT01NRU5UIiwiQ1JFQVRFX1BPU1QiLCJVUERBVEVfQ09NTUVOVCIsIkRFTEVURV9DT01NRU5UIiwiQ1JFQVRFX1BSSVZJTEVHRSIsIlVQREFURV9QT1NUIiwiUkVBRF9SRVBPUlQiLCJBUlRJQ0xFX1dSSVRFIiwiQ1JFQVRFX1JPTEUiLCJDUkVBVEVfQ09NTUVOVCIsIkFSVElDTEVfUkVBRCIsIlJFQURfUE9TVFMiXSwidXNlcm5hbWUiOiJ0ZXN0dXNlciJ9.ckY9EpOS7ul6gVyI9g0cObbWJkHyGo95k3qPVO2WAPzpXIHKXZk4usUsHWay_IM9cHWTYcpr3JokyPAtTOuWy5uVKOaXmTvZJUNcp3kV4JyapLXdz91uWI0T64HLxYkToaExnmgGH_9ioFVkrmzfuNYwrfqF4bpayR48lco9RLkEyYz3bRRpE-aGq_cLMfnUcf198u_oZCvNjFZE9Jvi6gqogOBBkwO0EG85bZhWgmiLyOD3k8q0yM9-kxnkH8D7GPbqURGsGhthzL3WZ4Mi5VF82CfeMT9lOu7-sdlCczoai7BoMkR9epBnFPCs6KoFul8u4oDXGiJaN826kKMLfA";

    @Value("${local.server.port}")
    public int port;

    public MockMvc mvc;

    public ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUpGlobal() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        String password = "password";

        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
//				.addFilters(springSecurityFilterChain)
                .build();
//		try {
//			//temp to allow testing with rabbitmq configs
//			AMQPServerMock server = new AMQPServerMock(defaultConfig().withKnownHosts("local.vh"));
//			server.start();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
    }

    public void saveExport(byte[] fileContents, String downloadFileName) throws IOException {
        String downloadFolder = "src/test/resources/downloads";
        File outputPath = new File(downloadFolder);

        // create the folder structure if it does not exist
        outputPath.mkdirs();

        // For the purpose of the test, if the file already exists then I will delete it

        File checkDownloaded = new File(outputPath.getPath(), downloadFileName);
        if (checkDownloaded.exists()) {
            checkDownloaded.delete();
        }
        File outputFile = new File(outputPath.getPath(), downloadFileName);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        ;

        // output contents to file
        OutputStream outStream = null;

        try {
            outStream = new FileOutputStream(outputFile);
            outStream.write(fileContents);
        } catch (Exception e) {
            System.out.println("Error writing file " + outputFile.getAbsolutePath());
        } finally {
            if (outStream != null) {
                outStream.close();
            }
        }
    }
}
