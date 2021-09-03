package com.cooperweisbach.CommunityGarden.controllers.Basic;

import com.cooperweisbach.CommunityGarden.models.Configuration;
import com.cooperweisbach.CommunityGarden.services.ConfigurationServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class BasicRestController {

    @GetMapping("/header")
    public String returnHeader() throws IOException {
        File htmlFile = new File("src/main/resources/templates/header.html");
        String htmlContent = new String(Files.readAllBytes(htmlFile.toPath()));
        log.warn(htmlContent);
        return htmlContent;
    }

}
