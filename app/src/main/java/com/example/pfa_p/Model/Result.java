package com.example.pfa_p.Model;

import android.content.Context;

import com.example.pfa_p.SurveyDataSingleton;
import com.example.pfa_p.Utils.RatingSystem;

import java.util.ArrayList;
import java.util.List;

public class Result implements RatingSystem {









    @Override
    public float getMeanScore() {
        return 0;
    }

    public String getResults(Domain domain) {

        String result = "";

        return result;

    }

    public List<String> getResults(SubModule section) {

        if (section.hasDomains()) {
            List<String> results = new ArrayList<>();
            List<Domain> domains = section.getDomains();
            for (Domain domain : domains) {
                results.add(getResults(domain));
            }
            return results;
        }

        /*if (section.getName().equals("Basic Questionnaire")) {
            List<String> result = new ArrayList<>();
          //  List<String> relevantSectionsList = new ArrayList<>();
            List<Question> questions = section.getQuestions();
            String bitInformation = calculate(questions, modules.get(section.getModule().getIndex() + 1));
            for(char c: bitInformation.toCharArray()){
                result.add(String.valueOf(c));
            }
            return result;
        }*/

        return new ArrayList<>();


    }



    public void evaluateQuestionnaires(Module module, Context context){

        if(module.getName().equals("Basic Questionnaire")){

            List<Question> currentModuleQuestions = module.getSections().get(0).getQuestions();
            Module nextModule = SurveyDataSingleton.getInstance(context).getModules().get(module.getIndex() + 1);
            List<SubModule> nextSections = nextModule.getSections();
            calculate(currentModuleQuestions,nextModule);




           /* List<String> bits = getResults(module.getSections().get(0));

            for(int i = 0; i<nextSections.size(); i++){

                String bit = bits.get(i);
                SubModule section = nextSections.get(i);
                section.isPresent = bit.equals("1");
            }*/


        }
    }


    private void calculate(List<Question> questions, Module nextModule) {


        boolean[] isSectionIPresent = new boolean[nextModule.getSections().size()];

        int PRESENT = 1;
        int ABSENT = 0;
        int response = 0;
  //      StringBuilder concatString = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            Question question = questions.get(i);
            response += question.getAnswerIndex();
        }
        isSectionIPresent[0] = response < 2;
        isSectionIPresent[1] = questions.get(3).getAnswerIndex() == 0;
        isSectionIPresent[2] = questions.get(2).getAnswerIndex() == 0 && questions.get(6).getAnswerIndex() == 0;
        for (int i = 4; i < 10; i++) {
            if (isSectionIPresent[3] = questions.get(i).getAnswerIndex() == 0)
                break;

        }
        isSectionIPresent[4] = questions.get(10).getAnswerIndex() != 0;

        for(int i = 0; i<nextModule.getSections().size(); i++){

            nextModule.getSections().get(i).setIsPresent(isSectionIPresent[i]);
        }



        /*String temp = "";
        for(boolean flag: isSectionIPresent){
            temp = flag?"1":"0";
            concatString.append(temp);
        }

        return concatString.toString();*/
    }
}



