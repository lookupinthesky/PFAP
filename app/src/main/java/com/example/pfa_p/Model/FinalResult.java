package com.example.pfa_p.Model;

import java.util.ArrayList;
import java.util.List;

public class FinalResult  {

    ResultAbstractModel sectionResult;

    public ResultAbstractModel getSectionResult() {
        return sectionResult;
    }

    public void setSectionResult(ResultAbstractModel sectionResult) {
        this.sectionResult = sectionResult;
    }

    public List<ResultAbstractModel> getDomainResult() {
        return domainResult;
    }

    public void setDomainResult(List<ResultAbstractModel> domainResult) {
        this.domainResult = domainResult;
    }

    List<ResultAbstractModel> domainResult ;

    public FinalResult(){
        domainResult = new ArrayList<>();
    }

}
