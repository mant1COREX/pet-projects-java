package ru.ruslan.spring.SensorsService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.spring.SensorsService.dto.MeasurementDTO;
import ru.ruslan.spring.SensorsService.dto.MeasurementResponse;
import ru.ruslan.spring.SensorsService.dto.RainyDaysCountResponse;
import ru.ruslan.spring.SensorsService.models.Measurement;
import ru.ruslan.spring.SensorsService.service.MeasurementService;
import ru.ruslan.spring.SensorsService.util.SensorErrorResponse;
import ru.ruslan.spring.SensorsService.util.SensorNotFoundException;
import ru.ruslan.spring.SensorsService.util.ValidationErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestBody @Valid MeasurementDTO measurementDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
                    .toList();

            ValidationErrorResponse response = new ValidationErrorResponse(errors, System.currentTimeMillis());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        measurementService.addMeasurement(convertToMeasurement(measurementDTO));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public MeasurementResponse findAll() {
        return new MeasurementResponse(measurementService.findAll()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public RainyDaysCountResponse findRainyDays() {
        return new RainyDaysCountResponse(measurementService.findRainyDays());
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
