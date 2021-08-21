package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iImageTypeRepo;
import com.cooperweisbach.CommunityGarden.models.ImageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ImageTypeServices {
    private iImageTypeRepo imageTypeRepo;

    @Autowired
    public ImageTypeServices(iImageTypeRepo imageTypeRepo) {
        this.imageTypeRepo = imageTypeRepo;
    }

    public List<ImageType> getAllImageTypes(){
        return imageTypeRepo.findAll();
    }
}
