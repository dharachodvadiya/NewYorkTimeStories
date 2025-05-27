package com.allvideo.status.free.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class TestCollector<T>(
    scope: TestScope,
    numberOfEmit: Int,
    flow: StateFlow<T>,
    private val timeOut: Long,
    private val timeUnit: TimeUnit
) {
    private val values = mutableListOf<T>()
    private val countDownLatch = CountDownLatch(1)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val job = scope.launch(UnconfinedTestDispatcher((scope.testScheduler))) {
        flow.collect {
            values.add(it)
            if (values.size == numberOfEmit) {
                countDownLatch.countDown()
                this.cancel()
            }
        }
    }

    fun getData(): List<T> {
        if (!countDownLatch.await(timeOut, timeUnit)) {
            job.cancel()
            throw TimeoutException("StateFlow value was never set.")
        }
        return values
    }
}

fun <T> StateFlow<T>.test(
    scope: TestScope,
    numberOfEmit: Int = 1,
    timeOut: Long = 10,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
): TestCollector<T> {
    return TestCollector(
        scope = scope,
        numberOfEmit = numberOfEmit,
        timeOut = timeOut,
        timeUnit = timeUnit,
        flow = this
    )
}