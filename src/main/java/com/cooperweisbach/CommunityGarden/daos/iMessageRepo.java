package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Message;
import com.cooperweisbach.CommunityGarden.models.MessageThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface iMessageRepo extends JpaRepository<Message, Integer> {

//    List<Message> findAllByMessageIdOrderByDesc(Pageable page);

    @Query(
            value="SELECT m FROM Message m WHERE m.messageThread = :thread"
    )
    Page<Message> findMessagesByMessageThread(@Param("thread") MessageThread thread, Pageable page);
}
