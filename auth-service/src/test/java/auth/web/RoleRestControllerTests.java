package auth.web;

import auth.AuthApplicationTests;
import auth.dto.request.RolePrivilegeDto;
import auth.entity.Role;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class RoleRestControllerTests extends AuthApplicationTests {

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN_TEST");
        role.setDescription("system administrator");

        String token = getToken();
        given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(role).log().all()
                .post("/auth/roles")
                .then().log().all()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token)
                .get("/auth/roles")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testGetRoleWorks() {
        String token = getToken();

        given()
                .header("Authorization", "Bearer " + token).log().all()
                .get("/auth/roles")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testAddPrivilegeToRole() {
        RolePrivilegeDto rolePrivilegeDto = new RolePrivilegeDto();
        rolePrivilegeDto.setPrivilegeUUIDs(Arrays.asList(UUID.fromString("db874ce2-dc46-4f11-8915-c1d644f236d1")));

        given()
                .header("Authorization", "Bearer " + getToken())
                .contentType("application/json")
                .body(rolePrivilegeDto)
                .patch("/auth/roles/assignPrivilegeToRole/{rolePublicId}", "fb874ce2-dc46-4f11-8915-c1d644f236dd")
                .then().log().all()
                .statusCode(200);

    }
}
