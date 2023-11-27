package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.HitOutputDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Integer> {

    @Query(value = "SELECT new ru.practicum.HitOutputDto(a.name, r.uri, COUNT(r.ip)) " +
            "FROM Hit as r " +
            "LEFT JOIN App as a ON a.id = r.app.id " +
            "WHERE r.timestamp between ?1 AND ?2 " +
            "AND r.uri IN (?3) " +
            "GROUP BY a.name, r.uri " +
            "ORDER BY COUNT(r.ip) DESC ")
    List<HitOutputDto> getAllRequestsWithUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.HitOutputDto(a.name, r.uri, COUNT(DISTINCT r.ip)) " +
            "FROM Hit as r " +
            "LEFT JOIN App as a ON a.id = r.app.id " +
            "WHERE r.timestamp between ?1 AND ?2 " +
            "AND r.uri IN (?3) " +
            "GROUP BY a.name, r.uri " +
            "ORDER BY COUNT(DISTINCT r.ip) DESC ")
    List<HitOutputDto> getUniqueIpRequestsWithUri(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT new ru.practicum.HitOutputDto(a.name, r.uri, COUNT(DISTINCT r.ip)) " +
            "FROM Hit as r " +
            "LEFT JOIN App as a ON a.id = r.app.id " +
            "WHERE r.timestamp between ?1 AND ?2 " +
            "GROUP BY a.name, r.uri " +
            "ORDER BY COUNT(DISTINCT r.ip) DESC ")
    List<HitOutputDto> getUniqueIpRequestsWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.HitOutputDto(a.name, r.uri, COUNT(r.ip)) " +
            "FROM Hit as r " +
            "LEFT JOIN App as a ON a.id = r.app.id " +
            "WHERE r.timestamp between ?1 AND ?2 " +
            "GROUP BY a.name, r.uri " +
            "ORDER BY COUNT(r.ip) DESC ")
    List<HitOutputDto> getAllRequestsWithoutUri(LocalDateTime start, LocalDateTime end);
}
