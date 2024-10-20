# Setup in Ubuntu
* Install android studio
* Install npm
  * https://nodejs.org/en/download/package-manager/current
  * `sudo apt install curl`
  * `curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.0/install.sh | bash`
  * Restart terminal
  * `nvm install 23`
* Install appium
  * `npm install -g appium`
* Start emulator
  * Go to `~/Android/Sdk/emulator` and run `./emulator -list-avds`
  * Then `./emulator -avd Medium_Phone_API_35`
* Install appium driver
  * https://appium.io/docs/en/2.12/quickstart/uiauto2-driver/
  * Setup env var `ANDROID_HOME` to `ANDROID_HOME=/home/USER/Android/Sdk/`
  * Setup `JAVA_HOME` to something like `JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64`
  * `appium driver install uiautomator2`
  * `appium driver doctor uiautomator2`
* Start appium `appium --allow-insecure chromedriver_autodownload`
