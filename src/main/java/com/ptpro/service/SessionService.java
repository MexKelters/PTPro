package com.ptpro.service;

import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.request.UpdateSessionRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Trainer niet gevonden met id: " + id))
                .getId();

        Session newSession = sessionMapper.toEntity(createSessionsRequest);
        newSession.setTrainer(trainerRepository.getReferenceById(idTrainer));

        Session savedSession = sessionRepository.save(newSession);
        return sessionMapper.toResponse(savedSession);
    }

    public List<SessionResponse> getAll(Long id) {
        List<Session> sessions = sessionRepository.findByTrainerId(id);
        if (sessions.isEmpty()){
            throw new ResourceNotFoundException("Geen sessies gevonden voor trainer met id" + id);
        }
        List<SessionResponse> dtos = new ArrayList<>();
        for(Session session : sessions){
            dtos.add(sessionMapper.toResponse(session));
        }
        return dtos;
    }


    public List<SessionResponse> getAllAvailable(Long id) {
        List<Session> sessions = sessionRepository.getAllAvailable(id);
        if (sessions.isEmpty()){
            throw new ResourceNotFoundException("Geen sessies gevonden voor trainer met id " + id);
        }
        List<SessionResponse> dtos = new ArrayList<>();
        for(Session session : sessions){
            dtos.add(sessionMapper.toResponse(session));
        }
        return dtos;
    }

    public SessionResponse updateSession(Long id, UpdateSessionRequest request) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessie niet gevonden met id: " + id));
        sessionMapper.updateEntity(session, request);
        return sessionMapper.toResponse(sessionRepository.save(session));
    }
}
