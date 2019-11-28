package com.expleo.webcm.config;

import com.expleo.webcm.service.ProiectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Guard {
    @Autowired
    ProiectService proiectService;

    public boolean checkCodProiect(String codProiect){
        return proiectService.hasPrincipalProject(codProiect);
    }
}
