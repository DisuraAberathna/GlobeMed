package com.disuraaberathna.globemed.model.service.memento;

import java.util.Stack;

public class PatientCaretaker {
    private final Stack<PatientMemento>  patientStack = new Stack<>();

    public void save(PatientMemento patientMemento) {
        patientStack.push(patientMemento);
    }

    public PatientMemento undo(){
        if(!patientStack.isEmpty()){
            return patientStack.pop();
        }

        return null;
    }
}
