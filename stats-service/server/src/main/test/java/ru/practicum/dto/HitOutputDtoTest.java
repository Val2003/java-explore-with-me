package ru.practicum.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.HitOutputDto;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class HitOutputDtoTest {

    @Autowired
    private JacksonTester<HitOutputDto> jacksonTester;

    @Test
    void testSerialize() throws Exception {
        HitOutputDto hitOutputDto = new HitOutputDto("ewm-main-service", "/events/1", 2L);

        JsonContent<HitOutputDto> hitOutputDtoSaved = jacksonTester.write(hitOutputDto);

        assertThat(hitOutputDtoSaved).hasJsonPath("$.app");
        assertThat(hitOutputDtoSaved).hasJsonPath("$.uri");
        assertThat(hitOutputDtoSaved).hasJsonPath("$.hits");

        assertThat(hitOutputDtoSaved).extractingJsonPathStringValue("$.app").isEqualTo(hitOutputDto.getApp());
        assertThat(hitOutputDtoSaved).extractingJsonPathStringValue("$.uri").isEqualTo(hitOutputDto.getUri());
        assertThat(hitOutputDtoSaved).extractingJsonPathNumberValue("$.hits").isEqualTo(hitOutputDto.getHits().intValue());
    }
}
