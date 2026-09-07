# Security Policy

## Security Contact

The security contact for `tfw` is:

- **[@jdcove2](https://github.com/jdcove2)** — TFW project maintainer

## Supported Versions

The `tfw` project is actively maintained on the latest development and release versions.

Security fixes are generally provided for:

| Version | Supported |
| --- | --- |
| Latest stable release | :white_check_mark: |
| Latest development version | :white_check_mark: |
| Older releases | :x: |

If you are using an older version, please upgrade to the latest available release before reporting a vulnerability, where practical.

If you are unsure whether your version is supported, please report the issue privately rather than disclosing security details publicly.

## Reporting a Vulnerability

**Please do not report security vulnerabilities through public GitHub Issues, pull requests, discussions, or other public channels.**

The preferred and supported way to report a security vulnerability in `tfw` is through **GitHub's private vulnerability reporting**:

https://github.com/tfw-org/tfw/security/advisories/new

Reports submitted through this mechanism are visible only to the repository maintainers and the reporter.

### What to Include

To help us investigate and remediate the issue efficiently, please include as much of the following information as you can:

- A clear description of the vulnerability and its potential impact.
- The affected version(s), commit(s), module(s), or component(s).
- Steps to reproduce the issue.
- A minimal proof of concept or test case, when safe to provide.
- Any relevant configuration, environment, or deployment details.
- Whether exploitation is possible remotely, locally, or only with specific privileges.
- Any known mitigations or workarounds.
- Your preferred name or handle for security advisory credit, if desired.

Please avoid including secrets, credentials, personal data, or other sensitive information that is not necessary to reproduce the issue.

## What to Expect

After receiving a vulnerability report, maintainers will make a reasonable effort to:

1. Acknowledge receipt of the report.
2. Assess the validity, severity, affected versions, and potential impact.
3. Work with the reporter to reproduce and understand the vulnerability.
4. Develop and test an appropriate fix.
5. Determine whether a security advisory and/or CVE is appropriate.
6. Release or otherwise communicate the remediation to affected users.
7. Publicly disclose relevant details after a fix or mitigation is available, while minimizing unnecessary risk to users.

Response and remediation times may vary depending on the severity, complexity, and maintainer availability.

## Coordinated Disclosure

We ask security researchers to give maintainers a reasonable opportunity to investigate and remediate vulnerabilities before publicly disclosing technical details.

Please avoid:

- Publicly posting the vulnerability before maintainers have had an opportunity to address it.
- Opening a public GitHub issue containing exploitable details.
- Publishing proof-of-concept code that materially increases the risk to users before a fix is available.
- Accessing, modifying, or deleting data belonging to other users.
- Performing actions that could disrupt services or affect users beyond what is necessary to demonstrate the vulnerability.

We appreciate responsible security research conducted in good faith.

Once a fix or effective mitigation is available, maintainers and reporters may coordinate an appropriate disclosure timeline. Where applicable, security advisories should identify affected versions, fixed versions, severity, and relevant remediation information.

## Security Updates

Security fixes may be released as normal project releases or as dedicated security releases, depending on the severity and circumstances.

Users should keep `tfw` and its dependencies up to date and monitor the repository's GitHub Security Advisories for security-related announcements.

## Dependency and Build Security

The project uses Maven and third-party dependencies. Contributors and maintainers should:

- Keep dependencies reasonably up to date.
- Review dependency security advisories before upgrading or introducing dependencies.
- Avoid committing credentials, API keys, tokens, certificates, or other secrets.
- Review changes to build and CI configuration for security implications.
- Prefer pinned and trusted dependencies and build tooling where practical.
- Treat changes to GitHub Actions, Maven plugins, and other build infrastructure as security-sensitive.

Security-sensitive changes should receive appropriate code review before being merged.

## Scope

This policy covers security vulnerabilities in the `tfw` project and its officially maintained source code, build configuration, and distributed artifacts.

Vulnerabilities exclusively affecting third-party dependencies should generally be reported to the affected upstream project as well. If the vulnerability has a material impact on `tfw`, please report it privately to the `tfw` maintainers too.

## Recognition

We appreciate responsible security researchers and contributors who help improve the security of `tfw`.

With the reporter's permission, maintainers may credit security researchers in a security advisory or release announcement.

## Contact

For security vulnerabilities, please use GitHub's private vulnerability reporting:

https://github.com/tfw-org/tfw/security/advisories/new

**Do not use public GitHub Issues to report suspected security vulnerabilities.**
