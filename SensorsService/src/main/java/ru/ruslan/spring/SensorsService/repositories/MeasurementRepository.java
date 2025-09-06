package ru.ruslan.spring.SensorsService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.spring.SensorsService.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Long countByIsRainingIsTrue();
}
