package com.example.pfa_p.Utils;

import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;

import java.util.List;

public interface RatingSystem {


    String RESULT_NO_INTERVENTION_REQUIRED = "Do not require the intervention of PFA-P";
    String RESULT_INTERVENTION_REQUIRED ="Require the intervention of PFA-P" ;
    String RESULT_INTERVETION_REQUIRED_WITH_FOLLOW_UP = "Requires the intervention of PFA-P with follow-ups";
    String RESULT_NEED_REFERRAL = "Needs immediate attention and referral";
    String RESULT_PERSONALITY_TRAITS_NORMAL = "Normal";
    String RESULT_PERSONALITY_SLIGHTLY_ANTISOCIAL = "Slightly antisocial";
    String RESULT_PERSONALITY_PROMINENT_ANTISOCIAL = "Having Prominent anti-social traits";
    String RESULT_NO_RESPONSE = "Very less or no response (Require PFA - P and/or referral";
    String RESULT_NORMAL_RESPONSE = "Normal response";
    String RESULT_OVER_EXPRESSIVE = "Over Expressive (Require PFA - P and/or referral)";
    String RESULT_MILD_DESPONDENCY = "Mild Despondency (Require PFA-P)" ;
    String RESULT_SEVERe_DESPONDENCY = "Moderate to Severe Despondency (Require referral and/or PFA-P)";

    String RESULT_NORMAL = "Normal";
    String RESULT_MILD = "Mild";
    String RESULT_MODERATE = "Moderate";
    String RESULT_SEVERE = "Severe";
    String RESULT_SATISFACTORY = "Satisfactory";
    String RESULT_CONSIDERABLE_SUPPORT = "Considerable Support";
    String RESULT_SLIGHT_SUPPORT = "Slight Support";
    String RESULT_POOR_SUPPORT = "Poor Support";


     float getMeanScore();






}
