package com.a2zdsatracker.actions;

import com.a2zdsatracker.model.Problem;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class OpenInBrowserAction extends AnAction {

    private final Problem problem;

    public OpenInBrowserAction(Problem problem) {
        super("Open Problem in Browser");
        this.problem = problem;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        String url = null;

        if (problem.getArticleLink() != null && !problem.getArticleLink().isBlank()) {
            url = problem.getArticleLink();
        } else if (problem.getGfgLink() != null && !problem.getGfgLink().isBlank()) {
            url = problem.getGfgLink();
        } else if (problem.getLeetCodeLink() != null && !problem.getLeetCodeLink().isBlank()) {
            url = problem.getLeetCodeLink();
        } else if (problem.getYouTubeLink() != null && !problem.getYouTubeLink().isBlank()) {
            url = problem.getYouTubeLink();
        }

        if (url != null) {
            BrowserUtil.browse(url);
        }

    }
}