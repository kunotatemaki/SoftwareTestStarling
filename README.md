## SoftwareTestStarling


In order to run the app, you need to put your own token in the `gradle.properties file``
Example:
```
USER_ACCESS_TOKEN=uu6MKvC2m9q0Wd5qjyXXe4l8m4PGbZndLcMNipdvyr7zkD52wR59Cf74kTNpuFz4
```

The first screen will show some info account and all your feeds with the potential saving for all those which are available for saving,
which means `direction == "OUT"` and `spendingCategory != SAVING`

When the FAB is clicked, the app navigates to a new screen where the total amount to save is shown, and a button
to send that money to the goal is displayed.

##### Considerations and assumptions
- The total amount to save is calculated from all available feeds, not for an specific week. This is because the automatic
generated feeds happen in the same day. To get the amount of money to save only from the latest week only the PersistenceManager 
and FeedsDao classes should be modified, including a date range in the function `getPotentialSavings` to pass it to the sql query. 
- When the money is sent to the goal, all feeds are marked as _SAVED_, so they won't be included in any future calculation
for more savings. They will be shown in the first screen with a grey colour.
- When the save button from the second screen is clicked, if a goal does not exist, a default one will be created 
automatically. Allowing the user to create a custom one is out of the scope.
- A recurrent transfer is created a deleted in any saving operation, just to get a transfer id.
- The account info and feeds are stored in a local db. For security, some of the fields can be stored encrypted. In this
test some of them have been stored as encrypted as an example. Some more could be encrypted, but then when loading the info
from the db, the time for un-encrypt it can be huge. There should be a balance.
- Not all the code has been covered with tests, but the more important parts -> DAOs, NetworkViewModel and Rounding Logic.

