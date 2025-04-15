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
         <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
        <version>3.0.2</version>
    </dependency>
```

Run postgres with docker

```bash
docker run --name taskdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=taskdb -p 5432:5438 -d postgres
```

PanacheEntity provides ready-to-use methods like `id`, `persist()`, and `findAll()`. It makes ORM tasks much easier 🔥

Add dependency quarkus-flyway to follow changes

```bash
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-flyway</artifactId>
</dependency>
```
