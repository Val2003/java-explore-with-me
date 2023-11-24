package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.HitOutputDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void addHit(HitDto hitDto);

    List<HitOutputDto> getHitsWithViews(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
