package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iMessageRepo extends JpaRepository<Message, Integer> {

}
