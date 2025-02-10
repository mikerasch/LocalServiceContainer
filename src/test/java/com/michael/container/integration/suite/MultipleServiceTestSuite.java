package com.michael.container.integration.suite;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({@ConfigureWireMock(name = "first-service"),
        @ConfigureWireMock(name = "second-service")})
public class MultipleServiceTestSuite {
    @InjectWireMock("first-service")
    WireMockServer firstServer;

    @InjectWireMock("second-service")
    WireMockServer secondServer;
}
