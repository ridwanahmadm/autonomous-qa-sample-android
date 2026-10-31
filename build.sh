#!/usr/bin/env bash
# Builds build/taskboard.apk with the Android SDK tools only (no Gradle).
#
# Needs: ANDROID_HOME (default ~/Library/Android/sdk) with build-tools and an
# android-35 platform, and a JDK (JAVA_HOME, else Android Studio's bundled one).
set -euo pipefail

here="$(cd "$(dirname "$0")" && pwd)"
sdk="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
platform="$sdk/platforms/android-35/android.jar"
tools="$(ls -d "$sdk"/build-tools/*/ 2>/dev/null | sort -V | tail -n 1)"
java_home="${JAVA_HOME:-/Applications/Android Studio.app/Contents/jbr/Contents/Home}"

[[ -f "$platform" ]] || { echo "Missing $platform (install platforms;android-35)." >&2; exit 1; }
[[ -n "$tools" ]] || { echo "No build-tools in $sdk/build-tools." >&2; exit 1; }
[[ -x "$java_home/bin/javac" ]] || { echo "No JDK at $java_home; set JAVA_HOME." >&2; exit 1; }
# d8 and apksigner are scripts that run `java` from the PATH.
export JAVA_HOME="$java_home" PATH="$java_home/bin:$PATH"

src="$here/app/src/main"
out="$here/build"
rm -rf "$out/gen" "$out/classes" "$out/dex" "$out"/*.apk "$out/res.zip"
mkdir -p "$out/gen" "$out/classes" "$out/dex"

"$tools/aapt2" compile --dir "$src/res" -o "$out/res.zip"
"$tools/aapt2" link "$out/res.zip" -I "$platform" --manifest "$src/AndroidManifest.xml" \
  --min-sdk-version 26 --target-sdk-version 35 --version-code 1 --version-name 1.0 \
  --java "$out/gen" -o "$out/unsigned.apk"

find "$src/java" "$out/gen" -name '*.java' > "$out/sources.txt"
javac --release 11 -classpath "$platform" -d "$out/classes" @"$out/sources.txt"
find "$out/classes" -name '*.class' > "$out/classes.txt"
"$tools/d8" --release --min-api 26 --lib "$platform" --output "$out/dex" @"$out/classes.txt"
(cd "$out/dex" && zip -q -u "$out/unsigned.apk" classes.dex)

"$tools/zipalign" -f -p 4 "$out/unsigned.apk" "$out/aligned.apk"
keystore="$out/debug.keystore"
if [[ ! -f "$keystore" ]]; then
  keytool -genkeypair -keystore "$keystore" -storepass android \
    -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 \
    -dname "CN=Android Debug,O=Android,C=US" >/dev/null 2>&1
fi
"$tools/apksigner" sign --ks "$keystore" --ks-pass pass:android \
  --out "$out/taskboard.apk" "$out/aligned.apk"
rm -f "$out/unsigned.apk" "$out/aligned.apk" "$out/aligned.apk.idsig" "$out/taskboard.apk.idsig"
echo "Built $out/taskboard.apk"
