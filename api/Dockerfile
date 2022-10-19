FROM maven:3.8.6-amazoncorretto-17

ENV HOME=/app

WORKDIR $HOME

COPY . $HOME

RUN mvn clean package -DskipTests

CMD ["java", "-jar target/${JAR_NAME}"]
