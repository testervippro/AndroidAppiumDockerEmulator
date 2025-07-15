
#  Android Emulator + Appium in Docker with Maven Test Execution

##  Step 1: Run Docker file


```bash
docker run -d -p 6080:6080  -e EMULATOR_DEVICE="Samsung Galaxy S10" -e WEB_VNC=true -v ${PWD}:/code -w /code --device /dev/kvm --name android-container budtmo/docker-android:emulator_11.0  -c "appium -a 0.0.0.0 -p 4723"

````
---

## Open Android Emulator in Browser

Once the container is running, open your browser and navigate to:

```
http://localhost:6080/
```

You will see the Android emulator running in the browser via VNC.

---

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


