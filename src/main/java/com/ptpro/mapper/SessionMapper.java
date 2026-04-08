package com.ptpro.mapper;

import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.request.UpdateSessionRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.model.Session;
import com.ptpro.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    //CREATE
    Session toEntity(CreateSessionsRequest dto);
    //UPDATE
    void updateEntity(@MappingTarget Session entity, UpdateSessionRequest dto);
    //RESPONSE
    SessionResponse toResponse(Session entity);
}
