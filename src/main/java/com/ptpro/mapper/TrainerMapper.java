package com.ptpro.mapper;

import com.ptpro.dto.request.CreateTrainerRequest;
import com.ptpro.dto.request.CreateUserRequest;
import com.ptpro.dto.request.UpdateTrainerRequest;
import com.ptpro.dto.response.TrainerResponse;
import com.ptpro.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrainerMapper{
    //CREATE
    Trainer toEntity(CreateTrainerRequest dto);
    //UPDATE
    void updateEntity(@MappingTarget Trainer entity, UpdateTrainerRequest dto);
    //RESPONSE
    TrainerResponse toResponse(Trainer entity);

}