package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.History;
import com.expleo.webcm.entity.expleodb.Skill;

import java.util.LinkedList;
import java.util.List;

/**
 * A helper class which is allows to make a proper list for History tracking
 * by manipulating HistoryForWeb objects
 * */
public class HistoryForWebUtil {

    /**
     * makeList is doing what the name says. Is taking a list of History objects and, from this objects, is generating
     * HistoryForWeb objects. Theses objects make the job of putting the data on the web easier.
     * @param histories is a list o History objects
     * @return a list of HistoryForWeb filled with data from histories
     * */
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
