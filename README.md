# TrendyRepo
Simple single screen app which shows the current trending Github repositories.


### Tech Stack
1. Clean Architecture + MVx for Presentation Layer
2. Local Unit Testing (JUnit, MockWebServer, Mockito, Google Truth)
3. Personal libraries - Deployed on Github Package Registry.
- scopey - Control the lifecycle of presenter. It uses Google's ViewModel internally.
- local-testing - Common testing utils and dependencies.
4. Dagger-Hilt
5. Ktlint 
6. Detekt
7. Retrofit + Gson
8. Facebook Shimmer
9. Airbnb's Lottie
10. Picasso
11. Google Room's Database

### Assumptions
1. "Current" means today. So, app will cache the result of todays trending repositories and only fetch new records the next day. This is to balance between "stale data" vs "current" based on the requirements.
2. Even though the description says "Github", the core domain layer makes no assumption of whether its from any other provider. It's up to the Presenter Layer and Data Layer to decide what to do with the data. This assumption is cheap and scales well.
3. Even though its supposedly MVP, a scalable setup is done to accomodate if necessary. This includes ktlint, detekt, dependency injection framework, and etc. Setup like these are faster if Android Team has template.
4. Overflow menu was used to support switching Dark/Light theme mode.

