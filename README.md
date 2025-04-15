# quarkus-docs

To create the project, run the following command:

```shell script
mvn io.quarkus.platform:quarkus-maven-plugin:3.10.1:create \
    -DprojectGroupId=com.tasktracker \
    -DprojectArtifactId=task-tracker \
    -DclassName="com.tasktracker.TaskResource" \
    -Dpath="/tasks"

```

To create the project, run the following command:

```shell script
cd task-tracker
./mvnw compile quarkus:dev
```

If you are working on Windows device run the following command:

```shell script
mvnw.cmd compile quarkus:dev
```

Add open api extension with following command:

```shell script
./mvnw quarkus:add-extension -Dextensions="smallrye-openapi"
```

Add dependencies in to the pom .xml

```bash
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-openapi</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-swagger-ui</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-rest-jackson</artifactId>
        </dependency>
             <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-hibernate-orm-panache</artifactId>
        </dependency>
             <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-jdbc-postgresql</artifactId>
        </dependency>
```
