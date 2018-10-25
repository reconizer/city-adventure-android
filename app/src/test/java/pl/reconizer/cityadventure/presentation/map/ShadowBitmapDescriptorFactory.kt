package pl.reconizer.cityadventure.presentation.map

import android.graphics.Bitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import com.nhaarman.mockitokotlin2.mock
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements


@Implements(BitmapDescriptorFactory::class)
class ShadowBitmapDescriptorFactory {
    companion object {

        internal var counter = 0
        internal var descriptor = BitmapDescriptor(mock())

        fun resetCounter() {
            counter = 0
        }

        @Implementation
        fun fromBitmap(image: Bitmap): BitmapDescriptor {
            counter++
            return descriptor
        }
    }
}