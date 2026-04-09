package com.ptpro.controller;


import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.service.SessionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    //Deze is voor het maken van een session FE-10
    @PostMapping("/{id}")
            public ResponseEntity<SessionResponse>createSession(@PathVariable Long id, @RequestBody CreateSessionsRequest createSessionsRequest){
            return ResponseEntity.ok(sessionService.createSession(id, createSessionsRequest));
    }

    //Deze is voor het ophalen van een session van 1 bepaalde trainer FE-13
    @GetMapping("/all/{id}")
    public ResponseEntity<List<SessionResponse>>getAll(@PathVariable Long id){
        return ResponseEntity.ok(sessionService.getAll(id));
    }

    //FE-12
    @GetMapping("/all/available/{id}")
    public ResponseEntity<List<SessionResponse>>getAllAvailable(@PathVariable Long id){
        return ResponseEntity.ok(sessionService.getAllAvailable(id));
    }

    //FE-14 & FE-15

}
