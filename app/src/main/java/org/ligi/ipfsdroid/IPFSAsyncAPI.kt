package org.ligi.ipfsdroid

class IPFSAsyncAPI(val api: IPFSAPI) {

    fun execute(foo:()->Any) {
        Thread(Runnable {
            foo()

        }).start()
    }
}