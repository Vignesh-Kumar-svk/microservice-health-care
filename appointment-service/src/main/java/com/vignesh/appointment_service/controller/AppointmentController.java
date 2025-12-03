package com.vignesh.appointment_service.controller;


import com.vignesh.appointment_service.dto.AppointmentRequestDto;
import com.vignesh.appointment_service.dto.AppointmentResponseDto;
import com.vignesh.appointment_service.service_impl.AppointmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
@Tag(name = "Appointment Service", description = "APIs related to appointment booking and cancellation")
public class AppointmentController {

    Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    AppointmentServiceImpl appointmentService;

    @Operation(
            summary = "Book an appointment",
            description = "Creates a new appointment booking and returns appointment details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment booked successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid booking request",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized (JWT token missing or invalid)"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/booking")
    public ResponseEntity<AppointmentResponseDto> createBooking(
            @Parameter(description = "Appointment booking payload", required = true)
            @RequestBody AppointmentRequestDto appointmentRequestDto){
        logger.info("HI Appointment Contoller -----> booking");
        System.out.println("HI appointemnt svc...");
        AppointmentResponseDto appointmentResponseDto = appointmentService.bookingAppointment(appointmentRequestDto);
        return ResponseEntity.ok().body(appointmentResponseDto);
    }

    @Operation(
            summary = "Cancel an appointment",
            description = "Cancels an existing booking and returns updated appointment status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment cancelled successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid cancellation request",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "Unauthorized (JWT token missing or invalid)"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/cancel")
    public ResponseEntity<AppointmentResponseDto> cancelAppointment(
            @Parameter(description = "Cancellation request payload", required = true)
            @RequestBody AppointmentRequestDto appointmentRequestDto){
        AppointmentResponseDto appointmentResponseDto = appointmentService.cancelAppointment(appointmentRequestDto);
        return ResponseEntity.ok().body(appointmentResponseDto);
    }
}
