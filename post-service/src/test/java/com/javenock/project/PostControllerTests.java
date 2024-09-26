package com.javenock.project;

import com.javenock.project.dto.PostDto;
import config.mock.UserManagementServicesMock;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostControllerTests extends ProjectApplicationTests {

    @Test
    public void testCreatePostWorks() throws IOException {
        UserManagementServicesMock.setupMockGetInstitution(wireMockRuleUserManagementService, "testuser");
        PostDto postDto = new PostDto();
        postDto.setTitle("hotels");
        postDto.setContent("Hotels in Mewa Town are 5 in number.");

        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(postDto).log().all()
                .post("/post")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testFetchAllPostsWorks() throws IOException {
        given()
                .contentType("application/json")
                .auth()
                .oauth2(accessToken)
                .get("/post")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1));
    }

    @Test
    public void testGetPostFails() throws IOException {
        given()
                .contentType("application/json")
                .auth()
                .oauth2(accessToken)
                .get("/post/{publicId}", "ab874ce2-dc46-4f11-8915-c1d644f236dd")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    public void testGetPostWorks() throws IOException {
        given()
                .contentType("application/json")
                .auth()
                .oauth2(accessToken)
                .get("/post/{publicId}", "ab874ce2-dc46-4f11-8915-c1d644f236da")
                .then().log().all()
                .statusCode(200)
                .body("createdBy.publicId", notNullValue())
                .body("createdBy.firstName", notNullValue())
                .body("title", equalTo("post 1"))
                .body("content", notNullValue());
    }

    @Test
    public void testUpdatePostWorks() throws IOException {
        PostDto postDto = new PostDto();
        postDto.setTitle("hotels");
        postDto.setContent("5 star hotel is the leading in kenya");
        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(postDto).log().all()
                .patch("/post/{publicId}", UUID.fromString("ab874ce2-dc46-4f11-8915-c1d644f236da"))
                .then().log().all()
                .statusCode(200)
                .body("title", equalTo("hotels"))
                .body("content", equalTo(postDto.getContent()))
                .body("modifiedBy", notNullValue());
    }

}
