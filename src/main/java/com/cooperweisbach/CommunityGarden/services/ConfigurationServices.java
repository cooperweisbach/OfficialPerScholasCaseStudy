package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iConfigurationRepo;
import com.cooperweisbach.CommunityGarden.models.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ConfigurationServices {
    private iConfigurationRepo configurationRepo;

    @Autowired
    public ConfigurationServices(
                                 iConfigurationRepo configurationRepo) {
        this.configurationRepo = configurationRepo;
    }

    public List<Configuration> getAllConfigurations(){
        return configurationRepo.findAll();
    }

    public Configuration checkUniquenessOfName(String configurationName){
        Configuration byName = configurationRepo.getConfigurationByConfigurationName(configurationName);
        if(byName == null){
            return new Configuration();
        } return byName;
    }

    public void resetPublishedField(Configuration publish){
        Configuration currentPublished = configurationRepo.getConfigurationByPublished(true);
        if(currentPublished != null){
            if(currentPublished.getConfigurationId() != publish.getConfigurationId())
            currentPublished.setPublished(false);
            configurationRepo.save(currentPublished);
        }
    }

    public Configuration save(Boolean publish, String name, String json){
        Configuration fromName = checkUniquenessOfName(name);
        log.warn("id of the configuration being saved: " + fromName.getConfigurationId());
        if(publish){
            resetPublishedField(fromName);
        }
        Configuration byName = fromName;
        byName.setConfigurationName(name);
        byName.setPublished(publish);
        byName.setJsonification(json);
        configurationRepo.save(byName);
        return byName;
    }


    public Configuration getPublishedConfiguration(){
        Configuration current = configurationRepo.getConfigurationByPublished(true);
        if(current != null){
            return configurationRepo.getConfigurationByPublished(true);
        }
        return new Configuration();
    }
}
