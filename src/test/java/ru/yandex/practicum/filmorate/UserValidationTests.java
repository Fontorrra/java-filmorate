package ru.yandex.practicum.filmorate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@AutoConfigureMockMvc
public class UserValidationTests {

	@Autowired
	private MockMvc mockMvc;

	Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
					new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
			.create();

	User user;

	private ResultActions getResultActions(User user) throws Exception {
		return mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(user)));
	}


	@BeforeEach
	public void beforeEach() {
		user = User.builder()
				.name("Daniil")
				.login("Fontorra")
				.email("email@yandex.ru")
				.birthday(LocalDate.of(2003, 2, 28))
				.build();
	}

	@Test
	public void testValidUser() throws Exception {

		getResultActions(user).andExpectAll(
				status().isOk(),
				jsonPath("$.name").value("Daniil"),
				jsonPath("$.email").value("email@yandex.ru"),
				jsonPath("$.login").value("Fontorra"),
				jsonPath("$.birthday").value("2003-02-28"));

		user.setName(null);
		getResultActions(user).andExpectAll(
				status().isOk(),
				jsonPath("$.name").value("Fontorra"),
				jsonPath("$.email").value("email@yandex.ru"),
				jsonPath("$.login").value("Fontorra"),
				jsonPath("$.birthday").value("2003-02-28"));
	}

	@Test
	public void testInvalidUserWithIncorrectLogin() throws Exception {
		user.setLogin(null);
		getResultActions(user).andExpect(status().isBadRequest());
		user.setLogin("  ");
		getResultActions(user).andExpect(status().isBadRequest());
	}

	@Test
	public void testInvalidUserWithIncorrectBirthday() throws Exception {
		user.setBirthday(LocalDate.of(3000, 1,1));
		getResultActions(user).andExpect(status().isBadRequest());
	}

	@Test
	public void testInvalidUserWithIncorrectEmail() throws Exception {
		user.setEmail("qwe");
		getResultActions(user).andExpect(status().isBadRequest());
		user.setEmail("qwe@");
		getResultActions(user).andExpect(status().isBadRequest());
		user.setEmail("@qwe");
		getResultActions(user).andExpect(status().isBadRequest());
	}
//
}
