package com.challengeaccepted.repositories;

import com.challengeaccepted.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by joel_ on 2016-04-29.
 */
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel getByGoogleTokenId(String googleTokenId);
}
