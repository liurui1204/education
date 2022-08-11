package com.mohe.nanjinghaiguaneducation.modules.performance.entity;

import lombok.Data;

import java.util.List;

@Data
public class EduStudyTree {

    private String name;
    private String rate;
    private String treeCode;
    private List<EduStudyTree> children;

    private Integer passRate;
}
