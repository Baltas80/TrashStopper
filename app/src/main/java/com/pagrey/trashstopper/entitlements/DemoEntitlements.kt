package com.pagrey.trashstopper.entitlements

import com.pagrey.trashstopper.BuildConfig

/** Local-only entitlement model used by debug builds to preview paid experiences. */
enum class DemoPlan {
    FREE,
    PREMIUM,
    FAMILY
}

object DemoEntitlements {
    fun isDebugDemoAvailable(): Boolean = BuildConfig.DEBUG
}
