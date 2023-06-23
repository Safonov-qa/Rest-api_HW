package tests;

import io.qameta.allure.Owner;
import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.LoginSpec.*;

public class RestApiHw19Test {

    @Test
    @Owner("Safonov Ilya")
    @DisplayName("Проверка информации по одному пользователя")
    void singleUserTest() {
        UserData response = step("Check one user information", () ->
                given(request)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(ResponseOkSpec)
                        .extract().as(UserData.class));
        step("Verify expected email", () ->
                assertThat(response.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"));

    }

    @Test
    @Owner("Safonov Ilya")
    @DisplayName("Проверка запроса на создание пользователя")
    void successfulCreateTest() {
        User user = new User();
        user.setName("morpheus");
        user.setJob("leader");

        ResponseCreate response = step("Make request for create user", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseCreated)
                        .extract().as(ResponseCreate.class));

        step("Verify expected name", () ->
                assertThat(response.getName()).isEqualTo("morpheus"));
        step("Verify expected job", () ->
                assertThat(response.getJob()).isEqualTo("leader"));

    }

    @Test
    @Owner("Safonov Ilya")
    @DisplayName("Проверка запроса на успешное создание пользователя")
    void successfulRegisterTest() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");

        ResponseRegister response = step("Make request for create user", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().as(ResponseRegister.class));

        step("Verify expected id", () ->
                assertThat(response.getId()).isEqualTo(4));
    }

    @Test
    @Owner("Safonov Ilya")
    @DisplayName("Проверка запроса на отсутвие пароля создание пользователя")
    void unsuccessfulRegisterTest() {
        User user = new User();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("");

        ErrorResponse response = step("Make request for create user", () ->
                given(request)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseFailed)
                        .extract().as(ErrorResponse.class));

        step("Verify error", () ->
                assertThat(response.getError()).isEqualTo("Missing password"));
    }

    @Test
    @Owner("Safonov Ilya")
    @DisplayName("Проверка запроса на удаление пользователя")
    void deleteTest() {
        step("Make request for delete user", () ->
                given(request)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(responseDelete));

    }
}
