package ru.ruslan.spring.SensorsService.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.spring.SensorsService.dto.SensorDTO;
import ru.ruslan.spring.SensorsService.models.Sensor;
import ru.ruslan.spring.SensorsService.service.SensorService;
import ru.ruslan.spring.SensorsService.util.SensorAlreadyExistsException;
import ru.ruslan.spring.SensorsService.util.SensorErrorResponse;
import ru.ruslan.spring.SensorsService.util.ValidationErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(
            @RequestBody @Valid SensorDTO sensorDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            List<String> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " - " + error.getDefaultMessage())
                    .toList();

            ValidationErrorResponse response = new ValidationErrorResponse(errors, System.currentTimeMillis());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        sensorService.create(convertToSensor(sensorDTO));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorAlreadyExistsException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
