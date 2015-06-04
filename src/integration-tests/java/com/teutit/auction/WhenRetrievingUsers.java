package com.teutit.auction;

import com.teutit.auction.repository.UserRepository;
import com.teutit.auction.utils.UserUtils;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({"server.port=2605"})
public class WhenRetrievingUsers {
    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate restTemplate;

    @Inject
    private UserRepository userRepository;

    @Before
    public void onSetup() throws MalformedURLException {
        base = new URL("http://localhost:"+port+"/user");
        restTemplate = new RestTemplate();
        userRepository.save(UserUtils.createUserList(3));

    }

    @Test
    public void givenRepositoryWithThreeUsers_thenItShouldReturnThem(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(base.toString(), String.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        //assertThat(responseEntity.getBody(), Matcher<String >); TO DO: check list length
        assertTheResponseHasThreeUsers(responseEntity.getBody());
        get("http://localhost:2605/user").then().assertThat().body("id", hasItems("id0", "id1"));
//        given().param("", "")
    }

    private void assertTheResponseHasThreeUsers(String responseBody) {
        for(int i=0;i<3;i++){
            assertThat(responseBody, containsString("id"+i));
            assertThat(responseBody, containsString("password"+i));
        }
    }
}
