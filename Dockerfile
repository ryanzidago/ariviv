# Use the official gradle image to create a build artifact.
FROM gradle:7.3.3 as builder

ENV TIME_TO_WAIT_BEFORE_CHECKING_IF_REMINDER_TO_EXERCISE_SHOULD_BE_SENT_IN_MS=3000
ENV REMINDER_TO_EXERCISE_DELAY_IN_MS=3000

# Copy local code to the container image.
COPY build.gradle.kts .
COPY gradle.properties .
COPY src ./src

# Build a release artifact.
RUN gradle installDist

FROM openjdk:8-jdk
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=builder /home/gradle/build/install/gradle /app/
WORKDIR /app/bin
CMD ["./gradle"]