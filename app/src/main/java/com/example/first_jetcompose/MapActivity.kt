package com.example.first_jetcompose

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class MapActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        //MapsScreen(modifier = Modifier.weight(1F))
                    }
                }
            }
        }
    }


    /*@Composable
    fun MapsScreen(modifier: Modifier = Modifier) {
        val initialPosition = LatLng(34.6767, 33.04455)
        var currentPosition by rememberSaveable { mutableStateOf(initialPosition) }

        Surface(
            color = Color.White,
            modifier = modifier.fillMaxWidth()
        ) {
            MapViewContainer(
                modifier = Modifier.fillMaxSize(),
                initialPosition = initialPosition,
                onMarkerDrag = { newPosition -> currentPosition = newPosition }
            )

            IconButton(
                onClick = {
                    Toast.makeText(
                        baseContext,
                        "Position: ${currentPosition.latitude}:${currentPosition.longitude}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) { Text(text = "Return Back") }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    @Composable
    fun MapViewContainer(
        modifier: Modifier = Modifier,
        initialPosition: LatLng,
        onMarkerDrag: (LatLng) -> Unit
    ) {
        var mapView: MapView? by remember { mutableStateOf(null) }

        DisposableEffect(Unit) {
            mapView?.onCreate(Bundle())
            onDispose { mapView?.onDestroy() }
        }

        AndroidView(
            factory = { context ->
                mapView = MapView(context)
                mapView!!
            },
            modifier = modifier
        )

        LaunchedEffect(mapView) {
            mapView!!.getMapAsync { googleMap ->
                googleMap.apply {
                    // Set map style
                    setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(baseContext, R.raw.map_style)
                    )

                    // Add a marker to the map
                    val markerOptions = MarkerOptions()
                        .position(initialPosition)
                        .title("Marker Title")
                        .draggable(true)
                    val marker = addMarker(markerOptions)

                    // Set the initial camera position
                    val cameraPosition = CameraPosition.Builder()
                        .target(initialPosition)
                        .zoom(12f)
                        .build()
                    moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

                    // Set marker drag listener
                    setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                        override fun onMarkerDragStart(marker: Marker) {}

                        override fun onMarkerDrag(marker: Marker) {}

                        override fun onMarkerDragEnd(marker: Marker) {
                            val newPosition = marker.position
                            onMarkerDrag(newPosition)
                        }
                    })
                } // apply{}
            }
        } // LaunchedEffect()
    }


    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(map: GoogleMap) {
        if (
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            &&
            (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                baseContext as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        map.isMyLocationEnabled = true
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13F))
        val zoom = CameraUpdateFactory.zoomTo(15F)
        map.animateCamera(zoom)
        map.addMarker(
            MarkerOptions()
                .title("Shop")
                .snippet("Is this the right location?")
                .position(position)
                .draggable(true)
        )

        map.setOnInfoWindowClickListener(this)
        map.setOnMarkerDragListener(this)
    }

    override fun onInfoWindowClick(marker: Marker) {
        Toast.makeText(this, marker.title, Toast.LENGTH_LONG).show()
    }

    override fun onMarkerDrag(marker: Marker) {
        val position0: LatLng = marker.position
        Log.d(localClassName, "Drag from ${position0.latitude} : ${position0.longitude}")
    }

    override fun onMarkerDragEnd(marker: Marker) {
        position = marker.position
        Log.d(localClassName, "Drag from ${position.latitude} : ${position.longitude}")
    }

    override fun onMarkerDragStart(marker: Marker) {
        val position0: LatLng = marker.position
        Log.d(localClassName, "Drag from ${position0.latitude} : ${position0.longitude}")
    }*/
}
