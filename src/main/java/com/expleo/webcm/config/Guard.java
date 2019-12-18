package com.expleo.webcm.config;

import com.expleo.webcm.service.ProiectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Was created to check if a manager has the project that he wants to access.
 * The class is used only in SecurityDataSourceConfig.
 * */
@Component
public class Guard {

    @Autowired
    ProiectService proiectService;

    public boolean checkCodProiect(String codProiect){
        return proiectService.hasPrincipalProject(codProiect);
    }
}
