FROM amazoncorretto:11
LABEL authors="Sleepwalker"
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
COPY target/*.jar gateway.jar
ENTRYPOINT ["java","-jar","/gateway.jar"]