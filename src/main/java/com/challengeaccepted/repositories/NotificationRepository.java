package com.challengeaccepted.repositories;

import com.challengeaccepted.models.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
}