FROM ubuntu:20.04
FROM ruby:3.3.1
LABEL maintainer="ganza@isakaro.com"

SHELL ["/bin/bash", "-c"]

RUN ln -fs /usr/share/zoneinfo/Africa/Kigali /etc/localtime

ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Africa/Kigali

#install needed software packages
RUN apt-get update && apt-get install -y --no-install-recommends \
	tzdata \
	vim \
	git \
	unzip \
	libglu1 \
	libpulse-dev \
	libasound2 \
	libc6 \
	libstdc++6 \
	libx11-6 \
	libx11-xcb1 \
	libxcb1 \
	libxcomposite1 \
	libxcursor1 \
	libxi6 \
	libxtst6 \
	libnss3 \
	openssh-client \
	wget \
	curl \
	build-essential \
	make \
	jq

WORKDIR /

COPY ./drivers/Gemfile ./
COPY ./drivers/fastlane ./fastlane

RUN gem install bundler:2.5.9 && gem install fastlane --version 2.220.0 --no-document && bundle install

#install jdk 17
RUN apt-get -y install openjdk-17-jdk

#remove bash_login if it exists
RUN rm -rf ~/.bash_login

#update packages needed
RUN apt-get update

RUN mkdir /opt/android-sdk
RUN mkdir /opt/gradlew
RUN touch /opt/gradlew/build.gradle.kts

#variable component versions and paths to component home
ARG GRADLE_VERSION=8.11.1
ARG ANDROID_API_LEVEL=35
ARG ANDROID_BUILD_TOOLS_LEVEL=35.0.0
ARG CMDLINE_TOOLS_VERSION=11076708
ENV GRADLE_HOME=/opt/gradle/gradle-$GRADLE_VERSION
ENV ANDROID_HOME=/opt/android-sdk

#download Gradle and configure its wrapper
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
	&& unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
	&& touch /opt/gradle/gradle-${GRADLE_VERSION}/bin/build.gradle \
	&& /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle wrapper --gradle-version ${GRADLE_VERSION} --distribution-type all -p /opt/gradlew  \
	&& /opt/gradle/gradle-${GRADLE_VERSION}/bin/gradle wrapper -p /opt/gradlew

#download required components for building android apps (command line tools and most importantly build-tools & platform tools).
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_TOOLS_VERSION}_latest.zip -P /tmp  \
    && unzip -d ${ANDROID_HOME} /tmp/commandlinetools-linux-${CMDLINE_TOOLS_VERSION}_latest.zip

#add sdkmanager to path
ENV PATH "${ANDROID_HOME}/cmdline-tools/bin:$PATH"

RUN yes Y | sdkmanager --sdk_root=${ANDROID_HOME} --install "tools" "platform-tools" "platforms;android-${ANDROID_API_LEVEL}" "build-tools;${ANDROID_BUILD_TOOLS_LEVEL}" "build-tools;33.0.2" "build-tools;33.0.1" "platforms;android-33" "build-tools;30.0.3" "emulator" \
    && yes Y | sdkmanager --sdk_root=${ANDROID_HOME} --licenses

#add executables to environment variables
ENV PATH "${GRADLE_HOME}/bin:/opt/gradlew:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools:${GEM_HOME}/bin:$PATH"

## cleanup of files from setup
RUN rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


RUN echo "DONE!!!!!!!!!!!!"
