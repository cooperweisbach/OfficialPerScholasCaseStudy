package com.cooperweisbach.CommunityGarden.daos;

import com.cooperweisbach.CommunityGarden.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iConfigurationRepo extends JpaRepository<Configuration, Integer> {
    Configuration getConfigurationByConfigurationName(String configurationName);
    Configuration getConfigurationByPublished(Boolean published);
}
