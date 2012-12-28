package com.backrow.wps

import collection.JavaConversions._

import android.app.Activity
import android.os.Bundle
import android.net.wifi.WifiManager
import android.content.Context

class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

		val wm = getSystemService(Context.WIFI_SERVICE).asInstanceOf[WifiManager]
    findView(TR.textview).setText(wm.getScanResults.view.flatMap(r => Seq(r.BSSID, ": ", r.level, ", ")).mkString)
  }
}
