package com.backrow.wps

import collection.JavaConversions._

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity extends Activity with TypedActivity {

  val TAG = "WifiPos"

  val resultsAvailable = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)

  lazy val wm = getSystemService(Context.WIFI_SERVICE).asInstanceOf[WifiManager]

  val wifiReceiver = new BroadcastReceiver() {
    def onReceive(c: Context, i: Intent) {
      findView(TR.textview).setText(
        "Results:\n" + wm.getScanResults.view.map(r => Seq(r.BSSID, ": ", r.level).mkString).mkString("\n")
      )
    }
  }

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.buttonscan).setOnClickListener(new View.OnClickListener() {
      def onClick(p1: View) {
        findView(TR.textview).setText("Starting Scan...")
        wm.startScan()
      }
    })

    registerReceiver(wifiReceiver, resultsAvailable)
  }

  override def onPause() {
    unregisterReceiver(wifiReceiver)
    super.onPause()
  }

  override def onResume() {
    registerReceiver(wifiReceiver, resultsAvailable)
    super.onResume()
  }
}