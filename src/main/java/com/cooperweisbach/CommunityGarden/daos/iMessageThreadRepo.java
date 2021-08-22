package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.MessageThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iMessageThreadRepo extends JpaRepository<MessageThread, Integer> {
    void deleteByMessageThreadId(Integer integer);
    MessageThread getMessageThreadByMessageThreadName(String messageThreadName);
    MessageThread getMessageThreadByMessageThreadId(Integer id);
}
