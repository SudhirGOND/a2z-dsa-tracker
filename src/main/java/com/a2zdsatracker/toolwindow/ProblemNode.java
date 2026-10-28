package com.a2zdsatracker.toolwindow;

import com.a2zdsatracker.model.Problem;
import com.intellij.ui.CheckedTreeNode;

public class ProblemNode extends CheckedTreeNode {

    private final Problem problem;

    public ProblemNode(Problem problem) {
        super(problem.getTitle());
        this.problem = problem;
        setChecked(problem.isSolved());
    }

    public Problem getProblem() {
        return problem;
    }
}