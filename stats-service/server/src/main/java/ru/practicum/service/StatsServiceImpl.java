package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.HitDto;

import ru.practicum.HitOutputDto;
import ru.practicum.model.HitMapper;
import ru.practicum.model.App;
import ru.practicum.model.Hit;
import ru.practicum.repository.AppRepository;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HitRepository hitRepository;
    private final AppRepository appRepository;


    public void addHit(HitDto hitDto) {
        Optional<App> optionalApp = appRepository.findByName(hitDto.getApp());

        App app = optionalApp.orElseGet(() -> appRepository.save(new App(hitDto.getApp())));


        Hit hit = HitMapper.INSTANCE.toHit(hitDto);
        hit.setApp(app);
        hitRepository.save(hit);

    }

    public List<HitOutputDto> getHitsWithViews(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {

        if (unique) {
            if (uris == null || uris.isEmpty()) {
                return hitRepository.getUniqueIpRequestsWithoutUri(start, end);
            }
            return hitRepository.getUniqueIpRequestsWithUri(start, end, uris);
        } else {
            if (uris == null || uris.isEmpty()) {
                return hitRepository.getAllRequestsWithoutUri(start, end);
            }
            return hitRepository.getAllRequestsWithUri(start, end, uris);
        }
    }
}
