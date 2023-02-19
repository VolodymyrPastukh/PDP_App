# PDP_1
##### App with live recipes for good cooking, learning and fun.
##### Includes topics: services, broadcast receivers, deep links. 
> Raw app only for presentation and investigating components.

Application work with Postman Mock Server and internal data store for caching
App consists of components:
1. Services
- ***CoroutineService*** - abstract service with lifecycle aware coroutines base 
- ***LoadingService*** - service for background downloading from net.
- ***LiveRecipeService*** - service for live playing of media(not only) content

2. BroadcastReceivers 
- ***DownloadingInfoReceivers*** - internal receiver for downloading notifications
- ***NetworkStateReceiver*** - context-registered receiver for network state updates

3. Deep Link
- ***https://www.pdpapp.com/*** base deep link which is registered in app
- ***https://www.pdpapp.com/recipes?id={id}*** link which redirect to specific content (example: /recipes?id=1488)
> If you use app on OS 12 or higher you have to enable deep link manually in App Settings




