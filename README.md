Webportal-API
========
This is the repository for the Booking Platform backend

Installation
-----------
* Make sure you have the PostgreSQL installed on your machine
* Project compilation: `mvn clean compile`
* Run unit / integration tests: `mvn clean test` / `mvn clean verify`
* Package the application in a jar: `mvn clean package`

Local Environment Setup
----------
To define environment variables: 
* for windows:
    * Search: `Edit Environment Variables for your Account`
    * Open `Environment variables` on the lower right corner of the  window
    * Add the variables relative to your user via `New` and then `Save`
    * Logout and login (by simply locking the screen the changes will not take effect)
    * (!) If you add them ass `System Variables` they will be visible after a reboot of the machine
* for Linux/MacOS:
    * open your `~/.bashrc` or `~/.bash_profile`
    * export each variable as `export VARIABLE=VALUE` (with no spaces surrounding "=")

Required Variables: 
* DATABASE_USERNAME: \* your postgres username\*
* DATABASE_PASSWORD: \* your postgres password\*
* DATABASE_NAME    : \* your postgres database name\*
* DATABASE_PORT    : \* your postgres database port\*
