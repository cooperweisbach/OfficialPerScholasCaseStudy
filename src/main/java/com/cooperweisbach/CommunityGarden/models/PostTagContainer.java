package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class PostTagContainer {
    private ArrayList<PostTag> newPostTagList = new ArrayList<>();
    private ArrayList<String> newPostTagTitles = new ArrayList<>();

    public PostTagContainer() {
    }

    public int getNumberOfAddedTags(){
        int counter = 0;
        for(PostTag pt : newPostTagList){
            if(pt != null){
                counter++;
            }
        }
        return counter;
    }

    public void addNewElement(String newElementName){
        newPostTagList.add(new PostTag(newElementName));
    }
}
