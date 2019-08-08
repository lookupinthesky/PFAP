package com.example.pfa_p.Utils;

import android.util.Log;

import com.example.pfa_p.Activities.MainActivity;
import com.example.pfa_p.Model.AnswerOptions;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JSONHelper {

    public JSONHelper(String jsonString) {

        try {
            parseData(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(MainActivity.class.getName(), "Problem in JSON Parsing, Cannot create JSONHelper Object");
        }

    }


    private List<Module> modules;
    private List<Question> questions;


    public List<Module> getModules() {
        return modules;
    }

    public List<Question> getQuestions() {
        return questions;
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
        question.setOptions(options);
        question.setSubModule(subModule);
        question.setSerialNumber(object.getInt("serial_number"));
        if (!domain.getName().equals("null")) {
            question.setDomain(domain);
        } //TODO: need a better solution than "null" string for no domain.


        // question.setDomainName(object.getString("domain_name"));

        return question;
    }

    private List<Domain> domainSectionWise = new ArrayList<>();
    private List<SubModule> sectionsModuleWise = new ArrayList<>();

    private void createSurveyData(String jsonString) throws JSONException {

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


        Set<Module> moduleSet = new HashSet<>();
        Set<SubModule> subModuleSet = new HashSet<>();
        Set<Question> questionSet = new HashSet<>();

        for (int i = 0; i < data.length(); i++) {
            eachObject = data.getJSONObject(i);
            moduleName = eachObject.getString("module_name");
            Module module1 = new Module(moduleName);
            moduleSet.add(module1);
        }

        modules = new ArrayList<>(moduleSet);

        for (int i = 0; i < data.length(); i++) {
            eachObject = data.getJSONObject(i);
            subModuleName = eachObject.getString("section_name");
            SubModule subModule1 = new SubModule(subModuleName);
            moduleName = eachObject.getString("module_name");
            for(Module module5:modules){
                if(module5.getName().equals(moduleName)){
            }
               subModule.setModule(module5);}
            subModuleSet.add(subModule1);
        }

        subModules = new ArrayList<>(subModuleSet);



        for (int i = 0; i < data.length(); i++) {

            eachObject = data.getJSONObject(i);
            domainName = eachObject.getString("domain_name");
            Domain domain1 = new Domain(domainName);
            //  domain1.setSubModule(subModule1);
            domainsArray.add(domain1);
        }
        for (int i = 0; i < data.length(); i++) {

            eachObject = data.getJSONObject(i);
            Question question1 = new Question(eachObject.getString("question_name"));
            domainName = eachObject.getString("domain_name");
            for(Domain domain2: domainsArray){
                if(domain2.getName().equals(domainName)){
                    question1.setDomain(domain2);
                }
            }
            subModuleName = eachObject.getString("section_name");
            for(SubModule subModule2: subModules){
                if (subModule2.getName().equals(subModuleName)){
                    question1.setSubModule(subModule2);
                }
            }
            moduleName = eachObject.getString("module_name");
            for(Module module2: modules){
                if(module2.getName().equals(moduleName)){
                    question1.getSubModule().setModule(module2);
                }
            }
            questions.add(question1);
        }


        for(Domain domain3: domainsArray) {
            for (Question question2 : questions) {

                if (question2.getDomain().getName().equals(domain3.getName())){
                    questionsDomainWise.add(question2);
                }
            }
            domain3.setQuestions(questionsDomainWise);
            domain3.setSubModule(questionsDomainWise.get(0).getSubModule());
        }
        for(SubModule submodule3: subModules) {
            for (Question question2 : questions) {
                if (question2.getSubModule().getName().equals(submodule3.getName())){
                    questionsSectionWise.add(question2);
                }
            }
            submodule3.setQuestions(questionsSectionWise);

            for(Domain domain4 : domainsArray){
                if(domain4.getSubModule().getName().equals(submodule3.getName())){
                    domainSectionWise.add(domain4);
                }
            }
            submodule3.setDomains(domainSectionWise);
        }

        for(Module module3: modules){

            for(SubModule subModule6: subModules){
                if(subModule6.getModule().getName().equals(module3.getName())){
                    sectionsModuleWise.add(subModule6);
                }
            }
            module3.setSections(sectionsModuleWise);
        }


    }


}
