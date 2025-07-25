package com.example.demo.repository;

import com.example.demo.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Integer> {

    Party findByPartyId(Integer partyId);
}
