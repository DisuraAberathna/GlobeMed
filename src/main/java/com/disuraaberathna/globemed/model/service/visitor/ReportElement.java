package com.disuraaberathna.globemed.model.service.visitor;

public interface ReportElement {
    void accept(ReportVisitor visitor);
}
