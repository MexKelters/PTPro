package com.ptpro.mapper;

import com.ptpro.dto.request.CreateTrainingScheduleRequest;
import com.ptpro.dto.request.UpdateTrainingScheduleRequest;
import com.ptpro.dto.response.TrainingScheduleResponse;
import com.ptpro.model.TrainingSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrainingScheduleMapper {
    //CREATE
    TrainingSchedule toEntity(CreateTrainingScheduleRequest dto);
    //UPDATE
    void updateEntity(@MappingTarget TrainingSchedule entity, UpdateTrainingScheduleRequest dto);
    //RESPONSE
    @Mapping(source = "trainer.id", target = "trainerId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "createAt", target = "createdAt")
    TrainingScheduleResponse toResponse(TrainingSchedule entity);
}