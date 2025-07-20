package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.Party;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyService partyService;

    @Transactional
    public void createNewItem(Integer partyId, Item item) {
        Party party = partyService.findById(partyId);

        item.setParty(party);
        party.getItems().add(item);

        itemRepository.save(item);
    }

    @Transactional
    public void deleteItem(Integer partyId, Integer itemId) {

        Item item = findById(itemId);

        Party party = partyService.findById(partyId);

        party.getItems().remove(item);

        item.setParty(null);
    }

    @Transactional
    public void modifyItem(Item modifiedItem, Integer partyId) {

        modifiedItem.setParty(partyService.findById(partyId));

        itemRepository.save(modifiedItem);
    }

    public Item findById(Integer itemId) {
        return itemRepository.findByItemId(itemId);
    }
}
