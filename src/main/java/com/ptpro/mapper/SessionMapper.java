package com.ptpro.mapper;

import com.ptpro.dto.request.CreateSessionsRequest;
import com.ptpro.dto.request.UpdateSessionRequest;
import com.ptpro.dto.response.SessionResponse;
import com.ptpro.model.Session;
import com.ptpro.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    //CREATE
    Session toEntity(CreateSessionsRequest dto);
    //UPDATE
    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "date", ignore = true)
    void updateEntity(@MappingTarget Session entity, UpdateSessionRequest dto);
    //RESPONSE
    @Mapping(source = "trainer.id", target = "trainerId")
    SessionResponse toResponse(Session entity);
}
