package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.History;
import com.expleo.webcm.entity.expleodb.Skill;

import java.util.LinkedList;
import java.util.List;

public class HistoryForWebUtil {
//    private List<History> histories;
//
//    public HistoryForWebUtil(List<History> histories) {
//        this.histories = histories;
//    }

    public List<HistoryForWeb> makeList(List<History> histories) {
        List<HistoryForWeb> historyForWebs = new LinkedList<>();
        List<Skill> skills = new LinkedList<>();

        for(History history:histories){
            if(!skills.contains(history.getSkill())){
                skills.add(history.getSkill());
            }
        }

        for(Skill tempSkill:skills){
            HistoryForWeb historyUtil = new HistoryForWeb(tempSkill);
            for(History history:histories){
                if(history.getSkill().equals(historyUtil.getSkill())){
                    historyUtil.addDate(history.getDate());
                    historyUtil.addEvaluations(history.getEvaluare());
                }
            }
            historyForWebs.add(historyUtil);
        }

        return historyForWebs;
    }
}
