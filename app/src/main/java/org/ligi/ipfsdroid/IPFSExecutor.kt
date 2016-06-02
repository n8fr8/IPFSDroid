package org.ligi.ipfsdroid

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import org.ligi.tracedroid.logging.Log

class IPFSExecutor(val context: Context) {

    fun isReady(): Boolean {
        return true
    }

    fun run(cmd: String): String {
        return go.ipfslib.Ipfslib.Start(context.filesDir.absolutePath,cmd)
    }

    fun runWithAlert(ctx: Context, command: String) {
        Log.i("executing $command")
        val handler = Handler()
        val progressDialog = ProgressDialog(ctx)
        progressDialog.setMessage("executing ipfs " + command)
        progressDialog.show();

        Thread(Runnable {
            val run = run(command)
            handler.post {
                progressDialog.dismiss()
                AlertDialog.Builder(ctx)
                        .setMessage(run)
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
            }
        }).start()

    }
}