package com.rom.moxo.internal

import kotlinx.coroutines.*

fun<T> lazydeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy{
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}