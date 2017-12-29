package taiwan.no.one.sample

import android.app.Application
import android.support.multidex.MultiDex

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}