package com.ptpro.controller;


import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.request.UpdateSessionRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.service.SessionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService){
        this.sessionService = sessionService;
    }

    //FE-10
    @PostMapping("/{id}")
            public ResponseEntity<SessionResponse>createSession(@PathVariable Long id, @RequestBody CreateSessionsRequest createSessionsRequest){
            return ResponseEntity.ok(sessionService.createSession(id, createSessionsRequest));
    }

    //FE-13
    @GetMapping("/all/{id}")
    public ResponseEntity<List<SessionResponse>>getAll(@PathVariable Long id){
        return ResponseEntity.ok(sessionService.getAll(id));
    }

    //FE-12
    @GetMapping("/all/available/{id}")
    public ResponseEntity<List<SessionResponse>>getAllAvailable(@PathVariable Long id){
        return ResponseEntity.ok(sessionService.getAllAvailable(id));
    }

    //FE-11
    @PutMapping("/{id}/available")
    public ResponseEntity<SessionResponse> setAvailable(@PathVariable Long id,
                                                        @RequestBody UpdateSessionRequest request) {
        return ResponseEntity.ok(sessionService.updateSession(id, request));
    }

}
