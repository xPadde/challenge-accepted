package com.challengeaccepted.services;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Stridsberg on 2016-04-27.
 */
@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public void saveChallengeToDatabase(ChallengeModel challengeModel) {
        challengeRepository.saveAndFlush(challengeModel);
    }
    public ChallengeModel getChallengeFromDatabase(Long id){
        return challengeRepository.getOne(id);
    }

    public ArrayList<ChallengeModel> getAllChallengesFromDatabase() {
        return (ArrayList<ChallengeModel>) challengeRepository.findAll();
    }

    public void updateChallengeInDatabase(ChallengeModel challengeModelFromWeb) {
        challengeRepository.save(challengeModelFromWeb);
    }
}
