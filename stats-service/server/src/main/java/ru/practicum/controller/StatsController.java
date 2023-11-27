package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.HitOutputDto;
import ru.practicum.service.StatsServiceImpl;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StatsController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatsServiceImpl statsService;

    @PostMapping("/hit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addRequest(@Valid @RequestBody HitDto hitDto) {
        statsService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<HitOutputDto>> getStats(@RequestParam String start,
                                                       @RequestParam String end,
                                                       @RequestParam(required = false) List<String> uris,
                                                       @RequestParam(defaultValue = "false") Boolean unique) {

        LocalDateTime startDT;
        LocalDateTime endDT;
        try {
            startDT = LocalDateTime.parse(start, DATE_TIME_FORMATTER);
            endDT = LocalDateTime.parse(end, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        List<HitOutputDto> results = statsService.getHitsWithViews(startDT, endDT, uris, unique);
        return ResponseEntity.ok().body(results);
    }
}
