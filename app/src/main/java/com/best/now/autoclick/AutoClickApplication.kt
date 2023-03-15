package com.best.now.autoclick

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.best.now.autoclick.lifecycle.KtxAppLifeObserver
import com.best.now.myad.utils.AppOpenManager
import com.best.now.myad.utils.initOkGo
import com.kodlib.megacalculator.MyApplication

/**
author:zhoujingjin
date:2022/11/27
 */
class AutoClickApplication : MyApplication() {
    companion object {
        var appOpenManager: AppOpenManager? = null
        var appIsOn = true
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        initOkGo(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(KtxAppLifeObserver())
        appOpenManager = AppOpenManager(this)
    }
}