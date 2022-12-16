package com.salesianostriana.kilo.controllers;

import com.salesianostriana.kilo.dtos.RankingResponseDTO;
import com.salesianostriana.kilo.services.ClaseService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final ClaseService claseService;

    @GetMapping("/")
    public ResponseEntity<List<RankingResponseDTO>> getRanking(){
        return ResponseEntity.ok(claseService.getRanking());
    }

}
