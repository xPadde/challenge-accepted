package com.challengeaccepted.repositories;

import com.challengeaccepted.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by joel_ on 2016-05-06.
 */
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
}
