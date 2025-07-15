
#  Android Emulator + Appium in Docker with Maven Test Execution

##  Step 1: Run Docker Compose

Run the Docker Compose file to start the Android Emulator:

```bash
docker-compose -f Dockercompose.yml up -d
````

> Make sure the file is named `Dockercompose.yml` and in your current directory.

---

## Open Android Emulator in Browser

Once the container is running, open your browser and navigate to:

```
http://localhost:6080/
```

You will see the Android emulator running in the browser via VNC.

---

## : Mount Code Directory

In the `docker-compose.yml` file:

```yaml
    volumes:
      - ./:/code     # Mount current host folder to /code inside the container
    working_dir: /code  # Set /code as working directory in the container
```

This lets you code normally in your current local folder.

---

##  Open Terminal and Access the Container

In a new terminal, run:

```bash
docker exec -it android-container /bin/bash
```

This opens an interactive shell into the running container named `android-container`.

---

##  Run Your Maven Test

Inside the container shell, run:

```bash
./mvnw clean test -Dtest=LaunchAppTest
```

This command runs your `LaunchAppTest` class inside the emulator environment.

---

##  Result

Go back to your browser at:

```
http://localhost:6080/
```

You will see the Android emulator running the test visually.


