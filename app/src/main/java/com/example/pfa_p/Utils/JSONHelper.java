package com.example.pfa_p.Utils;

import android.util.Log;

import com.example.pfa_p.Activities.MainActivity;
import com.example.pfa_p.Model.AnswerOptions;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
                } /*else if (subModuleName.equals(""))||){

                    Log.d("tag", "dne");
                }*/


                moduleName = eachObject.getString("module_name");
                module = new Module(moduleName);


            }

            //TODO: empty database or null values cases to be added

            if (moduleName.equals(eachObject.getString("module_name"))) {
                if (subModuleName.equals("") || !subModuleName.equals(eachObject.getString("section_name"))) {
                    if (!question.getQuestionName().equals("")) {
                        subModule.setQuestions(questionsSectionWise);
                        subModules.add(subModule);
                        questionsSectionWise = new ArrayList<>();
                    } /*else if (!subModuleName.equals(eachObject.getString("section_name"))) {
                        Log.d(TAG, "Empty or Duplicate Question");
                    }*/
                    /*else{
                        throw new IllegalArgumentException("Data Incomplete");
                    }*/
                    subModuleName = eachObject.getString(("section_name"));
                    subModule = new SubModule(subModuleName, eachObject.getBoolean("section_has_domains"),
                            eachObject.getInt("number_of_domains"));
                }
                if (!question.getQuestionName().equals(parseQuestion(eachObject).getQuestionName())) {
                    questions.add(question);
                    questionsSectionWise.add(question);
                }
                else{
                    Log.d("tag", "Duplicate Question");
                    throw new IllegalArgumentException();
                }

            }

        }
    }


    private Question parseQuestion(JSONObject object) throws JSONException {

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
        question.setSubModuleName(object.getString("section_name"));
        question.setSerialNumber(object.getInt("serial_number"));
        question.setDomainName(object.getString("domain_name"));

        return question;
    }


}
