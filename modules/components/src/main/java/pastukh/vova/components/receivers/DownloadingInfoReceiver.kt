package pastukh.vova.components.receivers

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pastukh.vova.components.R
import pastukh.vova.components.services.ServiceConstants
import pastukh.vova.components.services.media.LiveRecipeService

class DownloadingInfoReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "DownloadingInfoReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getStringExtra(Constants.Key.ID)
        val title = intent?.getStringExtra(Constants.Key.TITLE)
        Log.i(TAG, "receive $id:$title")

        AlertDialog.Builder(context).apply {
            setTitle(R.string.dialog_downloading_complete_title)
            setMessage(context?.getString(R.string.dialog_downloading_complete_message, title))
            setPositiveButton(R.string.button_yes) { _, _ ->
                context?.startService(
                    Intent(context, LiveRecipeService::class.java).apply {
                        putExtra(ServiceConstants.EXTRA_RECIPE_URL, id)
                    }
                )
            }
            setNegativeButton(R.string.button_cancel) { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    object Constants {
        const val ACTION = "pastukh.vova.pdpapp.RECIPE_DOWNLOADING"

        object Key {
            const val ID = "id"
            const val TITLE = "title"
        }
    }
}