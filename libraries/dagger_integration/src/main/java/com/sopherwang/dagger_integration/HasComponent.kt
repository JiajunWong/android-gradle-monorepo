package com.sopherwang.dagger_integration

interface HasComponent<T> {
  fun component(): T
}
