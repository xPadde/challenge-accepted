package com.challengeaccepted.services;

import com.challengeaccepted.models.NewsFeedModel;
import com.challengeaccepted.repositories.NewsFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsFeedService {

    @Autowired
    private NewsFeedRepository newsFeedRepository;

    public void saveNewsFeedToDatabase(NewsFeedModel newsFeedModel){
        newsFeedRepository.saveAndFlush(newsFeedModel);
    }

    public void updateNewsFeedToDatabase(NewsFeedModel newsFeedModel){
        newsFeedRepository.save(newsFeedModel);
    }

    public NewsFeedModel getNewsFeedFromDatabase(Long id){
        return newsFeedRepository.getOne(id);
    }
    public void updateNewsFeedModel (NewsFeedModel newsFeedModelToUpdate){
        newsFeedRepository.save(newsFeedModelToUpdate);
    }
}
