package com.github.alexdochioiu.core.navigation

private const val PACKAGE_BASE = "com.github.alexdochioiu"
private const val PACKAGE_NAME = "$PACKAGE_BASE.core"

object Features {
    object Main : DynamicFeature() {
        override val entryActivityName: String = "$PACKAGE_BASE.main_feature.MainActivity"
    }
}

abstract class DynamicFeature internal constructor(){
    val entryPackageName: String = PACKAGE_NAME
    abstract val entryActivityName: String
}