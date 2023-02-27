package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.fail;
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
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmValidationTests {

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .create();

    Film film;

    private ResultActions getResultActions(Film film) throws Exception {
        return mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(film)));
    }


    @BeforeEach
    public void beforeEach() {
        film = Film.builder()
                .name("Film name")
                .description("Film description")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(100)
                .build();
    }

    @Test
    public void testValidFilm() throws Exception {

        getResultActions(film).andExpectAll(
                status().isOk(),
                jsonPath("$.name").value("Film name"),
                jsonPath("$.description").value("Film description"),
                jsonPath("$.releaseDate").value("2000-01-01"),
                jsonPath("$.duration").value(100));
    }

    @Test
    public void testInvalidFilmWithEmptyName() throws Exception {
        film.setName(null);
        getResultActions(film).andExpect(status().isBadRequest());
        film.setName("  ");
        getResultActions(film).andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmWithIncorrectReleaseDate() throws Exception {
        film.setReleaseDate(LocalDate.of(1890, 1,1));
        getResultActions(film).andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmWithIncorrectDuration() throws Exception {
        film.setDuration(0);
        getResultActions(film).andExpect(status().isBadRequest());
        film.setDuration(-200);
        getResultActions(film).andExpect(status().isBadRequest());
    }

    @Test
    public void testInvalidFilmWithIncorrectDescription() throws Exception {
        String str = "Здесь 20 символов...";
        str = str.repeat(10);
        str = str + "q";
        film.setDescription(str);
        getResultActions(film).andExpect(status().isBadRequest());
    }

    @Test
    public void testFilmCorrectWhen200SymbolsInDescription() throws Exception {
        String str = "Здесь 20 символов...";
        str = str.repeat(10);
        film.setDescription(str);
        getResultActions(film).andExpect(status().isOk());
    }

/*
	@Test
	public void testInvalidUser() throws Exception {
		User user = new User("", "", "notanemail");
		BindingResult result = validate(user);
		mockMvc.perform(post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(user.toJson()))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors[0].field").value("firstName"))
				.andExpect(jsonPath("$.errors[0].message").value(result.getFieldError("firstName").getDefaultMessage()))
				.andExpect(jsonPath("$.errors[1].field").value("lastName"))
				.andExpect(jsonPath("$.errors[1].message").value(result.getFieldError("lastName").getDefaultMessage()))
				.andExpect(jsonPath("$.errors[2].field").value("email"))
				.andExpect(jsonPath("$.errors[2].message").value(result.getFieldError("email").getDefaultMessage()));
	}
*/
}
