package com.example.pfa_p.Model;

import android.content.Context;
import android.util.Log;

import com.example.pfa_p.Activities.ResultsActivity;
import com.example.pfa_p.SurveyDataSingleton;
import com.example.pfa_p.Utils.RatingSystem;

import java.util.List;

public class Result implements RatingSystem {


    private int maxResultValue;
    private int resultValueActual;
    private String resultText;
    private String nameForResults;
    private int despondencyValue;
    private String despondencyText;
    private String despondencyResultText;

    public int getDespondencyValue() {
        return despondencyValue;
    }

    public String getDespondencyText() {
        return despondencyText;
    }

    public String getDespondencyResultText() {
        return despondencyResultText;
    }

    private float meanScoreForSection;

    public Result(LeftPane item) {

        if (item instanceof Domain) {
            maxResultValue = calculateMaxResultValue((Domain) item);
            resultValueActual = calculateActualResultsValue((Domain) item);
            resultText = generateResultText((Domain) item);
            nameForResults = createNameForResults((Domain) item);
            despondencyValue = calculateDespondencyValue((Domain)item);
            despondencyText = "Despondency";
            despondencyResultText = calculateDespondencyResultText(despondencyValue);


        } else if (item instanceof SubModule) {
            if (((SubModule) item).hasDomains()) {
                List<Domain> domains = ((SubModule) item).getDomains();
                for (Domain domain : domains) {
                    if (!domain.isDomainResultCalculated) {
                        domain.getResult();
                    }
                }
                resultText = generateResultText((SubModule) item);
            }

        }
    }

    private String calculateDespondencyResultText(int despondencyValue){

       String despondencyResultText;
        if (despondencyValue >= 0 && despondencyValue <= 5) {
            despondencyResultText = RatingSystem.RESULT_SATISFACTORY;
        } else if (despondencyValue >= 6 && despondencyValue <= 13) {
            despondencyResultText = RatingSystem.RESULT_MILD_DESPONDENCY;
        } else {
            despondencyResultText = RatingSystem.RESULT_SEVERE_DESPONDENCY;
        }
        return despondencyResultText ;
    }


    private String createNameForResults(Domain domain) {
        String name = domain.getName();

        String assessing = "Assessing ";
        String checking = "Checking ";
        String finalString = "";


        if (name.contains(assessing)) {
            finalString = name.replaceAll(assessing, "");
        }
        if (name.contains(checking)) {
            finalString = name.replaceAll(checking, "");
        }
        Log.d(ResultsActivity.class.getName(), "method:createNameForResults: " + finalString);
        return finalString;



    }

    private String generateResultText(Domain domain) {
        switch (domain.getName()) {
            case "Assessing Anti-Social Personality Traits":
                if (resultValueActual > 0 && resultValueActual <= 6) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (resultValueActual > 6 && resultValueActual <= 9) {
                    return RatingSystem.RESULT_PERSONALITY_SLIGHTLY_ANTISOCIAL;
                } else {
                    return RatingSystem.RESULT_PERSONALITY_PROMINENT_ANTISOCIAL;
                }
            case "Assessing Possible Severe Psychiatric Illness":
                if (resultValueActual > 1) {
                    return RatingSystem.RESULT_NEED_REFERRAL;
                } else
                    return RatingSystem.RESULT_NORMAL;
            case "Checking Orientation":
                if (resultValueActual >= 0 && resultValueActual <= 9) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (resultValueActual >= 10 && resultValueActual <= 17) {
                    return RatingSystem.RESULT_MILD;
                } else if (resultValueActual >= 18 && resultValueActual <= 22) {
                    return RatingSystem.RESULT_MODERATE;
                } else {
                    return RatingSystem.RESULT_SEVERE;
                }
            case "Checking Anxiousness & Irritability":

                if (resultValueActual >= 0 && resultValueActual <= 6) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (resultValueActual >= 7 && resultValueActual <= 12) {
                    return RatingSystem.RESULT_MILD;
                } else if (resultValueActual >= 13 && resultValueActual <= 18) {
                    return RatingSystem.RESULT_MODERATE;
                } else {
                    return RatingSystem.RESULT_SEVERE;
                }
            case "Checking Social State":

                if (resultValueActual >= 0 && resultValueActual <= 5) {
                    return RatingSystem.RESULT_SATISFACTORY;
                } else if (resultValueActual >= 6 && resultValueActual <= 9) {
                    return RatingSystem.RESULT_CONSIDERABLE_SUPPORT;
                } else if (resultValueActual >= 10 && resultValueActual <= 14) {
                    return RatingSystem.RESULT_SLIGHT_SUPPORT;
                } else {
                    return RatingSystem.RESULT_POOR_SUPPORT;
                }
            case "Checking Physical State":

                if (resultValueActual >= 0 && resultValueActual <= 4) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (resultValueActual >= 5 && resultValueActual <= 9) {
                    return RatingSystem.RESULT_MILD;
                } else if (resultValueActual >= 10 && resultValueActual <= 13) {
                    return RatingSystem.RESULT_MODERATE;
                } else {
                    return RatingSystem.RESULT_SEVERE;
                }
            case "Checking emotional state":

                if (resultValueActual >= 0 && resultValueActual <= 6) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (resultValueActual >= 7 && resultValueActual <= 12) {
                    return RatingSystem.RESULT_MILD;
                } else if (resultValueActual >= 13 && resultValueActual <= 18) {
                    return RatingSystem.RESULT_MODERATE;
                } else {
                    return RatingSystem.RESULT_SEVERE;
                }
            case "Assessing Suicidal Feelings":

                if (resultValueActual == 0) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (resultValueActual >= 1 && resultValueActual <= 3) {
                    return RatingSystem.RESULT_MILD;
                } else if (resultValueActual >= 4 && resultValueActual <= 6) {
                    return RatingSystem.RESULT_MODERATE;
                } else {
                    return RatingSystem.RESULT_SEVERE;
                }
            case "Assessing Responsiveness":


                if (resultValueActual >= 0 && resultValueActual <= 8) {
                    return RatingSystem.RESULT_NO_RESPONSE;
                } else if (resultValueActual >= 9 && resultValueActual <= 20) {
                    return RatingSystem.RESULT_NORMAL_RESPONSE;
                } else {
                    return RatingSystem.RESULT_OVER_EXPRESSIVE;
                }
        }

        return "";
    }

    public float getMeanSectionScore() {
        return meanSectionScore;
    }
    int maxSectionScore;
    public int getMaxSectionScore(){
        return maxSectionScore;
    }

    float meanSectionScore ;
    private String generateResultText(SubModule subModule) {

         meanSectionScore = calculateMean(subModule);
         maxSectionScore = calculateMaxSectionScore(subModule);
        if (subModule.hasDomains()) {
            return generateResultText(meanSectionScore, subModule);
        } else {
            return "";
        }
    }
 private int   calculateMaxSectionScore(SubModule subModule){

        return - 1;
 }

    private float calculateMean(SubModule subModule) {

        int domainResultValue = 0;
        List<Domain> domains = subModule.getDomains();
        for (Domain domain : domains) {
            domainResultValue += domain.getResult().getResultValueActual();
        }
        return (float) domainResultValue / domains.size();
    }


    private String generateResultText(float meanSectionScore, SubModule subModule) {

        switch (subModule.getName()) {

            case "Questionnaire A": {
                return getResultForSectionWiseLimits(5, 10, 15, meanSectionScore);
            }
            case "Questionnaire B": {
                return getResultForSectionWiseLimits(5, 10, 13, meanSectionScore);
            }
            case "Questionnaire C": {
                return getResultForSectionWiseLimits(0, 3, 6, meanSectionScore);
            }
            case "Questionnaire D": {
                if (meanSectionScore > 1) {
                    return RatingSystem.RESULT_NEED_REFERRAL;
                } else
                    return RatingSystem.RESULT_NORMAL;
            }
            case "Questionnaire E": {
                if (meanSectionScore > 0 && meanSectionScore <= 6) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (meanSectionScore > 6 && meanSectionScore <= 9) {
                    return RatingSystem.RESULT_PERSONALITY_SLIGHTLY_ANTISOCIAL;
                } else {
                    return RatingSystem.RESULT_PERSONALITY_PROMINENT_ANTISOCIAL;
                }
            }

            case "Questionnaire F": {

               /* return "";*/ //TODO: mean score? despondency as another domain or just a value?
              /*  if (getName().equals("Checking Responsiveness")) {*/
                    if (meanSectionScore > 0 && meanSectionScore <= 8) {
                        return RatingSystem.RESULT_NO_RESPONSE;
                    } else if (meanSectionScore > 8 && meanSectionScore <= 20) {
                        return RatingSystem.RESULT_NORMAL_RESPONSE;
                    } else {
                        return RatingSystem.RESULT_OVER_EXPRESSIVE;
                    }
                }/* else if (getName().equals("Checking despondency")) {
                    if (meanSectionScore > 0 && meanSectionScore <= 5) {
                        return RatingSystem.RESULT_SATISFACTORY;
                    } else if (meanSectionScore > 5 && meanSectionScore <= 13) {
                        return RatingSystem.RESULT_MILD_DESPONDENCY;
                    } else {
                        return RatingSystem.RESULT_NEED_REFERRAL;
                    }
                }*/

            default:
                return "";
        }

    }


    private String getResultForSectionWiseLimits(int firstLimit, int secondLimit, int thirdLimit, float meanSectionScore) {


        if (meanSectionScore > 0 && meanSectionScore <= firstLimit) {

            return RatingSystem.RESULT_NO_INTERVENTION_REQUIRED;
        } else if (meanSectionScore > firstLimit && meanSectionScore <= secondLimit) {
            return RatingSystem.RESULT_INTERVENTION_REQUIRED;
        } else if (meanSectionScore > secondLimit && meanSectionScore <= thirdLimit) {
            return RatingSystem.RESULT_INTERVETION_REQUIRED_WITH_FOLLOW_UP;
        } else {
            return RatingSystem.RESULT_NEED_REFERRAL;
        }

    }


    private int calculateMaxResultValue(Domain domain) {
        List<Question> questions = domain.getQuestions();
        int maxValue = 0;
        for (Question question : questions) {
            maxValue += question.getOptions().getOptions().size() - 1;

        }
        return maxValue;
    }

    private int calculateDespondencyValue(Domain domain) {
        int despondency = 0;
        if (!domain.getName().equals("Assessing Responsiveness")) {
            despondency = -1;

        } else {
            List<Question> questions = domain.getQuestions();
            for (Question question : questions) {
                despondency += question.getDespondency();
            }
        }
        return despondency;
    }

    private int calculateActualResultsValue(Domain domain) {

        int actualValue = 0;
        List<Question> questions = domain.getQuestions();

        if (domain.getSubModule().getName().equals("Questionnaire E")) {
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                int index = question.getAnswerIndex();
                if (i < questions.size() - 1) {
                    if (index == 0) {
                        actualValue += 2;
                    } else {
                        actualValue += 1;
                    }
                } else {
                    if (index == 0) {
                        actualValue += 1;
                    } else {
                        actualValue += 2;
                    }
                }
            }
            return actualValue;
        } else if (domain.getName().equals("Checking Social State") || domain.getName().equals("Checking Physical State")) {

            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                int index = question.getAnswerIndex();
                actualValue += question.getOptions().getOptions().size() - 1 - index;
            }
            return actualValue;
        } else {
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                int index = question.getAnswerIndex();
                actualValue += index;
            }
            return actualValue;
        }
    }

    public int getMaxResultValue(/*LeftPane item*/) {
        return maxResultValue;
    }

    public void setMaxResultValue(int maxResultValue) {
        this.maxResultValue = maxResultValue;
    }

    public int getResultValueActual() {
        return resultValueActual;
    }

   /* public void setResultValueActual(LeftPane item) {
        if (item instanceof Domain) {
        }

        if (item instanceof SubModule) {
        }


        this.resultValueActual = resultValueActual;
    }*/

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getNameForResults() {
        return nameForResults;
    }

    public void setNameForResults(String nameForResults) {
        this.nameForResults = nameForResults;
    }

    @Override
    public float getMeanScore() {
        return 0;
    }

   /* public String getResults(Domain domain) {

        String result = "";

        return result;

    }*/

   /* public List<String> getResults(SubModule section) {

        if (section.hasDomains()) {
            List<String> results = new ArrayList<>();
            List<Domain> domains = section.getDomains();
            for (Domain domain : domains) {
                results.add(getResults(domain));
            }
            return results;
        }

        *//*if (section.getName().equals("Basic Questionnaire")) {
            List<String> result = new ArrayList<>();
          //  List<String> relevantSectionsList = new ArrayList<>();
            List<Question> questions = section.getQuestions();
            String bitInformation = calculate(questions, modules.get(section.getModule().getIndex() + 1));
            for(char c: bitInformation.toCharArray()){
                result.add(String.valueOf(c));
            }
            return result;
        }*//*

        return new ArrayList<>();


    }*/


    public static boolean[] evaluateQuestionnaires(Module module, Context context) {

        if (module.getName().equals("Basic Questionnaire")) {

            List<Question> currentModuleQuestions = module.getSections().get(0).getQuestions();
            Module nextModule = SurveyDataSingleton.getInstance(context).getModules().get(module.getIndex() + 1);
            List<SubModule> nextSections = nextModule.getSections();
            return calculate(currentModuleQuestions, nextModule);




           /* List<String> bits = getResults(module.getSections().get(0));

            for(int i = 0; i<nextSections.size(); i++){

                String bit = bits.get(i);
                SubModule section = nextSections.get(i);
                section.isPresent = bit.equals("1");
            }*/


        }
        return null;
    }


    private static boolean[] calculate(List<Question> questions, Module nextModule) {


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
            if (questions.get(i).getAnswerIndex() == 0) {
                isSectionIPresent[3] = true;
                break;
            }
        }
        isSectionIPresent[4] = questions.get(10).getAnswerIndex() != 0;
        isSectionIPresent[5] = true;

        for (int i = 0; i < nextModule.getSections().size(); i++) {

            nextModule.getSections().get(i).setIsPresent(isSectionIPresent[i]);
            Log.d("RESULTS", "following state of sections = " + isSectionIPresent[i]);

        }
        return isSectionIPresent;




        /*String temp = "";
        for(boolean flag: isSectionIPresent){
            temp = flag?"1":"0";
            concatString.append(temp);
        }

        return concatString.toString();*/
    }
}



