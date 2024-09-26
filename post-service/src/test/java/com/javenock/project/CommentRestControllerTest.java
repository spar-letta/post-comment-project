package com.javenock.project;

import com.javenock.project.dto.CommentDto;
import com.javenock.project.model.dataType.CommentType;
import io.restassured.http.ContentType;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class CommentRestControllerTest extends ProjectApplicationTests {

    @Test
    public void testCreateCommentWorks() throws Exception {
        CommentDto comment = new CommentDto();
        comment.setCommentBody("comment about hetel 5 star");
        comment.setPostId(UUID.fromString("ab874ce2-dc46-4f11-8915-c1d644f236da"));

        String publicId = given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(comment).log().all()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract().path("publicId");

        // create second first comment
        comment.setPostId(UUID.fromString("ab874ce2-dc46-4f11-8915-c1d644f236da"));
        comment.setCommentBody("Am a child comment");
        comment.setCommentId(UUID.fromString(publicId));
        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(comment)
                .post("/comments")
                .then().log().all()
                .statusCode(200);

        // create second child comment
        comment.setPostId(UUID.fromString("ab874ce2-dc46-4f11-8915-c1d644f236da"));
        comment.setCommentBody("Am a child comment 2");
        comment.setCommentId(UUID.fromString(publicId));
        given()
                .contentType(ContentType.JSON)
                .auth()
                .oauth2(accessToken)
                .body(comment)
                .post("/comments")
                .then().log().all()
                .statusCode(200);

        given()
                .auth()
                .oauth2(accessToken)
                .get("/comments/{publicId}", publicId)
                .then().log().all()
                .statusCode(200);

        given()
                .auth()
                .oauth2(accessToken)
                .queryParam("type", CommentType.PARENT)
                .get("/comments")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1))
                .body("content[0].commentBody", equalTo("comment about hetel 5 star"));

    }
}
