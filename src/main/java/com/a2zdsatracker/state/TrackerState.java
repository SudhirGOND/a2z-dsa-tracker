package com.a2zdsatracker.state;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import java.util.HashMap;
import java.util.Map;

@State(
        name = "A2ZTrackerState",
        storages = @Storage("a2z-dsa-tracker.xml")
)
public class TrackerState implements PersistentStateComponent<TrackerState> {

    public Map<String, Boolean> solvedProblems = new HashMap<>();
    public Map<String, String> problemNotes = new HashMap<>();

    public static TrackerState getInstance() {
        return ApplicationManager.getApplication().getService(TrackerState.class);
    }

    public Map<String, Boolean> getSolvedProblems() {
        return solvedProblems;
    }

    public Map<String, String> getProblemNotes() {
        return problemNotes;
    }

    @Override
    public TrackerState getState() {
        return this;
    }

    @Override
    public void loadState(TrackerState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}