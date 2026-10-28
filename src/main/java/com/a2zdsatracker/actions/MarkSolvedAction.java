package com.a2zdsatracker.actions;

import com.a2zdsatracker.model.Problem;
import com.a2zdsatracker.service.ProblemRepository;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class MarkSolvedAction extends AnAction {

    private final Problem problem;
    private final ProblemRepository repository;

    public MarkSolvedAction(Problem problem, ProblemRepository repository) {
        super(problem.isSolved() ? "Mark as Unsolved" : "Mark as Solved");
        this.problem = problem;
        this.repository = repository;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            repository.markSolved(problem, !problem.isSolved());
        }
    }
}
