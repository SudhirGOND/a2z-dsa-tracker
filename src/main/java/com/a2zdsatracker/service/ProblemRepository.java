package com.a2zdsatracker.service;

import com.a2zdsatracker.model.Problem;
import com.a2zdsatracker.state.TrackerState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProblemRepository {

    private final List<Problem> problems;

    public ProblemRepository() {
        this.problems = loadProblems();
    }

    private List<Problem> loadProblems() {
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("problems.json")) {

            if (inputStream == null) {
                throw new RuntimeException("problems.json not found");
            }

            try (InputStreamReader reader =
                         new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

                Type listType = new TypeToken<List<Problem>>() {}.getType();
                List<Problem> loaded = new Gson().fromJson(reader, listType);

                if (loaded == null) {
                    return new ArrayList<>();
                }

                applySavedState(loaded);
                return loaded;
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load problems.json", e);
        }
    }

    private void applySavedState(List<Problem> list) {
        TrackerState state = TrackerState.getInstance();

        for (Problem problem : list) {
            problem.setSolved(
                    state.getSolvedProblems().getOrDefault(problem.getId(), false)
            );
            problem.setNotes(
                    state.getProblemNotes().getOrDefault(problem.getId(), "")
            );
        }
    }

    public List<Problem> getAll() {
        return problems;
    }

    public void markSolved(Problem problem, boolean solved) {
        problem.setSolved(solved);
        TrackerState.getInstance().getSolvedProblems().put(problem.getId(), solved);
    }

    public void saveNote(Problem problem, String note) {
        problem.setNotes(note);
        TrackerState.getInstance().getProblemNotes().put(problem.getId(), note);
    }
}