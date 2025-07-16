
# Android Emulator + Appium in Docker with Maven Test Execution (Only windows)

## Step 1: Run Docker Container use powershell and run  in curent project

```bash
docker run -d -p 6080:6080  -e EMULATOR_DEVICE="Samsung Galaxy S10" -e WEB_VNC=true -v ${PWD}:/code -w /code --device /dev/kvm --name android-container budtmo/docker-android:emulator_11.0

```

---

## Open Android Emulator in Browser

Once the container is running, open your browser and navigate to:

```
http://localhost:6080/
```

You will see the Android emulator running in the browser via VNC.

---

This setup lets you work normally on your local folder (mounted inside the container).

---

## Step 2: Access the Container Terminal and Start Appium

Open a new terminal and run:

```bash
docker exec -it android-container /bin/bash
```
Then, inside the container shell, start the Appium server:


```bash
appium -p 4723
```

## Step 3: Run Your Maven Test

Open another terminal, access the container shell again:

```bash
docker exec -it android-container /bin/bash
```

Run your Maven test inside the container:

```bash
mvn clean test -Dtest=LaunchAppTest
```

This command will run the `LaunchAppTest` class inside the emulator environment.

---

## Step 4: View Test Execution

Go back to your browser at:

```
http://localhost:6080/
```

You can watch the Android emulator running your test visually.

---

## Note

Running everything in one command like this:

```bash
docker run -d -p 6080:6080 -p 4723:4723 -e EMULATOR_DEVICE="Samsung Galaxy S10" -e WEB_VNC=true -e APPIUM=true --device /dev/kvm --name android-container budtmo/docker-android:emulator_11.0
```

may **not** work correctly (reason unclear).

Therefore, it’s recommended to run Appium manually in a separate terminal as shown above.

For more details, see the official documentation:
[https://github.com/budtmo/docker-android/blob/master/documentations/USE\_CASE\_APPIUM.md](https://github.com/budtmo/docker-android/blob/master/documentations/USE_CASE_APPIUM.md)

https://github.com/budtmo/docker-android/issues/395#issuecomment-2040219000



