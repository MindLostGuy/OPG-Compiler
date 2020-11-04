FROM openjdk:12
WORKDIR /app/
COPY ./* ./
RUN javac OPG.java