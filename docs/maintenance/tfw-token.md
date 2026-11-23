# Updating the `TFW_FUZZING_TOKEN` in the tfw repository.

This document describes how to generate and install a replacement fine-grained personal access token (PAT) for the `tfw` repository.

1. Go to GitHub → Profile → Settings → Credentials → Fine-grained personal access tokens → Fine-grained tokens.
2. Press "Generate new token"
3. Set Token name: `tfw-fuzzing access for tfw`.
4. Set Resource owner: `tfw-org`.
5. Set Expiration: `366 days`.
6. Set Repository access: `Only select repositories`
7. Set Selected repositories: `tfw-fuzzing`.
8. Set Permissions: Read-only access to Metadata and Read/Write access to Contents
9. Press "Generate token"
10. Press "Generate token"
11. Copy the token value
12. Navigate to the [tfw repository](https://github.com/tfw-org/tfw).
13. Select Settings → Secrets and variables → Actions.
14. Locate the `TFW_FUZZING_TOKEN` secret.
15. Update the secret with the newly generated token.
16. Save the changes.