# Github-Search-API-and-Local-DataStore
android simple application with common features such a 
1. search feature,
2. list of user and bookmarked user,
3. detail user,
4. theme that uses local datastore to store the theme preferences,

there's some endpoint for getting raw data
1. Search : https://api.github.com/search/users?q={username}
2. Detail user : https://api.github.com/users/{username}
3. Follower : https://api.github.com/users/{username}/followers
4. Following : https://api.github.com/users/{username}/following

this application already implement
1. Room,
2. Repository and Injection,
3. ViewModels,
4. DataStore,
5. API,
6. LiveData,
7. SearchBar and SearchView,
8. Tab Layout with ViewPager2, etc.
