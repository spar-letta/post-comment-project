package com.javenock.event_service.event;

import com.javenock.event_service.EventServiceApplicationTests;
import com.javenock.event_service.config.ReportProcessingConfig;
import com.javenock.event_service.domain.dataTypes.ExportType;
import com.javenock.event_service.domain.dto.events.posts.PostCommentEvent;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Slf4j
public class PostReportTests extends EventServiceApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void postReport() throws IOException {
        String payload = "{\"type\":\"postCreationEvent\",\"id\":\"692d3910-e7a9-4c2b-a362-aa8cf2f085ac\",\"eventTime\":\"2023-11-11T09:36:22.246\",\"subject\":{\"publicId\":\"bc8bbeda-8ff3-11ee-b9d1-0242ac120002\",\"title\":\"hotels\",\"createdByName\":\"peterKason\",\"description\":\"post created\",\"eventType\":\"post_created\"},\"actionedBy\":\"b6a40c0c-f9a2-494b-9241-35e842967dbd\",\"comment\":null}";

        PostCommentEvent postCommentEvent = objectMapper.readValue(new GenericMessage<>(payload).getPayload(), PostCommentEvent.class);
        rabbitTemplate.convertAndSend(ReportProcessingConfig.REPORT_DIRECT_EXCHANGE, ReportProcessingConfig.ROUTING_KEY_REPORT, postCommentEvent);

        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .get("/reportSummary")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1))
                .body("content[0].publicId", equalTo("bc8bbeda-8ff3-11ee-b9d1-0242ac120002"))
                .body("content[0].title", equalTo("hotels"))
                .body("content[0].createdByName", equalTo("peterKason"))
                .body("content[0].description", equalToIgnoringCase("post created"))
                .body("content[0].eventType", equalToIgnoringCase("post_created"));


        Response response = given()
                .auth().oauth2(accessToken)
                .queryParam("exportType", ExportType.PDF)
                .get("/reportSummary/export")
                .then()
                .statusCode(200)
                .contentType("application/pdf")
                .extract().response();

        byte[] fileContents = response.getBody().asByteArray();
        saveExport(fileContents, "post_report.pdf");
    }
}
