package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.lombok.UserData;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class RestApiReqresInExtendedTest {
    @Test
    void successfulLoginWithPojoTest() {
      //  String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        LoginBodyPojoModel loginBody = new LoginBodyPojoModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponsePojoModel response = given()
                .log().uri()
                .body(loginBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);
      //  assertEquals("QpwL5tke4Pnpja7X4",response.getToken());
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }
@Test
    void successfulLoginWithLombokTest() {

        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .log().uri()
                .body(loginBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
      //  assertEquals("QpwL5tke4Pnpja7X4",response.getToken());
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }
@Test
    void successfulLoginWithAllureTest() {

        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(new AllureRestAssured())
                .log().uri()
                .body(loginBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

@Test
    void successfulLoginWithCustomAllureTest() {

        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .body(loginBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

@Test
    void successfulLoginWithAllureStepsTest() {
    step("Prepare testdata");

    LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

    LoginResponseLombokModel response = step("Make request",() ->
                given()
                .filter(withCustomTemplates())
                .log().uri()
                .body(loginBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class));

    step("Verify response",() ->
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulLoginWithAllureSpecsTest() {
    step("Prepare testdata");

    LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

    LoginResponseLombokModel response = step("Make request",() ->
            given(loginRequestSpec)
                    .body(loginBody)
                    .when()
                    .post()
                    .then()
                    .spec(loginResponseSpec)
                .extract().as(LoginResponseLombokModel.class));

    step("Verify response",() ->
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4"));
    }
}