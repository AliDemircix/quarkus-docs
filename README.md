# quarkus-docs

To create the project, run the following command:

```bash
mvn io.quarkus.platform:quarkus-maven-plugin:3.10.1:create \
    -DprojectGroupId=com.tasktracker \
    -DprojectArtifactId=task-tracker \
    -DclassName="com.tasktracker.TaskResource" \
    -Dpath="/tasks"

```

To create the project, run the following command:

```bash
cd task-tracker
./mvnw compile quarkus:dev
```

If you are working on Windows device run the following command:

```bash
mvnw.cmd compile quarkus:dev
```
