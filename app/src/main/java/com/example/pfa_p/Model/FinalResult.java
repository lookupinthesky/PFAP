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

    public List<DomainResultModel> getDomainResult() {
        return domainResult;
    }

    public void setDomainResult(List<DomainResultModel> domainResult) {
        this.domainResult = domainResult;
    }

    List<DomainResultModel> domainResult ;

    public FinalResult(){
        domainResult = new ArrayList<>();
    }

}
