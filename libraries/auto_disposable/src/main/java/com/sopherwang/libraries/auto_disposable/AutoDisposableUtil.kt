package com.sopherwang.libraries.auto_disposable

import io.reactivex.disposables.Disposable

object AutoDisposableUtil {
    fun Disposable.addTo(autoDisposable: AutoDisposable) {
        autoDisposable.add(this)
    }
}
