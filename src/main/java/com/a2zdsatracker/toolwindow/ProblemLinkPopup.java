package com.a2zdsatracker.toolwindow;

import com.a2zdsatracker.model.Problem;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemLinkPopup {

    public static void show(Problem problem, Component owner) {

        List<LinkItem> links = new ArrayList<>();

        if (problem.getArticleLink() != null && !problem.getArticleLink().isBlank()) {
            links.add(new LinkItem("📖  Article", problem.getArticleLink()));
        }

        if (problem.getGfgLink() != null && !problem.getGfgLink().isBlank()) {
            links.add(new LinkItem("💻  GeeksForGeeks", problem.getGfgLink()));
        }

        if (problem.getLeetCodeLink() != null && !problem.getLeetCodeLink().isBlank()) {
            links.add(new LinkItem("🟧  LeetCode", problem.getLeetCodeLink()));
        }

        if (problem.getYouTubeLink() != null && !problem.getYouTubeLink().isBlank()) {
            links.add(new LinkItem("▶  YouTube", problem.getYouTubeLink()));
        }

        if (links.isEmpty()) {
            JOptionPane.showMessageDialog(
                    owner,
                    "No platform link is available.",
                    "A2Z DSA Tracker",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }


        DefaultListModel<String> model = new DefaultListModel<>();
        for (LinkItem link : links) {
            model.addElement(link.name);
        }

        JBList<String> list = new JBList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() != 2) return;

                int index = list.locationToIndex(e.getPoint());
                if (index >= 0 && index < links.size()) {
                    BrowserUtil.browse(links.get(index).url);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel(problem.getTitle());
        title.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JBScrollPane(list), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(300, 180));

        JBPopupFactory.getInstance()
                .createComponentPopupBuilder(panel, list)
                .setTitle("Open Problem")
                .setResizable(false)
                .setMovable(true)
                .setRequestFocus(true)
                .createPopup()
                .showInCenterOf(owner);
    }

    private static class LinkItem {
        private final String name;
        private final String url;

        private LinkItem(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}