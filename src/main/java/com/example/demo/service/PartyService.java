package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.Party;
import com.example.demo.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    public Party findById(Integer partyId) {
        return partyRepository.findByPartyId(partyId);
    }

    public Integer totalPrice(Party party) {
        int total = 0;
        for (Item item : party.getItems()) {
            total += item.getItemPrice();
        }
        return total;
    }
}
