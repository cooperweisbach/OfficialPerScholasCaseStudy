package com.cooperweisbach.CommunityGarden.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
public class PostTagContainer {
    private PostTag[] newPostTagList = new PostTag[10];
    private String[] newPostTagTitles = new String[10];

    public PostTagContainer() {
        for(PostTag pt: newPostTagList){
            pt = new PostTag();
        }
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

    public void addNewElement(int position, String newElementName){
        newPostTagList[position] = new PostTag(newElementName);
    }
}
