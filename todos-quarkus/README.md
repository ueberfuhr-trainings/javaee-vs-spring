# Todos Management App (Quarkus)

## Commands

### Starting in Dev Mode

`mvn clean compile quarkus:dev`

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packaging and Running

```bash
mvn clean package
# uber jar
mvn clean package -Dquarkus.package.type=uber-jar
# native executable
mvn clean package -Pnative
# native executable (build in in a container)
mvn clean package -Pnative -Dquarkus.native.container-build=true
```
