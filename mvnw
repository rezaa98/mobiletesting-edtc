#!/bin/bash
# Maven Wrapper Bootstrap Script
# This script downloads and runs Maven Wrapper
# Usage: ./mvnw clean test

set -e

MAVEN_WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

if [ ! -f "$MAVEN_WRAPPER_PROPERTIES" ]; then
    mkdir -p .mvn/wrapper
    cat > "$MAVEN_WRAPPER_PROPERTIES" << 'EOF'
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip
wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar
EOF
fi

# Download Maven Wrapper JAR if not present
if [ ! -f "$MAVEN_WRAPPER_JAR" ]; then
    echo "Downloading Maven Wrapper..."
    WRAPPER_URL=$(grep wrapperUrl "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
    curl -sL "$WRAPPER_URL" -o "$MAVEN_WRAPPER_JAR"
fi

# Run Maven
MAVEN_HOME="${HOME}/.m2/wrapper/dists/apache-maven-3.9.9"
if [ ! -d "$MAVEN_HOME" ]; then
    echo "Downloading Maven distribution..."
    DIST_URL=$(grep distributionUrl "$MAVEN_WRAPPER_PROPERTIES" | cut -d'=' -f2-)
    mkdir -p "${HOME}/.m2/wrapper/dists"
    curl -sL "$DIST_URL" -o /tmp/maven.zip
    unzip -qo /tmp/maven.zip -d "${HOME}/.m2/wrapper/dists/"
    rm /tmp/maven.zip
fi

export PATH="${MAVEN_HOME}/bin:${PATH}"
exec mvn "$@"
