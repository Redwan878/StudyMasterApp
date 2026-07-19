# StudyMaster (পড়াশোনা)

Native Android study-productivity app — Pomodoro timer, stats, routines,
app blocker with strict Zen Mode, goals / tasks / notes / exams, local AI
study assistant, and more. Package: `com.porashona.studymaster`.

## Building

### Debug APK
```bash
./gradlew assembleDebug
# app/build/outputs/apk/debug/app-debug.apk
```

### Release APK
The repo ships with a **self-signed dev keystore** at `app/release.keystore`
so that both local and CI release builds work out of the box without any
manual setup. This key is **not** suitable for Play Store uploads — it is
only a convenience for side-loading signed APKs.

```bash
./gradlew assembleRelease
# app/build/outputs/apk/release/app-release.apk
```

The default credentials are:

| property  | value         |
|-----------|---------------|
| storeFile | `app/release.keystore` |
| storePass | `studymaster` |
| keyAlias  | `studymaster` |
| keyPass   | `studymaster` |

Override with these environment variables for CI or for production signing:

```
RELEASE_KEYSTORE_FILE
RELEASE_KEYSTORE_PASSWORD
RELEASE_KEY_ALIAS
RELEASE_KEY_PASSWORD
```

### Releasing a new version

Tag the commit with a `v*` tag and push — `.github/workflows/release.yml`
will build the release APK and attach it to a GitHub Release.

```bash
git tag v0.1.0
git push origin v0.1.0
```

You can also run the workflow manually from the **Actions** tab
("Build & Release APK" → *Run workflow*) to produce an APK artifact
without cutting a release.
