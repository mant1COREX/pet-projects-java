package ru.ruslan.spring.SensorsService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.spring.SensorsService.models.Measurement;
import ru.ruslan.spring.SensorsService.repositories.MeasurementRepository;
import ru.ruslan.spring.SensorsService.util.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Long findRainyDays() {
        return measurementRepository.countByIsRainingIsTrue();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) throws SensorNotFoundException {
        if (sensorService.findByName(measurement.getSensor().getName()).isEmpty()) {
            throw new SensorNotFoundException(
                    "A sensor with that name '" + measurement.getSensor().getName() + "' is not registered"
            );
        }

        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedAt(LocalDateTime.now());
    }
}
