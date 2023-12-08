package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity <CompilationDto> addCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {

        log.info("Calling POST: /admin/compilations with 'compilationDto': {}", compilationDto);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(compilationService.addCompilation(compilationDto));
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable Long compId,
                                            @RequestBody @Valid UpdateCompilationRequest compRequest) {

        log.info("Calling PATCH: /admin/compilations/{compId} with 'compId': {}", compId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(compilationService.updateCompilation(compId, compRequest));
    }

    @DeleteMapping("/{compId}")
    public  ResponseEntity<HttpStatus> deleteCompilation(@PathVariable Long compId) {

        log.info("Calling DELETE: /admin/compilations/{compId} with 'compId': {}", compId);
        compilationService.deleteCompilation(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}