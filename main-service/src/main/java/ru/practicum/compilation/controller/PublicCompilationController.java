package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                                @Positive @RequestParam(defaultValue = "10") Integer size) {

        log.info("Calling GET: /compilations with 'pinned': {}, 'from': {}, 'size': {}", pinned, from, size);
        return ResponseEntity
                .status(HttpStatus.OK).body(compilationService.getAllCompilations(pinned, from, size));
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <CompilationDto> getCompilationById(@PathVariable Long compId) {

        log.info("[Public Compilation Controller] received a public request GET /compilations/{}", compId);
        return ResponseEntity
                .status(HttpStatus.OK).body(compilationService.getCompilation(compId));
    }
}