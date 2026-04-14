package com.ptpro.mapper;

import com.ptpro.dto.request.CreateBookingRequest;
import com.ptpro.dto.request.UpdateBookingRequest;
import com.ptpro.dto.response.BookingResponse;
import com.ptpro.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    //CREATE
    Booking toEntity(CreateBookingRequest dto);
    //UPDATE
    void updateEntity(@MappingTarget Booking entity, UpdateBookingRequest dto);
    //RESPONSE
    BookingResponse toResponse(Booking entity);
}