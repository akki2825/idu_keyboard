#!/bin/bash

# Idu Mishmi Keyboard - App Signing Script
# This script helps sign the release APK

echo "🔐 Signing Idu Mishmi Keyboard APK..."

# Build the release APK
echo "📦 Building release APK..."
./gradlew assembleRelease

if [ $? -eq 0 ]; then
    echo "✅ Release APK built successfully!"
    echo "📱 APK location: app/build/outputs/apk/release/HeliBoard_1.0.0-release.apk"
    echo "🔑 Keystore: idu_keyboard.keystore"
    echo "📋 Certificate: Idu Mishmi Cultural and Literary Society"
    echo ""
    echo "To install the signed APK:"
    echo "adb install -r app/build/outputs/apk/release/HeliBoard_1.0.0-release.apk"
else
    echo "❌ Build failed!"
    exit 1
fi
