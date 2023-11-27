package ru.practicum;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HitOutputDto {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private Long hits;
}