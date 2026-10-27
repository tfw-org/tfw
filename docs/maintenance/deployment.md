# TFW Release Guide

Follow these steps to release TFW using the GitHub web interface.

## 1. Update the version for the release

1. Open `pom.xml` in the GitHub repository.
2. Ensure that the `master` branch is selected by checking the branch shown in the left column.
3. Press the pencil icon on the right to edit `pom.xml`.
4. Remove `-SNAPSHOT` from the version field.
5. Press **Commit Changes** in the upper-right corner.
6. Change the commit message to `Release <version>`, replacing `<version>` with the version specified in `pom.xml`.
7. Make sure **Commit directly to master branch** is selected.
8. Press **Commit Changes**.

## 2. Create and publish the GitHub release

9. Return to the main project screen.
10. Press **Releases** on the right side.
11. Press **Draft a new release** in the upper-right corner.
12. Press **Choose a tag**.
13. Enter the new `<version>`.
14. Press **Create new tag: <version>**.
15. Ensure the target is `master`.
16. Enter `<version>` in the **Release Title** field.
17. Press **Generate release notes**.
18. Ensure **Set as a pre-release** is unchecked.
19. Ensure **Set as the latest release** is checked.
20. Press **Publish release**.
21. Open **Actions** and check that the release workflow completed successfully.

## 3. Prepare for the next development iteration

22. Return to the main project screen and open `pom.xml`.
23. Ensure that the `master` branch is selected by checking the branch shown in the left column.
24. Press the pencil icon on the right to edit `pom.xml`.
25. Increment the version and add `-SNAPSHOT` to the version field.
26. Press **Commit Changes** in the upper-right corner.
27. Change the commit message to `Prepare for next development iteration`.
28. Make sure **Commit directly to master branch** is selected.
29. Press **Commit Changes**.
