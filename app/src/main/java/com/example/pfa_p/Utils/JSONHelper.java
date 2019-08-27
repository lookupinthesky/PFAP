package com.example.pfa_p.Utils;

import android.util.Log;

import com.example.pfa_p.Activities.DashboardActivity;
import com.example.pfa_p.Model.AnswerOptions;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JSONHelper {

    public JSONHelper(String jsonString) {
        try {
            createSurveyData(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(DashboardActivity.class.getName(), "Problem in JSON Parsing, Cannot create JSONHelper Object");
        }
    }


    public static String getSurveyId(){

        return "survey_id";
    }


    public List<Module> getModules() {
        return modules;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public List<Domain> getDomains(){return domains; }
    public List<SubModule> getSections(){return subModules;}

    private List<Module> modules;
    private List<SubModule> subModules;
    private List<Domain> domains;
    private List<Question> questions;
    private List<Domain> domainSectionWise;
    private List<Question> questionsSectionWise;
    private List<Question> questionsDomainWise;
    private List<SubModule> sectionsModuleWise;

    private void createSurveyData(String jsonString) throws JSONException {
        intializeArrays();
        JSONArray data = new JSONArray(jsonString);
        createModulesArray(data);
        createSubModulesArray(data);
        createDomainsArray(data);
        createQuestionsArray(data, domains, subModules, modules);
        setQuestionsToDomains(domains, questions);
        setQuestionsToSubModules(subModules, questions);
        setDomainsToSubModules(subModules, domains);
        setSubModulesToModules(modules, subModules);
    }

    private void intializeArrays(){
        modules = new ArrayList<>();
        subModules = new ArrayList<>();
        domains = new ArrayList<>();
        questions = new ArrayList<>();
        questionsDomainWise = new ArrayList<>();
        questionsSectionWise = new ArrayList<>();
        domainSectionWise = new ArrayList<>();
        sectionsModuleWise = new ArrayList<>();
    }

    private AnswerOptions getAnswerOptionsForQuestion(JSONObject object) throws JSONException {
        AnswerOptions options = new AnswerOptions(object.getString("answer_type"),
                object.getInt("number_of_options"),
                object.getString("option1"),
                object.getString("option2"),
                object.getString("option3"),
                object.getString("option4"),
                object.getString("option5"),
                object.getString("option6"),
                object.getString("option7"),
                object.getString("option8")
        );
        return options;
    }

    private void createModulesArray(JSONArray data) throws JSONException {
        Set<Module> moduleSet = new LinkedHashSet<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject eachObject = data.getJSONObject(i);
            String moduleName = eachObject.getString("module_name");
            Module module1 = new Module(moduleName);
            moduleSet.add(module1);
        }
        modules = new ArrayList<>(moduleSet);
        for(Module module: modules){
            module.setIndex(modules.indexOf(module));
            if(module.getName().equals("Assessing Need For PFA-P")){
                module.setResultBased(true);
            }
        }
    }

    private void createSubModulesArray(JSONArray data) throws JSONException {
        Set<SubModule> subModuleSet = new LinkedHashSet<>();
       // int count = 0;
        for (int i = 0; i < data.length(); i++) {
            JSONObject eachObject = data.getJSONObject(i);
            String subModuleName = eachObject.getString("section_name");
            SubModule subModule = new SubModule(subModuleName);
            String moduleName = eachObject.getString("module_name");
            for (Module module5 : modules) {
              //  module5.setIndex(count++);
                if (module5.getName().equals(moduleName)) {
                    subModule.setModule(module5);
                }
            }
            subModuleSet.add(subModule);
      //      subModule.setIndex(count++);
        }
        subModules = new ArrayList<>(subModuleSet);
    }


    private void createDomainsArray(JSONArray data) throws JSONException {
        Set<Domain> domainSet = new LinkedHashSet<>();
    //    int count = 0;
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            String domainName = object.getString("domain_name");
            if (!domainName.equals("null")) {
                Domain domain = new Domain(domainName);

                domainSet.add(domain);
            }
        }
        domains = new ArrayList<>(domainSet);
    }


    private void createQuestionsArray(JSONArray data, List<Domain> domains, List<SubModule> subModules, List<Module> modules) throws JSONException {
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            Question question = new Question(object.getString("question_name"));
            String domainName = object.getString("domain_name");
            for (Domain domain : domains) {
                if (domain.getName().equals(domainName)) {
                    question.setDomain(domain);
                }
            }
            String subModuleName = object.getString("section_name");
            for (SubModule subModule : subModules) {
                if (subModule.getName().equals(subModuleName)) {
                    question.setSubModule(subModule);
                }
            }
            String moduleName = object.getString("module_name");
            for (Module module : modules) {
                if (module.getName().equals(moduleName)) {
                    question.getSubModule().setModule(module);
                }
            }

            question.setOptions(getAnswerOptionsForQuestion(object));
            question.setSerialNumber(object.getInt("serial_number"));
            questions.add(question);
        }
    }

    private void setQuestionsToDomains(List<Domain> domains, List<Question> questions) {
        for (Domain domain : domains) {
            for (Question question2 : questions) {
                if (question2.getDomain()!= null &&  question2.getDomain().getName().equals(domain.getName())) {
                    questionsDomainWise.add(question2);
                }
            }
            domain.setQuestions(questionsDomainWise);
            setSubModuleToDomain(domain,questionsDomainWise);
            questionsDomainWise = new ArrayList<>();
        }
    }

    private void setSubModuleToDomain(Domain domain, List<Question> questionsDomainWise){
        domain.setSubModule(questionsDomainWise.get(0).getSubModule());
    }

    private void setDomainsToSubModules(List<SubModule> subModules, List<Domain> domains) {

        for (SubModule submodule : subModules) {
            int count = 0;
            for (Domain domain : domains) {
                if (domain.getSubModule().getName().equals(submodule.getName())) {
                    domain.setIndex(count++);
                    domainSectionWise.add(domain);
                }
            }
            submodule.setDomains(domainSectionWise);
            domainSectionWise = new ArrayList<>();
        }
    }

    private void setQuestionsToSubModules(List<SubModule> subModules, List<Question> questions) {
        for (SubModule submodule : subModules) {
            for (Question question : questions) {
                if (question.getSubModule().getName().equals(submodule.getName())) {
                    questionsSectionWise.add(question);
                }
            }
            submodule.setQuestions(questionsSectionWise);
            questionsSectionWise = new ArrayList<>();
        }
    }

    private void setSubModulesToModules(List<Module> modules, List<SubModule> subModules) {

        for (Module module : modules) {
            int count = 0;
            for (SubModule subModule6 : subModules) {
                if (subModule6.getModule().getName().equals(module.getName())) {
                    subModule6.setIndex(count++);
                    sectionsModuleWise.add(subModule6);
                }
            }
            module.setSections(sectionsModuleWise);
            sectionsModuleWise = new ArrayList<>();
        }
    }

    private void parseData(String jsonString) throws JSONException {

        Question question = new Question("");
        Module module = new Module("");
        SubModule subModule = new SubModule();
        JSONObject eachObject;
        modules = new ArrayList<>();
        ArrayList<SubModule> subModules = new ArrayList<>();
        questions = new ArrayList<>();
        ArrayList<Question> questionsSectionWise = new ArrayList<>();
        String moduleName = "";
        String subModuleName = "";
        String domainName = "null";

        Domain domain = new Domain("");
        List<Question> questionsDomainWise = new ArrayList<>();
        List<Domain> domainsArray = new ArrayList<>();

        JSONArray data = new JSONArray(jsonString);
        Module moduleTemp;


        for (int i = 0; i < data.length(); i++) {
            eachObject = data.getJSONObject(i);
            // String  moduleNameNew = eachObject.getString("module_name");
            if ((moduleName.equals("") && i == 0) || !moduleName.equals(eachObject.getString("module_name"))) {
                if (!subModuleName.equals("")) {
                    module.setSections(subModules);
                    module.setNumberOfSections(eachObject.getInt("number_of_sections"));
                    modules.add(module);
                    subModules = new ArrayList<>();
                }
                moduleName = eachObject.getString("module_name");
                module = new Module(moduleName);
            }

            //TODO: empty database or null values cases to be added

            if (moduleName.equals(eachObject.getString("module_name"))) {
                if (subModuleName.equals("") || !subModuleName.equals(eachObject.getString("section_name"))) {
                    if (!question.getQuestionName().equals("")) {
                        subModule.setQuestions(questionsSectionWise);
                        subModule.setDomains(domainsArray);
                        subModule.setModule(module);
                        //TODO: 1. add submodule to questions 2. add domain to questions
                        subModules.add(subModule);
                        questionsSectionWise = new ArrayList<>();
                        domainsArray = new ArrayList<>();
                    }
                    subModuleName = eachObject.getString(("section_name"));
                    subModule = new SubModule(subModuleName, eachObject.getBoolean("section_has_domains"),
                            eachObject.getInt("number_of_domains"));
                }
                if (subModuleName.equals(eachObject.getString("section_name"))) {

                    if (eachObject.getBoolean("section_has_domains")) {

                        if ((!domainName.equals(eachObject.getString("domain_name"))) && !domainName.equals("null")) {
                            domain.setQuestions(questionsDomainWise);
                            domain.setSubModule(subModule);
                            domainsArray.add(domain);
                        }
                        domainName = eachObject.getString("domain_name");
                        domain = new Domain(domainName);
                    }
                }
                if (!question.getQuestionName().equals(parseQuestion(eachObject, subModule, domain).getQuestionName())) {
                    questions.add(question);
                    questionsSectionWise.add(question);
                    questionsDomainWise.add(question);
                } else {
                    Log.d("tag", "Duplicate Question");
                    throw new IllegalArgumentException();
                }

            }

        }

    }


    private Question parseQuestion(JSONObject object, SubModule subModule, Domain domain) throws JSONException {

        //TODO:add null case

        Question question = new Question(object.getString("question_name"));
/*
        AnswerOptions options = new AnswerOptions(object.getString("answer_type"),
                object.getInt("number_of_options"),
                object.getString("option1"),
                object.getString("option2"),
                object.getString("option3"),
                object.getString("option4"),
                object.getString("option5"),
                object.getString("option6"),
                object.getString("option7"),
                object.getString("option8")
        );*/
        /*question.setOptions(getAnswerOptionsForQuestion(object));
        question.setSubModule(subModule);
        question.setSerialNumber(object.getInt("serial_number"));*/
       /* if (!domain.getName().equals("null")) {
            question.setDomain(domain);
        } *///TODO: need a better solution than "null" string for no domain.


        // question.setDomainName(object.getString("domain_name"));

        return question;
    }


}
  /* Set<Module> moduleSet = new HashSet<>();
        Set<SubModule> subModuleSet = new HashSet<>();
        Set<Question> questionSet = new HashSet<>();

        for (int i = 0; i < data.length(); i++) {
            eachObject = data.getJSONObject(i);
            moduleName = eachObject.getString("module_name");
            Module module1 = new Module(moduleName);
            moduleSet.add(module1);
        }

        modules = new ArrayList<>(moduleSet);*/

      /*  for (int i = 0; i < data.length(); i++) {
            eachObject = data.getJSONObject(i);
            subModuleName = eachObject.getString("section_name");
            SubModule subModule1 = new SubModule(subModuleName);

            moduleName = eachObject.getString("module_name");
            for (Module module5 : modules) {
                if (module5.getName().equals(moduleName)) {
                }
                subModule.setModule(module5);
            }
            subModuleSet.add(subModule1);
        }

        subModules = new ArrayList<>(subModuleSet);*/


        /*for (int i = 0; i < data.length(); i++) {

            eachObject = data.getJSONObject(i);
            domainName = eachObject.getString("domain_name");
            Domain domain1 = new Domain(domainName);
            //  domain1.setSubModule(subModule1);
            domainsArray.add(domain1);
        }*/
        /*for (int i = 0; i < data.length(); i++) {

            eachObject = data.getJSONObject(i);
            Question question1 = new Question(eachObject.getString("question_name"));
            domainName = eachObject.getString("domain_name");
            for (Domain domain2 : domainsArray) {
                if (domain2.getName().equals(domainName)) {
                    question1.setDomain(domain2);
                }
            }
            subModuleName = eachObject.getString("section_name");
            for (SubModule subModule2 : subModules) {
                if (subModule2.getName().equals(subModuleName)) {
                    question1.setSubModule(subModule2);
                }
            }
            moduleName = eachObject.getString("module_name");
            for (Module module2 : modules) {
                if (module2.getName().equals(moduleName)) {
                    question1.getSubModule().setModule(module2);
                }
            }
            questions.add(question1);
        }*/




       /* for (Domain domain3 : domainsArray) {
            for (Question question2 : questions) {

                if (question2.getDomain().getName().equals(domain3.getName())) {
                    questionsDomainWise.add(question2);
                }
            }
            domain3.setQuestions(questionsDomainWise);
            domain3.setSubModule(questionsDomainWise.get(0).getSubModule());
        }*/
