package com.teutit.auction;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.teutit.auction.domain.User;
import com.teutit.auction.repository.UserRepository;
import com.teutit.auction.utils.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.net.MalformedURLException;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class WhenRetrievingUsers {
    public static final String USERS_RESOURCE = "/user";
    public static final String FIRST_ID = "id0";
    public static final String FIRST_PASSWORD = "password0";
    public static final String SECOND_ID = "id1";
    public static final String SECOND_PASSWORD = "password1";
    public static final String ID_FIELD = "id";
    public static final String PASSWORD_FIELD = "password";
    public static final User FIRST_USER = new UserBuilder(FIRST_ID).password(FIRST_PASSWORD).build();
    public static final User SECOND_USER = new UserBuilder(SECOND_ID).password(SECOND_PASSWORD).build();

    @Value("${local.server.port}")
    private int serverPort;

    @Inject
    private UserRepository userRepository;

    @Before
    public void onSetup() throws MalformedURLException {
        RestAssured.port = serverPort;
        userRepository.save(FIRST_USER);
        userRepository.save(SECOND_USER);

    }
//    <activeProfiles>
//    <!--make the profile active all the time -->
//    <activeProfile>nexus</activeProfile>
//    </activeProfiles>
//    <profile>
//    <id>integration</id>
//    <properties>
//    <unit-tests.skip>false</unit-tests.skip>
//    <integration-tests.skip>false</integration-tests.skip>
//    </properties>
//    </profile>

    @Test
    public void givenRepositoryWithThreeUsers_thenItShouldReturnThem(){
        Response response = get(USERS_RESOURCE);
        when()
                .get(USERS_RESOURCE)
        .then()
                .assertThat().body(ID_FIELD, hasItems(FIRST_ID, SECOND_ID))
                .assertThat().body(PASSWORD_FIELD, hasItems(FIRST_PASSWORD, SECOND_PASSWORD));
    }

}
