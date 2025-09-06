package ru.ruslan.spring.SensorsService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.spring.SensorsService.models.Sensor;
import ru.ruslan.spring.SensorsService.repositories.SensorRepository;
import ru.ruslan.spring.SensorsService.util.SensorAlreadyExistsException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void create(Sensor sensor) {
        if (sensorRepository.findByName(sensor.getName()).isPresent()) {
            throw new SensorAlreadyExistsException(
                    "Sensor with name '" + sensor.getName() + "' already exists"
            );
        }

        sensorRepository.save(sensor);
    }
}
