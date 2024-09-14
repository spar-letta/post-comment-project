package config.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class UserManagementServicesMock {
    public static void setupMockGetInstitution(WireMockServer mockService, String username) throws IOException {
        mockService.stubFor(WireMock.get(WireMock.urlEqualTo(String.format("/auth/internal/user?username=%s", username)))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                UserManagementServicesMock.class.getClassLoader().getResourceAsStream("client/response/fetchUserOk.json"),
                                defaultCharset()))));
    }
}
