package com.challengeaccepted.repositories;

import com.challengeaccepted.models.NewsFeedModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsFeedRepository extends JpaRepository<NewsFeedModel, Long>{
}
