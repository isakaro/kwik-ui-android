## Table of contents 📋
- [Required tools 🔨](#required-tools-)
- [Project overview](#project-overview)
- [Architecture 🏠](#architecture-)
  - [MVVM](#mvvm)
  - [Networking 🌐](#networking-)
  - [UI 📺](#ui-)
  - [Dependency Injection with Koin 💉](#dependency-injection-with-koin-)
- [How to build ⚒](#how-to-build-)
- [Running the app ▶](#running-the-app-)
- [Releasing 🚀](#releasing-)
  - [staging](#staging)
  - [production](#production)
- [Project Docker image](#docker-image)
- [Troubleshooting 😵‍💫](#troubleshooting-)

## Required tools 🔨
- Kotlin 1.9.10
- Java 17
- Gradle 8.4
- Android Studio Giraffe 2022.3 or higher

## Project overview

* `Android SDK` version minimum is `API level 29 [Android 10]`

* The architecture used is `MVVM (Model-View-ViewModel)` to facilitate separation of concerns (UI from business logic)

* Multi-Threading is handled using `Coroutines`

* Dependency Injection is provided by `Koin`.

* `Retrofit` is used as the networking library. Under the hood lies Okhttp.

* `NewRelic`is used to log events and exceptions.

* The project is configured to support multiple flavors, namely `[Debug, Staging, Production]`.

## Architecture 🏠

### MVVM

`MVVM (Model-View-ViewModel)` pattern takes inspiration from clean architecture.

- Model includes repositories and data sources (API Endpoints, LocalData DAOs)
- ViewModel acts as the mediator between the Model and the View.
- View deals with displaying data from ViewModel and getting input from the user.

### Networking 🌐

`Retrofit` is used to make network requests. Under the hood lies OkHttp. This is a very reliable networking client with many features.

### Navigation 🧭

Compose Destinations is used to provide a flexible and type-safe navigation pattern.

## UI 📺

This is a single-activity architecture. There's only one activity and the rest are Composables. This has multiple advantages.
- App wide configuration is done once: fonts, dark mode, communication...
- Modular UI thanks to Composables. You can change one part of the app without affecting the other.
- Flexibility regarding how the UI is developed.
- Easier navigation

## Dependency Injection with Koin 💉

Dependency injection helps us make our classes lighter and easier to test by outsourcing the creation of dependencies elsewhere.
Koin accomplishes that task quite well.

## Running the app ▶

This project uses Gradle as the build tool.

You have to install Gradle and add it to the environment variable. However, if no Gradle is installed on your machine then use Gradle wrapper /.gradlew on Unix systems or gradlew.bat on Windows systems.

To build the project, you need to have met the aforementioned requirements in [section one](#required-tools).

This project has three main build variants:
- debug (for local development)
- preprod (staging)
- release (production)

Running the app is simple.

- Connect your device to your computer or emulator ([guide to create emulator](https://developer.android.com/studio/run/managing-avds))
- In Android Studio, press run ▶️or Shift+F10

If it's your first time running the app, make sure Gradle has synced all the dependencies and everything is up to date before running as it will save you time.

👇🏽 👇🏽 👇🏽 👇🏽 👇🏽 👇🏽
> Remember to turn on **Offline Mode** in Android Studio to avoid Gradle syncing every time you run the app. This is very important to speed up the dev process. However, if you're adding new dependencies, you have to turn it off to sync the new dependencies.

> IMPORTANT NOTICE: You must choose a flavor to run.
> As of Android Studio Giraffe | 2022.3.1 Patch 2, the `Build Variant` section is located at the top left. Click on the Android robot icon. Typically, when developing you should use **porscheDebug**, it runs in debug mode and allows you to see network request logs and disables analytics.

## How to build ⚒

If everything is set up correctly, you can build the project by running the following commands:

For PreProd build:

> `gradle assemblePreProd` (build preprod APK)

For Release, Play Store requires AAB instead of APK, so:

> `gradle bundleRelease` (build production AAB)

You can use the flag `--no-build-cache` to tell gradle to build from scratch. Useful when facing miscellaneous build errors.\
Example: `gradle assemblePreProd --no-build-cache` (build from scratch)

APK output is located at

> staging `/app/build/outputs/apk/porschePreprod/`

> production `/app/build/outputs/apk/porscheRelease/`

> To see all possible gradle tasks, run `gradle tasks`

👇🏽 👇🏽 👇🏽 👇🏽 👇🏽 👇🏽
> Staging build is automatically signed locally. However, production build will be signed in the CI pipelines.

## Releasing for each environment 🚀

The CI/CD pipeline uses Docker to build the app and push an update to AppCenter for staging, and to Incapptic for production.
Thanks to a handy custom Gradle task, CI will also update the app version name and code using major, minor and patch notation.
Generally speaking, major means, well, a major feature, while minor means a medium feature change and lastly, patch means a small change.

You can specify a version type by adding an version update type to your commit message.

> Possible choices `[major] [minor] [patch]`

> For example: `git commit -am "new changes [major]"` will bump the major version number. `v1.0.0 will become v2.0.0`

If none of the above is specified, the default will be patch.

Example for major update
> `git commit -am "new changes [major]"`

Notation
```
v1.0.1
 │_|_|__major
   │_|___minor
     |_____patch
```

Below describes the release process for each environment.

-------
Staging
-------

> `git push origin main`

That's it, the changes will go to main and CI will do its job. Remember to always pull first before pushing since CI bumps up the version number in the **build.gradle.kts**, so there are changes that you don't have locally.

----------
Production
----------

After rigorous testing on staging, it's time to release to production.
The process is very simple, the `release.sh` script will take care of everything.
Simply run it:

> `sh release.sh`

To specify which version type should be bumped, you can, for example, do:
> `sh release.sh [major]` to bump major version. `v1.0.0` will become `v2.0.0`

> `sh release.sh [minor]` to bump minor version. `v1.0.0` will become `v1.1.0`

If no version type is specified, as described in the above section, patch will be updated. `v1.0.0` will become `v1.0.1`.

This script will pull any changes from main then push the changes to the release branch, which then triggers a production release.
To see running CI jobs [go here](https://cicd.skyway.porsche.com/mlxxx/pma-android-app/-/jobs)

After the CI jobs are completed, the update is automatically uploaded to Incapptic and then later to Play Store once approved.

Now everyone is able to download and run the latest version 🚀

## Troubleshooting 😵‍💫

Murphy's law states that whatever that can go wrong, will go wrong. Problems can arise when building an Android project. Down below are some of the most common issues and how to fix them.

> IMPORTANT NOTICE: if you don't have Gradle globally installed on your machine, you have to use the Gradle wrapper like so `./gradlew command`

- Error: Gradle daemon disappeared unexpectedly (it may have been killed or may have crashed)

The above error mostly happens when the machine is running out of memory. To fix it, try the following:

If you were running a deployment on Porsche Skyway CI/CD, simply retry the individual failed job (it's usually the 'assemble' type of job), it should work afterwards.
If you were running locally on your PC, try closing some applications and freeing up some memory then try again.

> `gradle --stop` (stop the gradle daemon - very useful)

> `rm -rf ./gradle/caches/` (remove the cache) on Linux/macOS or `rmdir ./gradle/caches/` on Windows

After the above commands, try running the build again.

- Build is seemingly taking forever. ADB Daemon could be stuck.

  > `gradle --stop` (stop the gradle daemon - very useful)

  > `rm -rf ./gradle/caches/` (remove the cache) on Linux/macOS or `rmdir ./gradle/caches/` on Windows

After the above commands, try running the build again.

- Sometimes you get an error saying the device isn't connected while it is. run the code below to stop and start ADB

  > `adb kill-server`

  > `adb start-server`

- Android Studio is behaving strangely. Ex: code completion not working, every single line is red, the output app icons and texts are incorrectly displayed, etc...

Here are some things you can try:

> Try invalidating caches `File > Invalidate Caches > Invalidate and Restart`

> Try `Build > Clean Project` then `Build > Rebuild Project`

> Uninstall the app from the device/emulator and install it again (having done at least one of the above steps).

The above action will clear the cache and restart Android Studio. This is a very useful trick to fix miscellaneous issues.

## Project Docker image 🐳

The project uses a Docker image to build the app in the CI/CD pipeline. The Dockerfile is located in the root project folder.

The image is hosted on the Porsche Skyway project container registry and doesn't change often unless upgrading major project build tools such as Gradle and JDK.
To build the image, follow the steps below.

Make sure you're in the project root folder, then run this command:

> `docker build -t cr.cicd.skyway.porsche.com/mlxxx/pma-android-app . `

After the image is built, you have to run it locally against the app to test it out. Do so by running:

> `docker run -it -v path-to-project/workforce-android:/app cr.cicd.skyway.porsche.com/mlxxx/pma-android-app:latest /bin/bash`

After the above command, you'll be inside the container with the project code included in the app directory.
We need to test if the image can successfully build the app. We will test this by building a staging app variant. To do so, run:

> `cd app`

> `gradle assemblePreProd`

If the above command completes successfully, then all is well and we can proceed with pushing the image to the container registry.

You can push the image to the container registry by running:

> `docker login cicd.skyway.porsche.com –u [your-skyway-usernname] –p [your-skyway-access-token]` without the brackets.

> `docker push cr.cicd.skyway.porsche.com/mlxxx/pma-android-app`

That's it. Now the CI pipeline has the latest image to build the app with :)
