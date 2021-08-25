package com.cooperweisbach.CommunityGarden.controllers.Member;

import com.cooperweisbach.CommunityGarden.models.Configuration;
import com.cooperweisbach.CommunityGarden.services.ConfigurationServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class MemberAccountRestController {

    private ConfigurationServices configurationServices;

    @Autowired
    public MemberAccountRestController(ConfigurationServices configurationServices) {
        this.configurationServices = configurationServices;
    }

    @GetMapping("/api/configurations/load")
    public Configuration getPublishedConfiguration(){
        return configurationServices.getPublishedConfiguration();
    }
}
