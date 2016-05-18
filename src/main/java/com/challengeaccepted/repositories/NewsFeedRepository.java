package com.challengeaccepted.repositories;

import com.challengeaccepted.models.ChallengeModel;
import com.challengeaccepted.models.NewsFeedModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface NewsFeedRepository extends JpaRepository<NewsFeedModel, Long>{
}
