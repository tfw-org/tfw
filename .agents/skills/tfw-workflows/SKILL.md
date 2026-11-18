---
name: tfw-workflows
description: Understand, maintain, troubleshoot, and improve GitHub Actions workflows and CI/CD automation for the TFW Java framework. Use when modifying workflow files, investigating workflow failures, changing automation, or working with builds, security checks, fuzzing, GitHub Pages, and releases.
---

# TFW Workflows

## Purpose

Guide AI agents in understanding, maintaining, troubleshooting,
and improving the GitHub Actions automation used by the TFW
Java framework.

The repository's actual workflow files, Maven configuration,
and maintenance documentation are authoritative. Do not assume
that a workflow behaves as expected merely from its name or
description. Inspect its triggers, permissions, jobs, conditions,
steps, dependencies, and outputs before changing it.

Workflow changes can affect build reliability, repository
security, release integrity, and published project artifacts.
Prefer focused changes that preserve existing safeguards.

## Before Modifying Workflows

1. Identify the workflow or automation involved.
2. Read the complete relevant workflow file.
3. Inspect related workflows that consume its artifacts,
   trigger from its completion, or otherwise depend on it.
4. Check the applicable Maven configuration and maintenance
   documentation.
5. Understand the event that triggers the workflow and the
   context in which it executes.
6. Identify required permissions, secrets, artifacts, and
   external services.
7. Review recent workflow runs when investigating an existing
   problem.
8. Determine whether the requested change affects security,
   release publishing, or other consequential operations.

Do not modify a workflow based only on an error message or
a suggested fix without understanding the relevant execution
path.

For implementation and testing conventions, consult
`tfw-development` and `tfw-testing`.

For release procedures, consult
`docs/maintenance/deployment.md`.

## General GitHub Actions Practices

### Keep changes focused

- Make the smallest change that correctly addresses the
  requirement.
- Preserve existing triggers and conditions unless changing
  them is intentional.
- Avoid duplicating jobs or steps when an existing workflow
  can be extended appropriately.
- Preserve useful concurrency controls.
- Avoid unnecessary workflow dependencies.
- Do not introduce additional actions or external services
  without a clear benefit.
- Keep workflow names and job names descriptive.

Inspect the complete workflow after making changes. A small
YAML edit can have effects beyond the individual line changed.

### Pin third-party actions

TFW pins GitHub Actions to full commit SHAs and includes
version comments.

Follow this convention:

```yaml
- uses: actions/checkout@<full-commit-sha> # vX.Y.Z
```

When adding or updating an action:

1. Identify the official upstream repository.
2. Verify the release or version being selected.
3. Resolve the corresponding full commit SHA.
4. Pin the action to that SHA.
5. Include a comment identifying the release version.
6. Check whether the action has relevant security advisories
   or compatibility concerns.

Do not use a mutable version tag when the repository's
pinned-SHA convention applies.

Do not invent commit SHAs or assume that a version comment
proves the SHA corresponds to that release.

When updating an action, consider its inputs, outputs,
permissions, runner requirements, and breaking changes.

### Use least-privilege permissions

Declare workflow-level permissions explicitly.

Use the minimum permissions needed by each workflow and,
where appropriate, narrow them further at the job level.

For example:

```yaml
permissions:
  contents: read
```

If a job needs to publish results or update a pull request,
grant only the additional permissions required by that job.

Examples include:

- `pull-requests: write` for pull request updates.
- `checks: write` for check-run updates.
- `security-events: write` for security analysis uploads.
- `pages: write` and `id-token: write` for GitHub Pages deployment.
- `contents: write` for operations that must modify repository
  contents or release assets.

Do not grant write permissions at workflow scope when only
one job needs them.

Do not remove permissions that are necessary for an existing
operation without understanding the consequences.

Do not add broad permissions merely to make an authentication
or publishing failure disappear.

### Handle credentials and secrets carefully

- Never print secret values in workflow logs.
- Never commit tokens, passwords, private keys, or other
  credentials.
- Pass secrets through environment variables or supported
  action inputs rather than embedding them in command text.
- Avoid exposing credentials to untrusted pull request code.
- Use the built-in `GITHUB_TOKEN` when it provides the required
  access.
- Use a separate secret or token only when the operation
  genuinely requires permissions beyond the built-in token.
- Keep token permissions and repository access as narrow
  as practical.
- Never assume a secret exists or has the expected permissions
  without checking the relevant configuration or run output.

For checkout steps that do not need authenticated Git operations
after checkout, preserve the repository's use of:

```yaml
with:
  persist-credentials: false
```

Do not weaken credential handling without a specific,
understood requirement.

Never request, display, or include the value of a repository
secret in a pull request or workflow modification.

### Treat pull request code as untrusted

Pull requests can contain code and workflow changes that
must not automatically receive privileged repository access.

Pay particular attention to:

- Fork pull requests.
- `pull_request_target` workflows.
- `workflow_run` workflows.
- Downloaded artifacts produced by pull request workflows.
- Commands that execute scripts or binaries from a pull request.
- Actions that receive write-capable tokens.

Do not execute untrusted pull request code in a privileged
workflow.

A workflow using `workflow_run` requires particular care:
the follow-up workflow may run with a different security
context and permissions from the original pull request run.

When consuming artifacts from an untrusted workflow:

- Treat artifact contents as untrusted input.
- Validate expected files and paths.
- Do not execute downloaded scripts or binaries.
- Avoid checking out or executing the pull request's source
  code in a privileged follow-up workflow.
- Keep permissions limited to the operation being performed.

TFW's `pitest-update-pr.yml` is an intentional example of
a privileged follow-up workflow. It downloads PIT results
and updates the pull request, but does not check out or
execute pull request code.

Preserve that security boundary when modifying the PIT
automation.

## TFW Continuous Integration

### Main Maven build

The primary build workflow is:

`.github/workflows/maven.yml`

It runs on pushes and pull requests targeting `master`.

The current build matrix includes:

- Java 21 and Java 25.
- Zulu, Temurin, and Corretto distributions.

The workflow:

1. Checks out the repository.
2. Sets up the selected JDK.
3. Installs `tightvncserver`.
4. Runs the Maven verification build through
   `execute-on-vnc.sh`.
5. Checks whether the build modified tracked files.

The build command is:

```bash
./mvnw -B verify
```

The CI invocation is:

```bash
./execute-on-vnc.sh ./mvnw -B verify
```

The VNC setup is important because TFW includes GUI-related
tests. Do not remove the VNC setup or bypass the wrapper
without understanding the affected tests.

The Maven build also checks for unintended modifications
to tracked files. Do not remove this check merely because
a build produces generated changes. Investigate why the
files changed and whether the build is expected to modify
them.

Remember that TFW targets Java 8 bytecode even though the
current development and CI builds use newer JDKs.

### Maven and Java configuration

Use the Maven Wrapper rather than assuming a particular
system Maven installation.

The authoritative configuration is maintained in:

- `pom.xml`
- `.mvn/wrapper/maven-wrapper.properties`
- `.github/workflows/maven.yml`

When changing the Java matrix, distinguish the JDK used
to run Maven from the Java version targeted by the compiled
library.

When changing Maven or JDK versions, consider:

- Maven Wrapper compatibility.
- Compiler source, target, and release settings.
- Maven plugin compatibility.
- Test framework compatibility.
- CI runner support.
- Reproducibility across supported environments.

Do not change the Java bytecode target simply because
a newer JDK is used in CI.

## Testing and Quality Automation

TFW uses several complementary workflows. Understand their
individual purposes before changing or combining them.

### PIT mutation testing

The PR mutation workflow is:

`.github/workflows/pitest-pr.yml`

It runs for pull requests and uses the configured PIT profile
to analyze changes.

The follow-up workflow is:

`.github/workflows/pitest-update-pr.yml`

It runs after successful completion of the PIT workflow,
downloads the generated report artifact, and updates the
associated pull request.

Important considerations:

- Preserve the separation between the unprivileged analysis
  workflow and the privileged follow-up workflow.
- Preserve the artifact handoff between the workflows.
- Do not execute pull request code in the follow-up workflow.
- Do not remove mutation analysis simply because it takes
  longer than the ordinary build.
- Investigate missing reports, failed aggregation, and
  artifact-download problems before changing the workflow.

The local mutation-testing command is:

```bash
./mvnw -Ppitest test
```

Consult `tfw-testing` for interpreting mutation results.

### ClusterFuzzLite

TFW has three principal ClusterFuzzLite workflows:

- `.github/workflows/cflite_pr.yml`
- `.github/workflows/cflite_batch.yml`
- `.github/workflows/cflite_coverage.yml`

They serve different purposes.

**Pull request fuzzing**

`cflite_pr.yml` runs when relevant production source,
fuzzer source, templates, configuration, or build files
change.

It performs code-change fuzzing with a short execution
window to identify problems introduced by a pull request.

Preserve the path filters unless there is a deliberate
decision to change which modifications require fuzzing.

**Scheduled batch fuzzing**

`cflite_batch.yml` runs on a schedule and can also be
started manually.

It performs longer-running batch fuzzing and stores results
in the separate `tfw-fuzzing` repository.

**Scheduled coverage**

`cflite_coverage.yml` generates fuzzing coverage reports
and publishes them to the `gh-pages` branch of
`tfw-fuzzing`.

It also updates the machine-readable Pages size information.

When modifying fuzzing workflows:

- Preserve the distinction between PR, batch, and coverage
  modes.
- Preserve the intended storage repositories and branches.
- Understand which token is used for each cross-repository
  operation.
- Do not assume a fixed number of fuzzers. Fuzzers are
  expected to grow as TFW gains coverage.
- Do not modify the separate `tfw-fuzzing` repository's
  workflow or published content as an incidental side effect.
- Preserve the existing action pinning and security settings.
- Check both the generated reports and the destination
  repository when troubleshooting publishing failures.

For fuzzer implementation and local fuzzing procedures,
consult `docs/maintenance/fuzzing.md` and `tfw-testing`.

### Coverage and quality analysis

TFW uses JaCoCo, PIT, and SonarCloud for complementary
quality information.

The SonarCloud workflow is:

`.github/workflows/sonar.yml`

It runs verification and coverage-enabled analysis. It
uses a full Git checkout for analysis and does not run
for Dependabot pull requests.

Do not remove the full checkout depth without checking
the impact on analysis.

The Maven coverage command is:

```bash
./mvnw -Pcoverage verify
```

Coverage is not proof of correctness. Preserve meaningful
tests and mutation analysis rather than optimizing only
for coverage percentages.

## Security and Supply-Chain Workflows

TFW uses multiple security and supply-chain checks. They
serve different purposes and should not be treated as
interchangeable.

### CodeQL

Workflow:

`.github/workflows/codeql.yml`

CodeQL analyzes Java code and runs on pushes, pull requests,
and a scheduled basis.

Preserve the intended language configuration, permissions,
and analysis upload behavior.

When changing the workflow, verify that the selected
language and build mode are appropriate for the repository.

### zizmor

Workflow:

`.github/workflows/zizmor.yml`

zizmor analyzes GitHub Actions workflows for security
problems.

Workflow modifications should be checked against its
findings. Do not suppress a finding merely to make the
workflow pass.

If an exception is genuinely necessary, understand the
specific risk and document the reason next to the narrowest
possible suppression.

### Scorecard

Workflow:

`.github/workflows/scorecard.yml`

Scorecard evaluates supply-chain security practices and
publishes analysis results.

It includes scheduled analysis and other event triggers
needed for the configured checks.

Do not casually remove event triggers or permissions:
some checks depend on particular events or GitHub security
features.

### Dependency Review

Workflow:

`.github/workflows/dependency-review.yml`

Dependency Review examines dependency changes in pull
requests.

The current configuration includes license allowlists,
a pull request summary, and retry handling for snapshot
warnings.

When changing dependency policies:

- Understand the difference between vulnerability findings
  and license findings.
- Verify the actual dependency coordinates and licenses.
- Keep justified exclusions narrow.
- Do not allow all licenses or disable vulnerability checks
  merely to avoid a failure.
- Investigate snapshot warnings separately from actual
  dependency vulnerabilities.

Do not assume that a warning about unavailable snapshot
metadata means that a dependency is vulnerable.

### Java security scanning

Workflow:

`.github/workflows/security-scan.yml`

The workflow builds the project and generates a Maven SBOM.
It then uses:

- Trivy to scan the repository filesystem for critical
  findings.
- Grype to scan the generated SBOM.

The current configuration makes critical Trivy findings
fail the workflow, while Grype findings are reported
without failing the build.

Preserve the distinction between the two scanners and
their configured failure policies unless a deliberate
policy change is requested.

Do not silently change a blocking security check into
a non-blocking check.

## GitHub Pages Publishing

The Pages workflow is:

`.github/workflows/publish-pages.yml`

It runs on pushes to `master`, release events, and manual
dispatch.

It generates and publishes several reports:

- Development Javadoc.
- Latest release Javadoc.
- API differences between the latest release and `master`.
- PIT mutation testing reports.
- A landing page containing report links and size information.
- Machine-readable `site-size.json` information.

The API difference report uses OpenJDK APIDiff. The workflow
fetches a specific APIDiff commit rather than relying on
an unpinned moving revision.

The workflow also performs full mutation testing for
the published PIT report.

When modifying this workflow:

- Preserve the distinction between the latest release
  source and the current `master` source.
- Ensure that Javadoc is generated from the intended source
  revision.
- Preserve the APIDiff tool's pinned revision.
- Understand the meaning of APIDiff's exit codes before
  treating a nonzero result as a failure.
- Do not discard temporary diagnostic files before they
  are no longer needed for troubleshooting.
- Preserve the Pages artifact structure and landing page
  links.
- Keep the size calculation consistent with the files
  actually published.
- Preserve the separation between the artifact-generation
  job and the deployment job.
- Keep deployment permissions limited to the job that
  performs the deployment.

The workflow uses GitHub Pages deployment permissions and
an environment for the deployment job. Do not replace
the deployment mechanism with a broad repository write
token without a specific requirement.

## Maven Central Release Automation

The release workflow is:

`.github/workflows/release.yml`

It runs when a GitHub release is published and skips
pre-releases.

The workflow:

1. Checks out the release source.
2. Configures Java and Maven Central publishing credentials.
3. Determines the Maven artifact ID and version.
4. Runs the Maven release deployment.
5. Generates build provenance attestations.
6. Prepares the provenance bundle.
7. Uploads artifacts, signatures, and provenance to
   the GitHub release.

The publishing command is:

```bash
./mvnw -ntp -B deploy -P release
```

Release publishing is consequential. Do not publish a
release, change release credentials, or trigger an actual
deployment without explicit authorization.

Never expose or log Maven Central credentials, GPG private
keys, passphrases, or tokens.

Do not remove signing, provenance generation, or artifact
verification merely to make a release workflow succeed.

For the project's release procedure, including version
changes and GitHub release creation, follow
`docs/maintenance/deployment.md`.

## Workflow Triggers and Concurrency

When changing workflow triggers, consider the consequences
of each event type.

Common triggers in TFW include:

- `push`
- `pull_request`
- `release`
- `schedule`
- `workflow_dispatch`
- `workflow_run`
- `branch_protection_rule`

Check whether a workflow should run for:

- All branches or only `master`.
- All pull requests or only changes to selected paths.
- Fork pull requests.
- Dependabot pull requests.
- Scheduled maintenance.
- Manual execution.
- Completion of another workflow.

Do not broaden a trigger unnecessarily. A workflow that
runs on every change may consume significant resources
or expose secrets to contexts that do not need them.

Use concurrency groups when overlapping runs could conflict
or waste resources.

Understand the difference between:

- `cancel-in-progress: true`, which allows newer work
  to replace outdated work.
- `cancel-in-progress: false`, which allows an existing
  operation to finish.

Do not cancel publishing or deployment operations
without understanding the risk of interrupting them.

## Troubleshooting Workflow Failures

When a workflow fails:

1. Identify the exact workflow run and commit SHA.
2. Determine whether it was triggered by a push, pull request,
   release, schedule, or another workflow.
3. Read the failed job and step logs, including the first
   meaningful error.
4. Determine whether the failure is in the workflow,
   the application build, an external service, or the
   runner environment.
5. Inspect the relevant workflow file and recent changes.
6. Check whether the failure is reproducible locally.
7. Make the smallest appropriate correction.
8. Re-run the relevant validation and inspect the new
   workflow results.

Distinguish among:

- YAML syntax or workflow validation errors.
- Missing or insufficient permissions.
- Missing, expired, or incorrectly scoped secrets.
- Maven or JDK compatibility problems.
- Dependency resolution and repository availability.
- Test failures.
- External action or service failures.
- Artifact upload, download, or retention problems.
- Runner environment and resource limitations.
- Concurrency cancellation or workflow dependency issues.

### Stuck or long-running workflows

Do not assume that a workflow is hung simply because
it has run longer than expected.

Inspect:

- The current job and step.
- Recent log activity.
- Whether a command is still running.
- Whether the job is waiting for an external service.
- Whether a dependent workflow or artifact is unavailable.
- Whether the workflow has exceeded its normal duration.

If a run is genuinely stuck, consider cancellation
when appropriate. Do not merge around a failed or
unverified required check merely to avoid waiting.

If a workflow is intentionally long-running, determine
whether its configured duration is expected before
changing timeouts or execution limits.

### Re-running workflows

Before re-running a failed workflow, determine whether
the failure was transient or caused by the code or
configuration.

Re-running a workflow does not fix a deterministic
failure.

For `workflow_run` automation, verify the status and
conclusion of the upstream workflow as well as the
downstream workflow.

Do not claim that a workflow succeeded until the actual
run result has been checked.

## Reviewing Workflow Changes

Before submitting a workflow modification, review the
complete diff and check:

- [ ] The workflow has the intended triggers.
- [ ] Job conditions and dependencies are correct.
- [ ] Concurrency behavior is appropriate.
- [ ] Third-party actions are pinned to verified full
      commit SHAs.
- [ ] Action version comments identify the pinned releases.
- [ ] Workflow and job permissions follow least privilege.
- [ ] Secrets are handled without exposing their values.
- [ ] Untrusted pull request code is not executed with
      privileged credentials.
- [ ] Artifacts are passed between jobs and workflows
      safely and correctly.
- [ ] Required build and quality checks remain enabled.
- [ ] Security findings have been investigated rather
      than suppressed.
- [ ] Release and deployment behavior has not changed
      unintentionally.
- [ ] Related documentation has been updated when needed.

Use the repository's existing security analysis workflows,
including zizmor, to help evaluate workflow changes.

## Completion Criteria

A workflow change is complete only when:

- The intended behavior is understood and implemented.
- The complete workflow diff has been reviewed.
- Relevant security and permission implications have
  been evaluated.
- Applicable workflow validation and CI checks have
  completed successfully.
- Any failed checks have been investigated.
- Required downstream workflows and artifact handoffs
  have been considered.
- Documentation has been updated where necessary.
- Remaining limitations or unverified results are
  clearly disclosed.

Do not weaken security, quality, or release safeguards
to obtain a successful workflow run.

Do not claim that a workflow is fixed merely because
the YAML change was committed. Verify the resulting
GitHub Actions run.

## Important References

Workflow configuration:

- `.github/workflows/`
- `.github/workflows/maven.yml`
- `.github/workflows/pitest-pr.yml`
- `.github/workflows/pitest-update-pr.yml`
- `.github/workflows/cflite_pr.yml`
- `.github/workflows/cflite_batch.yml`
- `.github/workflows/cflite_coverage.yml`
- `.github/workflows/dependency-review.yml`
- `.github/workflows/codeql.yml`
- `.github/workflows/security-scan.yml`
- `.github/workflows/sonar.yml`
- `.github/workflows/scorecard.yml`
- `.github/workflows/zizmor.yml`
- `.github/workflows/publish-pages.yml`
- `.github/workflows/release.yml`

Project documentation and configuration:

- `CONTRIBUTING.md`
- `docs/maintenance/README.md`
- `docs/maintenance/development.md`
- `docs/maintenance/deployment.md`
- `docs/maintenance/fuzzing.md`
- `pom.xml`
- `.mvn/wrapper/maven-wrapper.properties`
- `execute-on-vnc.sh`

Consult the current repository files rather than relying
on remembered workflow names, action versions, permissions,
or execution behavior.
