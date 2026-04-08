package com.ptpro.controller;


import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    //Deze is voor het maken van een session
    @PostMapping("/{id}")
            public ResponseEntity<SessionResponse>createSession(@PathVariable Long id, @RequestBody CreateSessionsRequest createSessionsRequest){
            return ResponseEntity.ok(sessionService.createSession(id, createSessionsRequest));
    }

    //Deze is voor het ophalen van alle sessions


    //Deze is voor het ophalen van een session van 1 bepaalde trainer
}
