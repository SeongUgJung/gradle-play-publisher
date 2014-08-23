It's Gradle Plug-in for Android Play-store Publisher API

Sorry, Watchers.

I have some complex job, In Work, Life.
I got 4 sprints to septempber in work. and I gonna finish my private job.

This project is not stopping. just hold to september mid.

I'll be back.

# Why make Gradle?

Jul 28, 2014. Google update Play store API for publisher.
but That's only Java and Python Library.
so I think need for Gradle task.


It was refer to [Google Java Sample](https://github.com/googlesamples/android-play-publisher-api/tree/master/v2/java)

# HOW TO USE

- Build Script Setup
``` groovy
buildscript {
    repositories {
        mavenCentral()

        dependencies {
            classpath 'com.nobrain.gradle:gradle-play-publisher:0.1.3'
        }
    }
}

apply plugin: 'playpublisher'

```

- Check Gradle Task
``` shell
$> ./gradlew tasks

Other tasks           
-----------           
playPublish
cleanAuth
               
```

- Gradle Publisher

``` groovy
publisher {
    applicationName "[INPUT Application Name]"
    packageName "[INPUT Package Name]"
    apkFile file("apk file")
    secretFile file('secret json file')
    productType 'production' // production, alpha, beta
}
```

# HOW TO Set 'Google Play Publisher API'
Go to [Wiki #1] (https://github.com/ZeroBrain/gradle-play-publisher/wiki/01.-How-to-Set-Google-Play-Publisher-API)

# Change Log
* 0.1.2
  - First Release
* 0.1.3
  - add task for cleaning auth store path
  - publisher.authStore is deprecated : authStore static path is ./.store
  - add validate publisher property for playPublish task 

# Future Job
  * support service oauth (now only client-secret oauth)
