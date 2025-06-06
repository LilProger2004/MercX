package com.diploma.MrcX.service.implemets;

import com.diploma.MrcX.model.entity.Freelancers;
import com.diploma.MrcX.model.entity.Offers;
import com.diploma.MrcX.model.entity.Order;
import com.diploma.MrcX.reposirtory.OffersRepository;
import com.diploma.MrcX.service.OffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OffersServiceImplementes implements OffersService {

    private final OffersRepository offersRepository;
    @Override
    public void addFreelancerToOrder(Freelancers freelancers, Order order) {
        Offers offer = new Offers();
        offer.setFreelancer(freelancers);
        offer.setOrder(order);
        offersRepository.save(offer);
    }

    @Override
    public boolean existByFreelancerId(String id, UUID orderId) {
        return offersRepository.existsByFreelancer_Id(id,orderId);
    }

    @Override
    public void deleteFrelancer(Freelancers freelancer, Order order) {
        offersRepository.deleteByFreelancerAndOrder(freelancer,order);
    }

    @Override
    public List<Offers> findByOrderId(UUID id) {
        return offersRepository.findByOrder_Id(id);
    }
}
