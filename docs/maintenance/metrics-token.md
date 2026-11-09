# Updating the `TFW_ORG_READ_TOKEN` in the tfw-metrics repository.

Use these steps to generate and install a replacement fine-grained personal access token for `tfw-metrics`.

1. Go to GitHub → Profile → Settings → Credentials → Fine-grained personal access tokens → Fine-grained tokens.
2. Press "Generate new token"
3. Set Token name: `tfw/tfw-fuzzing for tfw-metrics`
4. Set Resource owner: `tfw-org`
5. Set Expiration: `366 days`
6. Set Repository access: `Only select repositories`
7. Set Selected repositories: `tfw` and `tfw-fuzzing`
8. Set Permissions: Read-only access to Actions, Contents, and Metadata
9. Press "Generate token"
10. Press "Generate token"
11. Copy the token value
12. Go to `tfw-fuzzing` → Repository Settings → Secrets and variables → Actions
13. Press the pencil icon next to `TFW_ORG_READ_TOKEN` to edit the value
14. Paste the token value from Step 11.
15. Press "Update secret"
