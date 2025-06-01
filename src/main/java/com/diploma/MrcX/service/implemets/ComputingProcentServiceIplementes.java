package com.diploma.MrcX.service.implemets;

import com.diploma.MrcX.reposirtory.ComputingProcentRepository;
import com.diploma.MrcX.service.ComputingProcentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComputingProcentServiceIplementes implements ComputingProcentService {

    private final ComputingProcentRepository computingProcentRepository;

    @Override
    public double getTaxPercent() {
        return computingProcentRepository.findById("tax").orElseThrow().getPercent();
    }

    @Override
    public double myPercent() {
        return computingProcentRepository.findById("my").orElseThrow().getPercent();
    }
}
