package com.application.sberhomework13.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ChunkController {

    private final ObjectMapper objectMapper;

    public ChunkController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> getChunks() {
        List<User> userList = createUserList();

        List<String> jsonList = new ArrayList<>();

        for (User user : userList) {
            try {

                String userJson = objectMapper.writeValueAsString(user);

                JsonNode jsonNode = objectMapper.readTree(userJson);
                jsonNode.fieldNames().forEachRemaining(fieldName -> {
                    jsonList.add("{\"" + fieldName + "\":\"" + jsonNode.get(fieldName).asText() + "\"}");
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return Flux.fromIterable(jsonList)
                .delayElements(Duration.ofSeconds(5));
    }

    private List<User> createUserList() {
        return Arrays.asList(
                User.builder()
                        .surname("Сухоруков")
                        .name("Георгий")
                        .patronymic("Дмитриевич")
                        .birthday(LocalDate.of(1990, 8, 12))
                        .address("Москва - ул. Строителей - 25 - 12")
                        .weight(83.5)
                        .height(185.12)
                        .education("Среднее специальное")
                        .build(),
                User.builder()
                        .surname("Глебова")
                        .name("Ксения")
                        .patronymic("Семёновна")
                        .birthday(LocalDate.of(2000, 2, 10))
                        .address("Саратов - ул. Ленина - 78 - 120")
                        .weight(51.1)
                        .height(165.78)
                        .education("Высшее")
                        .build(),
                User.builder()
                        .surname("Семёнов")
                        .name("Павел")
                        .patronymic("Андреевич")
                        .birthday(LocalDate.of(1987, 12, 12))
                        .address("Санкт-Петербург - пр. Невский - 2/2 - 1")
                        .weight(95.64)
                        .height(189.11)
                        .education("Высшее")
                        .build()
        );
    }
}
