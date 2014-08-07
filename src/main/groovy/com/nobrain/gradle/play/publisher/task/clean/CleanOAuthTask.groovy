package com.nobrain.gradle.play.publisher.task.clean

import org.gradle.api.tasks.Delete

/**
 * Created by jsuch2362 on 2014. 8. 6..
 */
class CleanOAuthTask extends Delete {

    @Override
    Delete delete(Object... targets) {

        println("Delete : $targets")

        return super.delete(targets)
    }
}
