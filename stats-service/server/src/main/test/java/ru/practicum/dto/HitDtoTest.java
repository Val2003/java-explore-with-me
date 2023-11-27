package ru.practicum.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.HitDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JsonTest
public class HitDtoTest {

    @Autowired
    private JacksonTester<HitDto> jacksonTester;

    private HitDto hitDto;

    @BeforeEach
    void setup() {
        hitDto = new HitDto(1, "ewm-main-service", "/events/1", "192.163.0.1", LocalDateTime.now());
    }

    @Test
    void serialize() throws Exception {
        JsonContent<HitDto> hitDtoSaved = jacksonTester.write(hitDto);

        assertThat(hitDtoSaved).hasJsonPath("$.id");
        assertThat(hitDtoSaved).hasJsonPath("$.app");
        assertThat(hitDtoSaved).hasJsonPath("$.uri");
        assertThat(hitDtoSaved).hasJsonPath("$.ip");
        assertThat(hitDtoSaved).hasJsonPath("$.timestamp");

        assertThat(hitDtoSaved).extractingJsonPathNumberValue("$.id").isEqualTo(hitDto.getId());
        assertThat(hitDtoSaved).extractingJsonPathStringValue("$.app").isEqualTo(hitDto.getApp());
        assertThat(hitDtoSaved).extractingJsonPathStringValue("$.uri").isEqualTo(hitDto.getUri());
        assertThat(hitDtoSaved).extractingJsonPathStringValue("$.ip").isEqualTo(hitDto.getIp());

        assertThat(hitDtoSaved).hasJsonPathValue("$.timestamp");
    }

    @Test
    void deserialize() throws Exception {
        String json = "{\"app\":\"main-service\"," +
                "\"uri\":\"/events/1\"," +
                "\"ip\":\"192.163.0.1\"}";

        HitDto hitDto = jacksonTester.parseObject(json);

        assertNotNull(hitDto);

        assertThat(hitDto.getApp()).isEqualTo(hitDto.getApp());
        assertThat(hitDto.getUri()).isEqualTo(hitDto.getUri());
        assertThat(hitDto.getIp()).isEqualTo(hitDto.getIp());
    }
}
