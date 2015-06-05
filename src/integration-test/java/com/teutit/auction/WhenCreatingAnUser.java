package com.teutit.auction;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.teutit.auction.domain.User;
import com.teutit.auction.repository.UserRepository;
import com.teutit.auction.utils.UserBuilder;
import com.teutit.auction.utils.UserUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.mail.FetchProfile;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.post;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class WhenCreatingAnUser {
    public static final String USERS_RESOURCE = "/user";
    public static final String ID = "id";
    public static final String PASSWORD = "password";
    public static final String ID_FIELD = "id";
    public static final String PASSWORD_FIELD = "password";
    public static final User USER = new UserBuilder(ID).password(PASSWORD).build();
    @Value("${local.server.port}")
    private int serverPort;

    @Inject
    private UserRepository userRepository;

    @Before
    public void onSetup() throws MalformedURLException {
        RestAssured.port = serverPort;
//        userRepository.save(UserUtils.createUserList(3));

    }
//    <activeProfiles>
//    <!--make the profile active all the time -->
//    <activeProfile>nexus</activeProfile>
//    </activeProfiles>

    @Test
    public void givenCorrectPayload_thenItShouldCreateUser(){
        given()
                .body(USER)
                .contentType(ContentType.JSON)
        .when()
                .post(USERS_RESOURCE)
        .then()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .assertThat().body(ID_FIELD, is(ID))
                .assertThat().body(PASSWORD_FIELD, is(PASSWORD));
    }

}
