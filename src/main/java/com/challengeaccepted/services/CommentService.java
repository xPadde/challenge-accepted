package com.challengeaccepted.services;


import com.challengeaccepted.models.CommentModel;
import com.challengeaccepted.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public void saveCommentToDatabase(CommentModel commentModel){
        commentRepository.saveAndFlush(commentModel);
    }
}
