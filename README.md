# Taskboard for Android

A small native Android app (Java, no third-party libraries) for keeping a task
list. It is a sample target for Autonomous QA's mobile testing: install the
APK on an emulator, point the project's test environment at it, and run the
approved Android cases.

## Build and install

The build uses the Android SDK tools directly (aapt2, javac, d8, zipalign,
apksigner); Gradle is not needed.

```bash
./build.sh
adb install -r build/taskboard.apk
```

`build.sh` needs the SDK (`ANDROID_HOME`, default `~/Library/Android/sdk`)
with build-tools and the `android-35` platform, and a JDK (`JAVA_HOME`, else
the one bundled with Android Studio). The app id is
`com.autonomousqa.taskboard`; it runs on Android 8.0 (API 26) and later.

## Account

| Email | Password |
| --- | --- |
| demo@example.com | demo-pass-123 |

## Rules

- **Sign in** needs an email and a password. An empty email shows "Email is
  required.", an empty password "Password is required.", and a wrong email or
  password shows **"Wrong email or password."** and stays on the sign-in
  screen. The email is not case-sensitive.
- After signing in, **Your tasks** lists the tasks with a checkbox each, and a
  summary line **"N of M done"** above them. The list starts with three tasks,
  one of them done.
- **New task**: the title is required ("Enter a title.") and has at most 80
  characters ("Titles can have at most 80 characters."). Its first letter is
  capitalized. A new task is not done.
- **Checking or unchecking a task updates the summary** straight away.
- **Sign out** returns to the sign-in screen.
- Tasks are kept in memory while the app runs and reset when it restarts.

## License

Apache-2.0
