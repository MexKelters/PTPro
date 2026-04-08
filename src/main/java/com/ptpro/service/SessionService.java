package com.ptpro.service;

import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.mapper.SessionMapper;
import com.ptpro.model.Session;
import com.ptpro.repository.SessionRepository;
import com.ptpro.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TrainerRepository trainerRepository;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, TrainerRepository trainerRepository, SessionMapper sessionMapper){
        this.sessionRepository = sessionRepository;
        this.trainerRepository = trainerRepository;
        this.sessionMapper = sessionMapper;
    }

    public SessionResponse createSession(Long id, CreateSessionsRequest createSessionsRequest) {
        Long idTrainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer niet gevonden met id: " + id))
                .getId();

        Session newSession = sessionMapper.toEntity(createSessionsRequest);
        newSession.setTrainer(trainerRepository.getReferenceById(idTrainer));

        Session savedSession = sessionRepository.save(newSession);
        return sessionMapper.toResponse(savedSession);
    }
}
