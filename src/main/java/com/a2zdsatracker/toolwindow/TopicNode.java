package com.a2zdsatracker.toolwindow;

import com.a2zdsatracker.model.Topic;
import com.intellij.ui.CheckedTreeNode;

public class TopicNode extends CheckedTreeNode {

    private final Topic topic;

    public TopicNode(Topic topic) {
        super(topic.getDisplayName());
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}