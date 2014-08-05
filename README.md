It's Gradle Plug-in for Android Play-store Publisher API

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
            classpath 'com.nobrain.gradle:gradle-play-publisher:0.1.2'
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
               
```

- Gradle Publisher

``` groovy
publisher {
    applicationName "[INPUT Application Name]"
    packageName "[INPUT Package Name]"
    apkFile = new File("apk file")
    secretFile = new File('secret json file')
    authStore = new File('authStore path')
    productType 'production' // production, alpha, beta
}
```

# HOW TO Set 'Google Play Publisher API'
Coming soon...
