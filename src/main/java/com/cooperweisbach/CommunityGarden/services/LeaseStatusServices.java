package com.cooperweisbach.CommunityGarden.services;


import com.cooperweisbach.CommunityGarden.daos.iLeaseStatusRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class LeaseStatusServices {
    private iLeaseStatusRepo leaseStatusRepo;
}
