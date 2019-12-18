package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.ProiectSkill;
import com.expleo.webcm.entity.expleodb.UserExpleo;
import com.expleo.webcm.entity.expleodb.UserSkill;

import java.util.LinkedList;
import java.util.List;

/**
 * A helper class which will create and manipulate data for teamMatrix
 * */

public class CreateMatrixTeam {

    /**
     * Creating a list of MatrixTeamMember with all data from the list in a corresponding order;
     *
     *  @param foundUsers is a list filled with UserExpleo objects. Users from database.
     *  @param foundSkills is a list filled with ProiectSkill objects. Skills required for the project.
     *  @param foundUserSkills is a list filled with UserSkill objects. User skills evaluations.
     *  @return a project.
     * */
    public List<MatrixTeamMember> makeMatrixTeamList(
            List<UserExpleo> foundUsers, List<ProiectSkill> foundSkills, List<UserSkill> foundUserSkills){

        List<MatrixTeamMember> makeTeam = new LinkedList<>();

        for(UserExpleo user : foundUsers){
            MatrixTeamMember tempMember = new MatrixTeamMember();
            tempMember.setUser(user);
            for(UserSkill userSkill : foundUserSkills){
                for(ProiectSkill proiectSkill : foundSkills){
                    if (user.getUserSkills().contains(userSkill) && proiectSkill.getSkill().equals(userSkill.getSkill())) {
                        tempMember.addUserSkill(userSkill);
                        tempMember.addProiectSkill(proiectSkill);
                    }
                }
            }
            tempMember.makeScore();
            tempMember.makeName();
            makeTeam.add(tempMember);
        }

        return makeTeam;
    }

    /**
     * It will sort the input list by pondere. If some of the entries has the same pondere, will order by name.
     * At the end will call the method sortMatrixTeamListByScore for vertical sort.
     *
     *  @param input a list of MatrixTeamMember.
     * */
    public void sortMatrixTeamList(List<MatrixTeamMember> input){
        for(MatrixTeamMember member:input){
            member.getProiectSkills().sort((ps1, ps2) -> {
                if(ps2.getPondere() - ps1.getPondere() == 0){
                    return ps1.getSkill().getNumeSkill().compareTo(ps2.getSkill().getNumeSkill());
                }else {
                    return ps2.getPondere() - ps1.getPondere();
                }
            });
            List<UserSkill> tempList = new LinkedList<>();

            for(ProiectSkill proiectSkill : member.getProiectSkills()){
                for(UserSkill userSkill : member.getSkills()){
                    if(proiectSkill.getSkill().getIdSkill()==userSkill.getSkill().getIdSkill()){
                        tempList.add(userSkill);
                    }
                }
            }

            member.getSkills().clear();
            member.setSkills(tempList);
        }
        sortMatrixTeamListByScore(input);
    }

    /**
     * Returns a sorted list by score. If some of the entries has the same score, will order by name.
     *
     *  @param input a list of MatrixTeamMember.
     * */
    private void sortMatrixTeamListByScore(List<MatrixTeamMember> input){
        input.sort((o1, o2) -> {
            if(o2.getScore()-o1.getScore() == 0){
                return (o1.getUser().getNume()+" "+ o1.getUser().getPrenume()).compareTo(
                        (o2.getUser().getNume() + " " + o2.getUser().getPrenume()));
            } else{
                    return o2.getScore()-o1.getScore();
                }
            });
    }


}
