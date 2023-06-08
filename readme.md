# Dungeons-api
API for dungeons game. Provides common functionality of saving player state, doing periodical tasks, dungeons state and common game events.

## Local Setup
- use JDK 17
- setup postgresql, credentials for user stored in application.properties
- git pull
- update maven dependencies
- run plugin for resources generation 
  - DungeonsData -> Plugins -> resources -> resources:resources
- run plugin for database structure creation
  - DungeonsData -> plugins -> liquibase -> liquibase:update
  - in case db is not empty and you want to recreate it
    - DungeonsData -> plugins -> liquibase -> liquibase:dropall
    - DungeonsData -> plugins -> liquibase -> liquibase:update
- create run configuration type application
  - with -cp Dungeons-Api
  - check that working directory \Dungeons-Api
  - entry point class is ServerApplication
- run configuration