package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.LinkedList;
import java.util.List;

public class PrintMatrixTeam {

    public PrintMatrixTeam() {
    }

    public List<MatrixTeamMember> makeMatrixTeamList(
            List<UserExpleo> foundUsers, List<ProiectSkill> foundSkills, List<UserSkill> foundUserSkills){

        List<MatrixTeamMember> makeTeam = new LinkedList<>();

        for(UserExpleo user : foundUsers){
            MatrixTeamMember tempMember = new MatrixTeamMember();
            tempMember.setUser(user);
            for(UserSkill userSkill : foundUserSkills){
                for(ProiectSkill proiectSkill : foundSkills){
                    if(user.getUserSkills().contains(userSkill)){
                        if(proiectSkill.getSkill().equals(userSkill.getSkill())){
                            tempMember.addUserSkill(userSkill);
                            tempMember.addProiectSkill(proiectSkill);
                            tempMember.makeScore();
                        }
                    }
                }
            }

            makeTeam.add(tempMember);

        }

        return makeTeam;
    }


}
