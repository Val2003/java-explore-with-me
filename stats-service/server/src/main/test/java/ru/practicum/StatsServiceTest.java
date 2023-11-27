package ru.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.model.App;
import ru.practicum.model.Hit;
import ru.practicum.repository.AppRepository;
import ru.practicum.repository.HitRepository;
import ru.practicum.service.StatsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"db.name=test"})
public class StatsServiceTest {

    HitDto hitDto;
    HitOutputDto hitOutputDto;
    Hit hit;


    App app;
    @InjectMocks
    private StatsServiceImpl statsService;
    @Mock
    private HitRepository hitRepository;
    @Mock
    private AppRepository appRepository;

    @BeforeEach
    void setup() {
        hit = new Hit(1, new App("main-service"), "/events/1", "192.163.0.1", LocalDateTime.now());
        hitDto = new HitDto(1, "main-service", "/events/1", "192.163.0.1", LocalDateTime.now());
        hitOutputDto = new HitOutputDto("main-service", "/events/1", 1L);
        app = new App(hitDto.getApp());
    }

    @Test
    void addRequest() {
        Mockito.when(appRepository.findByName(hitDto.getApp()))
                .thenReturn(Optional.ofNullable(app));

        Mockito.when(hitRepository.save(any()))
                .thenReturn(hit);

        statsService.addHit(hitDto);

        verify(hitRepository, atMostOnce()).saveAndFlush(any());
    }

    @Test
    void getRequestsWithViews_WhenUnique() {
        Mockito.when(hitRepository.getUniqueIpRequestsWithUri(any(), any(), any()))
                .thenReturn(List.of(hitOutputDto));

        List<HitOutputDto> hitOutputDtoSaved = statsService.getHitsWithViews(LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1), List.of("/events/1"), true);

        assertAll(
                () -> assertEquals(hitOutputDtoSaved.size(), 1),
                () -> assertEquals(hitOutputDtoSaved.get(0).getUri(), hit.getUri()),
                () -> assertEquals(hitOutputDtoSaved.get(0).getApp(), app.getName()),
                () -> assertEquals(hitOutputDtoSaved.get(0).getHits(), 1L)
        );

        verify(hitRepository, atMostOnce()).saveAndFlush(any());
    }

    @Test
    void getRequestsWithViews_WhenNotUnique() {
        Mockito.when(hitRepository.getAllRequestsWithUri(any(), any(), any()))
                .thenReturn(List.of(hitOutputDto));

        List<HitOutputDto> hitOutputDtoSaved = statsService.getHitsWithViews(LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1), List.of("/events/1"), false);

        assertAll(
                () -> assertEquals(hitOutputDtoSaved.size(), 1),
                () -> assertEquals(hitOutputDtoSaved.get(0).getUri(), hit.getUri()),
                () -> assertEquals(hitOutputDtoSaved.get(0).getApp(), app.getName()),
                () -> assertEquals(hitOutputDtoSaved.get(0).getHits(), 1L)
        );

        verify(hitRepository, atMostOnce()).saveAndFlush(any());
    }
}