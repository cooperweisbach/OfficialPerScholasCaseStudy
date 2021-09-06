package com.cooperweisbach.CommunityGarden.services;

import com.cooperweisbach.CommunityGarden.daos.iImageRepo;
import com.cooperweisbach.CommunityGarden.models.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ImageServices {

    private iImageRepo imageRepo;

    @Autowired
    public ImageServices(iImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public List<Image> getAllImages(){
        return imageRepo.findAll();
    }

    public void save(Image image){imageRepo.save(image);}

    public Image getImageById(int id){
        return imageRepo.getById(id);
    }

    public void deleteById(int id){
        imageRepo.deleteById(id);
    }

    public Page<Image> getResultsInRange(Integer pageNum, Integer numOfResults) {
        return imageRepo.findAll(PageRequest.of(pageNum, numOfResults));
    }
}
