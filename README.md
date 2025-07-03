# CarRentz simulation

## Introduction
This simulation happens in two parts:

1. The REST API, which maintains CarRentz's inventory. This is a Java 21/Gradle project which hosts the REST API in port 8080.
2. The simulation program(s) in Python3 code.

## Notes for readers

### Caveat Emptor!

1. This version is hardly complete, and ~~possibly~~ has a ton of bugs. This repo and work is for illustration/straw man/discussion purposes only.

2. It is expected that you have Java 21/Gradle 8.14.2 and Python3, but also the ability to run MySQL (8.0) in your machine, or a machine where you can create a MySQL database and have it be accessible from the REST API remotely.

3. The design document contains many _aspirational_ comments vis-a-vis operationalizing this project. Exists for discussion only. Many changes have since been made to the REST api and the design.
