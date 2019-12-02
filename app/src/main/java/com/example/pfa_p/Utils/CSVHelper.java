package com.example.pfa_p.Utils;

import android.util.Log;

import com.example.pfa_p.Activities.DashboardActivity;
import com.example.pfa_p.Model.AnswerOptions;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CSVHelper {

    public List<Module> getModules() {
        return modules;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public List<SubModule> getSections() {
        return subModules;
    }

    private List<Module> modules;
    private List<SubModule> subModules;
    private List<Domain> domains;
    private List<Question> questions;
    private List<Domain> domainSectionWise;
    private List<Question> questionsSectionWise;
    private List<Question> questionsDomainWise;
    private List<SubModule> sectionsModuleWise;


    public CSVHelper(InputStream inputStream){
        try {
            createSurveyData(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(DashboardActivity.class.getName(), "Problem in CSV Parsing, Cannot create CSVHelper Object");
        }
    }

    private void createSurveyData(InputStream inputStream) throws IOException {
        intializeArrays();
        Reader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader reader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();
        createModulesArray(reader);
        createSubModulesArray(reader);
        createDomainsArray(reader);
        createQuestionsArray(reader, domains, subModules, modules);
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

    /*private void readCSV(Context context) {

        InputStream inputStream = context.getResources().openRawResource(R.raw.survey);
        Reader inputStreamReader = new InputStreamReader(inputStream);
        CSVReader reader = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();
        String[] data = null;
        try {
            while ((data = reader.readNext()) != null) {


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    private void createCSV() {

    }

    private void createModulesArray(CSVReader reader) throws IOException {
        Set<Module> moduleSet = new LinkedHashSet<>();
        String[] data = null;
        while ((data = reader.readNext()) != null) {
            String moduleName = data[0];
            Module module1 = new Module(moduleName);
            moduleSet.add(module1);
        }
        modules = new ArrayList<>(moduleSet);
        for (Module module : modules) {
            module.setIndex(modules.indexOf(module));
            if (module.getName().equals("Assessing Need For PFA-P")) {
                module.setResultBased(true);
            }
        }
    }

    private void createSubModulesArray(CSVReader reader) throws IOException {
        String[] data = null;
        Set<SubModule> subModuleSet = new LinkedHashSet<>();
        // int count = 0;
        while ((data = reader.readNext()) != null) {
            String moduleName = data[0];
            //   Module module1 = new Module(moduleName);
            String subModuleName = data[2];
            SubModule subModule = new SubModule(subModuleName);

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

    private void createDomainsArray(CSVReader reader) throws IOException {

        String[] data = null;


        Set<Domain> domainSet = new LinkedHashSet<>();
        //    int count = 0;
        while ((data = reader.readNext()) != null) {

            String domainName = data[6];
            if (!domainName.equals("null")) {
                Domain domain = new Domain(domainName);

                domainSet.add(domain);
            }
        }
        domains = new ArrayList<>(domainSet);

    }

    private void createQuestionsArray(CSVReader reader, List<Domain> domains, List<SubModule> subModules, List<Module> modules) throws IOException {

        String[] data = null;
        while ((data = reader.readNext()) != null) {
            Question question = new Question(data[10]);
            String domainName = data[6];
            for (Domain domain : domains) {
                if (domain.getName().equals(domainName)) {
                    question.setDomain(domain);
                }
            }
            String subModuleName = data[2];
            for (SubModule subModule : subModules) {
                if (subModule.getName().equals(subModuleName)) {
                    question.setSubModule(subModule);
                }
            }
            String moduleName = data[0];
            for (Module module : modules) {
                if (module.getName().equals(moduleName)) {
                    question.getSubModule().setModule(module);
                }
            }

            question.setOptions(getAnswerOptionsForQuestion(data));
            question.setSerialNumber(Integer.valueOf(data[9]));
            questions.add(question);

        }

    }


    private AnswerOptions getAnswerOptionsForQuestion(String[] data) {
        AnswerOptions options = new AnswerOptions(data[7],
                Integer.valueOf(data[8]),
                data[11],
                data[12],
                data[13],
                data[14],
                data[15],
                data[16],
                data[17],
                data[18]
        );
        return options;
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

}
