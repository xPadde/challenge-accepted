package com.challengeaccepted.repositories;

import com.challengeaccepted.models.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

    ArrayList<NotificationModel> getByInteractedChallengeChallengeCreatorId(Long id);

    ArrayList<NotificationModel> getByInteractedChallengeChallengeClaimerId(Long id);

    ArrayList<NotificationModel> getByInteractedChallengeChallengeUpvotersId(Long id);

}