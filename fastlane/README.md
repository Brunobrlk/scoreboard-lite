fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android lint

```sh
[bundle exec] fastlane android lint
```

Run lint

### android unit_tests

```sh
[bundle exec] fastlane android unit_tests
```

Run unit tests

### android integration_tests

```sh
[bundle exec] fastlane android integration_tests
```

Run integration tests

### android build_debug_apk

```sh
[bundle exec] fastlane android build_debug_apk
```

Build debug APK

### android build_release_apk

```sh
[bundle exec] fastlane android build_release_apk
```

Build release APK

### android build_release_aab

```sh
[bundle exec] fastlane android build_release_aab
```

Build release AAB

### android firebase_distribution

```sh
[bundle exec] fastlane android firebase_distribution
```

Distribute build to Firebase testers

### android github_release

```sh
[bundle exec] fastlane android github_release
```

Create GitHub release

### android screenshots

```sh
[bundle exec] fastlane android screenshots
```



### android play_store_internal

```sh
[bundle exec] fastlane android play_store_internal
```

Upload to Play Store - Internal

### android promote_internal_to_alpha

```sh
[bundle exec] fastlane android promote_internal_to_alpha
```



### android promote_internal_to_beta

```sh
[bundle exec] fastlane android promote_internal_to_beta
```



### android promote_alpha_to_beta

```sh
[bundle exec] fastlane android promote_alpha_to_beta
```



### android promote_to_prod

```sh
[bundle exec] fastlane android promote_to_prod
```



### android promotion

```sh
[bundle exec] fastlane android promotion
```



### android chlog

```sh
[bundle exec] fastlane android chlog
```



----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
