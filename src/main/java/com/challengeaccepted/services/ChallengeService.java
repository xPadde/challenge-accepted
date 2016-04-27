package com.challengeaccepted.services;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.repositories.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return (ChallengeModel) challengeRepository.getOne(id);
    }
}
