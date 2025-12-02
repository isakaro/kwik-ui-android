# Release Guide

This guide explains how to release a new version of KwikUI to Maven Central.

## Release via Pull Request (Recommended)

Create a PR to `main` with a title that indicates the version bump:

### Patch Release (1.0.10 → 1.0.11)
```bash
git checkout -b fix/button-styling
# Make your changes
git add .
git commit -m "fix: resolve button styling issue"
git push origin fix/button-styling
# Create PR with title: "fix: resolve button styling issue"
```

### Minor Release (1.0.10 → 1.1.0)
```bash
git checkout -b feature/carousel
# Make your changes
git add .
git commit -m "feat: add new carousel component"
git push origin feature/carousel
# Create PR with title: "feat: add new carousel component [minor]"
```

### Major Release (1.0.10 → 2.0.0)
```bash
git checkout -b breaking/api-changes
# Make your changes
git add .
git commit -m "feat!: breaking API changes"
git push origin breaking/api-changes
# Create PR with title: "feat!: breaking API changes [major]"
```

The CI workflow will:
- Build and test your changes
- Comment on the PR with version bump info
- Verify everything passes before merge

When you merge the PR to `main`, the release workflow will automatically:
- Update the version
- Build and publish to Maven Central
- Create a GitHub release
- Commit version changes back to main

Note: JReleaser automatically creates the GitHub release, so no manual step needed.

## Version Numbering

- **Patch (X.Y.Z)**: Bug fixes, small improvements
- **Minor (X.Y.0)**: New features, backward compatible
- **Major (X.0.0)**: Breaking changes

## Verify Release

After publishing, verify the release is available:
- Check [Maven Central](https://central.sonatype.com/artifact/com.isakaro/kwik.ui)
- Check [GitHub Releases](https://github.com/isakaro/kwik-ui-android/releases)
- Test installation: `implementation("com.isakaro:kwik.ui:X.Y.Z")`
