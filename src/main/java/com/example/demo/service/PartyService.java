package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.Party;
import com.example.demo.repository.PartyRepository;
import com.example.demo.utilities.CustomFileUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    @Transactional
    public void addNewPartyByManager(Party party, MultipartFile partyImageFile) throws IOException {

        Thumbnails.of(partyImageFile.getInputStream())
                .forceSize(735, 429)
                .outputFormat("jpg")
                .toFile(new File("uploads/party-imgs/" + partyImageFile.getOriginalFilename()));

        party.setImageUrl("/images/home/party-imgs/" + partyImageFile.getOriginalFilename());
        partyRepository.save(party);
    }

    @Transactional
    public String deleteParty(Integer partyId) {
        Party partyDeletedByManager = findById(partyId);
        String partyName = partyDeletedByManager.getPartyName();

        String imageFileName = Paths.get(partyDeletedByManager.getImageUrl()).getFileName().toString();
        File file = new File("uploads/party-imgs/" + imageFileName);
        if (file.exists()) file.delete();

        partyRepository.delete(partyDeletedByManager);
        return partyName;
    }

    @Transactional
    public void modifyPartyByManager(Party party, MultipartFile partyImageFile) throws IOException {

        //delete image from uploads folder
        String imageFileName = Paths.get(findById(party.getPartyId()).getImageUrl()).getFileName().toString();
        File file = new File("uploads/party-imgs/" + imageFileName);
        if (file.exists()) file.delete();

        //resize new image
        Thumbnails.of(partyImageFile.getInputStream())
                .forceSize(735, 429)
                .outputFormat("jpg")
                .toFile(new File("uploads/party-imgs/" + partyImageFile.getOriginalFilename()));

        //set new imageUrl of the current party
        party.setImageUrl("/images/home/party-imgs/" + partyImageFile.getOriginalFilename());
        partyRepository.save(party);
    }
}
