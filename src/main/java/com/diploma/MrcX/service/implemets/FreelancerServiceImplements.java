package com.diploma.MrcX.service.implemets;

import com.diploma.MrcX.model.entity.Category;
import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.reposirtory.FreelancersRepository;
import com.diploma.MrcX.service.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerServiceImplements implements FreelancerService {

    private final FreelancersRepository freelancersRepository;
    @Override
    public boolean existById(String id) {
        return freelancersRepository.existsById(id);
    }

    @Override
    public void updateCatogoryByFreelancerId(String id, Category category) {
        freelancersRepository.updateCategoryById(category,id);
    }

    @Override
    public void updateDescription(String description, String id) {
        freelancersRepository.updateAboutMeById(description, id);
    }

    @Override
    public void save(Freelancers freelancer) {
        freelancer.setPriceInHour(0);
        freelancersRepository.save(freelancer);
    }

    @Override
    public Freelancers findById(String id) {
        return freelancersRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Freelancers> findAll() {
        return freelancersRepository.findAll();
    }
}
