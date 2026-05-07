package com.Prog3.agroptima.service;

import com.Prog3.agroptima.entity.dto.CollectivityLocalStatistics;
import com.Prog3.agroptima.entity.dto.CollectivityOverallStatistics;
import com.Prog3.agroptima.exception.BadRequestException;
import com.Prog3.agroptima.exception.NotFoundException;
import com.Prog3.agroptima.repository.CollectivityRepository;
import com.Prog3.agroptima.repository.StatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final CollectivityRepository collectivityRepository;

   
    public List<CollectivityLocalStatistics> getLocalStatistics(String collectivityId, Instant from, Instant to) {
        if (collectivityId == null || collectivityId.trim().isEmpty()) {
            throw new BadRequestException("Collectivity ID is required");
        }

        if (from == null || to == null) {
            throw new BadRequestException("Both 'from' and 'to' dates are required");
        }

        if (from.isAfter(to)) {
            throw new BadRequestException("'from' date must be before or equal to 'to' date");
        }

        
        if (collectivityRepository.findById(collectivityId) == null) {
            throw new NotFoundException("Collectivity not found with id: " + collectivityId);
        }

        return statisticsRepository.getLocalStatistics(collectivityId, from, to);
    }

   
    public List<CollectivityOverallStatistics> getOverallStatistics(Instant from, Instant to) {
        if (from == null || to == null) {
            throw new BadRequestException("Both 'from' and 'to' dates are required");
        }

        if (from.isAfter(to)) {
            throw new BadRequestException("'from' date must be before or equal to 'to' date");
        }

        return statisticsRepository.getOverallStatistics(from, to);
    }
}