# TrendyRepo
Simple single screen app which shows the current trending Github repositories.


### Assumptions
1. "Current" means today. So, app will cache the result of todays trending repositories and refresh it if its a new day.
2. UI has overflow icon for an app to have a force refresh functionality. This force refresh will clean up the cache if new data was fetched from api.
