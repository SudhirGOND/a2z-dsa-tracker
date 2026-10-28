package com.a2zdsatracker.toolwindow;

import com.a2zdsatracker.model.Problem;
import com.a2zdsatracker.model.Topic;
import com.a2zdsatracker.service.ProblemRepository;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.CheckedTreeNode;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class A2ZToolWindowFactory implements ToolWindowFactory {

    private static final String TAG_GAP = "        ";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ProblemRepository repository = new ProblemRepository();
        List<Problem> problems = repository.getAll();

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        JLabel progressLabel = new JLabel();
        JProgressBar progressBar = new JProgressBar(0, problems.size());
        progressBar.setStringPainted(false);

        Runnable refreshProgress = () -> {
            long solved = problems.stream().filter(Problem::isSolved).count();
            progressBar.setValue((int) solved);
            int percent = problems.isEmpty() ? 0 : (int) (solved * 100 / problems.size());
            progressLabel.setText(solved + " / " + problems.size() + " solved  (" + percent + "%)");
        };
        refreshProgress.run();

        headerPanel.add(progressLabel, BorderLayout.NORTH);
        headerPanel.add(progressBar, BorderLayout.CENTER);

        CheckedTreeNode root = buildTreeRoot(problems);

        CheckboxTree tree = new CheckboxTree(new CheckboxTree.CheckboxTreeCellRenderer() {

            @Override
            public void customizeRenderer(JTree jTree, Object value, boolean selected,
                                          boolean expanded, boolean leaf, int row, boolean hasFocus) {

                // 1. PROBLEM: title + difficulty tag
                if (value instanceof ProblemNode problemNode) {
                    Problem problem = problemNode.getProblem();
                    getTextRenderer().append(problem.getTitle());
                    appendDifficultyTag(getTextRenderer(), problem.getDifficulty());
                    return;
                }

                // 2. CATEGORY: name + solved count + difficulty tag
                if (value instanceof CategoryNode node) {
                    int total = node.getChildCount();
                    int solved = 0;
                    String difficulty = null;

                    for (int i = 0; i < total; i++) {
                        if (node.getChildAt(i) instanceof ProblemNode child) {
                            if (child.isChecked()) solved++;
                            if (difficulty == null) {
                                difficulty = child.getProblem().getDifficulty();
                            }
                        }
                    }

                    Object label = node.getUserObject();
                    String categoryName = label == null ? "" : label.toString();

                    getTextRenderer().append(
                            categoryName + "  (" + solved + "/" + total + ")",
                            SimpleTextAttributes.GRAYED_ATTRIBUTES
                    );
                    appendDifficultyTag(getTextRenderer(), difficulty);
                    return;
                }

                // 3. TOPIC: name + total solved count
                if (value instanceof TopicNode node) {
                    int total = 0;
                    int solved = 0;

                    for (int i = 0; i < node.getChildCount(); i++) {
                        if (node.getChildAt(i) instanceof CategoryNode categoryNode) {
                            for (int j = 0; j < categoryNode.getChildCount(); j++) {
                                if (categoryNode.getChildAt(j) instanceof ProblemNode child) {
                                    total++;
                                    if (child.isChecked()) solved++;
                                }
                            }
                        }
                    }

                    getTextRenderer().append(
                            node.getTopic().getDisplayName() + "  (" + solved + "/" + total + ")",
                            SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES
                    );
                    return;
                }

                // 4. ROOT / OTHER NODES
                if (value instanceof CheckedTreeNode node) {
                    Object object = node.getUserObject();
                    if (object != null) {
                        getTextRenderer().append(object.toString());
                    }
                }
            }

        }, root);

        tree.addCheckboxTreeListener(new com.intellij.ui.CheckboxTreeListener() {
            @Override
            public void nodeStateChanged(@NotNull CheckedTreeNode node) {
                if (node instanceof ProblemNode problemNode) {
                    Problem p = problemNode.getProblem();
                    repository.markSolved(p, node.isChecked());
                    refreshProgress.run();
                    tree.repaint();
                }
            }
        });

        tree.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }

                Object selectedNode = tree.getLastSelectedPathComponent();

                if (selectedNode instanceof ProblemNode problemNode) {
                    ProblemLinkPopup.show(problemNode.getProblem(), tree);
                }
            }
        });

        tree.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = tree.getRowForLocation(e.getX(), e.getY());
                if (row < 0) {
                    tree.setCursor(Cursor.getDefaultCursor());
                    tree.setToolTipText(null);
                    return;
                }

                TreePath path = tree.getPathForRow(row);
                if (path == null) {
                    tree.setCursor(Cursor.getDefaultCursor());
                    tree.setToolTipText(null);
                    return;
                }

                Rectangle bounds = tree.getRowBounds(row);
                boolean overCheckbox = e.getX() < bounds.x + 20;
                Object node = path.getLastPathComponent();

                if (node instanceof ProblemNode pn && !overCheckbox) {
                    tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    tree.setToolTipText("Double-click to open links: " + pn.getProblem().getTitle());
                } else {
                    tree.setCursor(Cursor.getDefaultCursor());
                    tree.setToolTipText(null);
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JBScrollPane(tree), BorderLayout.CENTER);

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    // ---------- Difficulty helpers ----------

    private static void appendDifficultyTag(
            com.intellij.ui.SimpleColoredComponent target, String difficulty) {

        JBColor color = difficultyColor(difficulty);
        if (color == null) {
            return;
        }

        target.append(
                TAG_GAP + difficulty,
                new SimpleTextAttributes(SimpleTextAttributes.STYLE_BOLD, color)
        );
    }

    private static JBColor difficultyColor(String difficulty) {
        if (difficulty == null) {
            return null;
        }

        switch (difficulty) {
            case "Easy":
                return new JBColor(new Color(0, 150, 70), new Color(100, 220, 140));
            case "Medium":
                return new JBColor(new Color(190, 105, 0), new Color(255, 180, 80));
            case "Hard":
                return new JBColor(new Color(190, 35, 35), new Color(255, 110, 110));
            default:
                return null;
        }
    }

    // ---------- Tree building ----------

    private CheckedTreeNode buildTreeRoot(List<Problem> problems) {
        CheckedTreeNode root = new CheckedTreeNode("A2Z DSA Sheet");

        for (Topic topic : Topic.values()) {
            LinkedHashMap<String, List<Problem>> byCategory = new LinkedHashMap<>();

            for (Problem p : problems) {
                if (p.getTopic().equals(topic.getDisplayName())) {
                    String cat = (p.getCategory() != null && !p.getCategory().isEmpty())
                            ? p.getCategory() : "General";
                    byCategory.computeIfAbsent(cat, k -> new ArrayList<>()).add(p);
                }
            }

            if (byCategory.isEmpty()) continue;

            TopicNode topicNode = new TopicNode(topic);
            for (var entry : byCategory.entrySet()) {
                CategoryNode categoryNode = new CategoryNode(entry.getKey());
                for (Problem p : entry.getValue()) {
                    categoryNode.add(new ProblemNode(p));
                }
                topicNode.add(categoryNode);
            }
            root.add(topicNode);
        }
        return root;
    }
}