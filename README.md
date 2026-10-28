# DSA Sheet Tracker

Track your Data Structures and Algorithms sheet progress without leaving your IDE.

DSA Sheet Tracker adds a tool window to IntelliJ IDEA that lists the problems of Striver's A2Z DSA Sheet, grouped by topic and category. Tick problems as you solve them, see your progress at a glance, and jump to the article, GeeksforGeeks, LeetCode or YouTube resource for any problem.

<!-- Add screenshots here (light and dark theme):
![Tool window](docs/screenshots/tool-window-dark.png)
![Tool window light](docs/screenshots/tool-window-light.png)
-->

## Features

- **Three-level tree:** Topic → Category → Problem, with solved counts (`x / y`) at every level.
- **Overall progress bar** showing solved problems out of the full sheet.
- **Easy / Medium / Hard tags** next to categories and problems.
- **Quick resource links:** double-click a problem to open a popup with its Article, GeeksforGeeks, LeetCode and YouTube links (only the links that exist for that problem are shown).
- **Partial progress states:** tick a whole category or topic, or tick problems one by one, and parent checkboxes update automatically.
- **Saved across projects:** your progress is stored once for the IDE, so it stays the same in every project you open.
- **Theme aware:** colors adapt to light and dark IDE themes.

## Installation

### From JetBrains Marketplace

1. Open `Settings | Plugins | Marketplace`.
2. Search for **DSA Sheet Tracker**.
3. Click **Install** and restart the IDE if asked.

### From disk

1. Download the plugin `.zip` from the [Releases](https://github.com/<your-username>/<your-repository>/releases) page.
2. Open `Settings | Plugins`, click the gear icon, and choose **Install Plugin from Disk...**.
3. Select the downloaded `.zip` file and restart the IDE.

## Usage

1. Open the **DSA Sheet Tracker** tool window from the right sidebar.
2. Expand a topic, then a category, to see its problems.
3. Tick the checkbox next to a problem when you solve it.
4. Double-click a problem to open its resource links.

Your progress is saved automatically.

## Compatibility

Built and tested against IntelliJ IDEA 2025.3. The plugin only uses the IntelliJ Platform, so it is intended to work in other IntelliJ-based IDEs that support this platform version.

## Privacy

All data stays on your machine. The plugin does not collect analytics and does not send your progress anywhere. Progress is stored in a local file named `a2z-dsa-tracker.xml` in your IDE's configuration directory. Links open in your default browser only when you choose to open them.

## Data source and credits

The problem list is based on **Striver's A2Z DSA Sheet** by [takeUforward](https://takeuforward.org/dsa/strivers-a2z-sheet-learn-dsa-a-to-z), used with the permission of its authors. All credit for the curriculum and problem selection goes to them.

Difficulty tags are assigned per category and are an approximation. An individual problem may be rated differently on LeetCode or GeeksforGeeks.

Problem titles and links point to their original sources (LeetCode, GeeksforGeeks, YouTube and takeUforward). This plugin does not host or copy their problem statements or solutions.

## Project structure

```
src/main
├── java/com/a2zdsatracker
│   ├── model/        Problem and related data classes
│   ├── state/        Persistent progress storage (TrackerState)
│   ├── service/      Loads the problem list and applies saved progress
│   ├── toolwindow/   Tool window, tree nodes, renderer, link popup
│   └── actions/      IDE actions
└── resources
    ├── problems.json     Problem dataset
    └── META-INF
        ├── plugin.xml        Plugin configuration
        └── pluginIcon.svg    Plugin logo
```

## Building from source

Requirements: a JDK supported by the IntelliJ Platform version configured in `build.gradle.kts`.

```bash
# Run a sandbox IDE with the plugin installed
./gradlew runIde

# Check compatibility against the configured IDE versions
./gradlew verifyPlugin

# Build the plugin distribution (.zip in build/distributions)
./gradlew buildPlugin
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Roadmap

- Search and filter problems
- Expand all / collapse all
- Personal notes per problem
- Export and import progress
- Progress statistics and streaks

Feature requests are welcome through the issue tracker.

## Contributing

Bug reports and suggestions are welcome. Please open an issue and include your IDE version, the plugin version, and steps to reproduce the problem. For code changes, open an issue first to discuss what you would like to change.

## License

The plugin source code is released under the [MIT License](LICENSE).

The problem dataset and the A2Z sheet structure belong to their original authors and are used with their permission. They are not covered by the MIT License of this repository.

## Disclaimer

This plugin is an independent tool. JetBrains, IntelliJ IDEA, LeetCode, GeeksforGeeks, YouTube and takeUforward are trademarks of their respective owners.
