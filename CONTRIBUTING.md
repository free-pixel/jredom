# Contributing Guidelines

Thank you for your interest in contributing to this project!

## Developer Certificate of Origin (DCO)

This project uses the [Developer Certificate of Origin (DCO)](https://developercertificate.org/) to certify contributions.

By signing off on your commits, you confirm that:

> The contribution was created in whole or in part by you and you have the right to submit it under the open source license indicated in the project.

### ✍️ Sign-Off Process

All commits submitted through GitHub's web interface or via Git CLI must be signed off.

#### ✅ GitHub Web Interface

When editing files directly on GitHub.com, please check the "Sign off" box before committing.

#### ✅ Git CLI

Use the `-s` option when making a commit:

```bash
git commit -s -m "Your commit message"
```

## Commit Message Guidelines

This project uses a commit message template to standardize commit messages. To use it:

1. The template is automatically configured when you clone the repository
2. If you need to set it up manually, run:
   ```bash
   git config --local commit.template .gitmessage
   ```
3. Follow the format in the template for all your commits
4. The template includes:
   - Type of change (feat, fix, docs, etc.)
   - Short description
   - Optional detailed description
   - Optional task references
   - Optional breaking changes
