package com.davec.expenses.activities


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.view.Surface
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.davec.expenses.R
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity(), LifecycleOwner {

    private val REQUEST_CODE_PERMISSIONS=999
    private val REQUIRED_PERMISSIONS=arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        if (allPermissionsGranted()) {
            textureView.post {
                //startCameraForCapture()
            }
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        setTitle("Camera")
    }

    private fun startCameraForCapture() {
        //====================== Image Preview Config code Start==========================
        // Create configuration object for the viewfinder use case
//        val previewConfig=PreviewConfig.Builder().apply {
//            setTargetAspectRatio(Rational(1,1))
//            setTargetResolution(Size(640,640))
//        }.build()

        // Build the viewfinder use case
        // val preview = Preview(previewConfig)

        // Every time the viewfinder is updated, recompute layout
//        preview.setOnPreviewOutputUpdateListener {
//            // To update the SurfaceTexture, we have to remove it and re-add it
//            val parent = textureView.parent as ViewGroup
//            parent.removeView(textureView)
//            parent.addView(textureView,0)
//            textureView.surfaceTexture=it.surfaceTexture
//            updateTransform()
//        }
        //====================== Image Preview Config code End==========================


        //====================== Image CAPTURE Config code Start==========================
//        val imageCaptureConfig=ImageCaptureConfig.Builder().apply {
//            setTargetAspectRatio(Rational(1,1))
//            // We don't set a resolution for image capture; instead, we
//            // select a capture mode which will infer the appropriate
//            // resolution based on aspect ration and requested mode
//            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
//        }.build()

        // Build the viewfinder use case
        //val imageCapture = ImageCapture(imageCaptureConfig)

//        capture_button.setOnClickListener {
//            val file = File(externalMediaDirs.first(),"${System.currentTimeMillis()}.jpg")
//            imageCapture.takePicture(file,object :ImageCapture.OnImageSavedListener{
//                override fun onImageSaved(file: File) {
//                    val msg = "Photo capture succeeded: ${file.absolutePath}"
//                    msg.toast()
//                }
//
//                override fun onError(useCaseError: ImageCapture.UseCaseError, message: String, cause: Throwable?) {
//                    val msg = "Photo capture failed: $message"
//                    msg.toast()
//                    cause?.printStackTrace()
//                }
//            })
//        }
        //====================== Image CAPTURE Config code End==========================


        //====================== Image Analysis Config code Start==========================

        // Setup image analysis pipeline that computes average pixel luminance
//        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
//            // Use a worker thread for image analysis to prevent glitches
//            val analyzerThread = HandlerThread("AnalysisThread").apply {
//                start()
//            }
//            setCallbackHandler(Handler(analyzerThread.looper))
//            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
//        }.build()

//        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
//            analyzer=LuminosityAnalyzer()
//        }

        //====================== Image Analysis Config code End==========================

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.

        //CameraX.bindToLifecycle(this,preview) // For Preview

        //CameraX.bindToLifecycle(this,preview,imageCapture) // For Preview and image Capture

        //CameraX.bindToLifecycle(this,preview,imageCapture,analyzerUseCase)
        // For Preview, image Capture and analysis use case

    }

    private fun updateTransform() {
        val matrix=Matrix()

        // Compute the center of the view finder
        val centerX=textureView.width / 2f
        val centerY=textureView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegree=when (textureView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegree.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        textureView.setTransform(matrix)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                textureView.post {
                    //startCameraForPreview()
                    // startCameraForCapture()
                }
            } else {
                "Permissions not granted by the user.".toast()
                finish()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun String.toast() {
        Toast.makeText(
            this@CameraActivity,
            this,
            Toast.LENGTH_SHORT
        ).show()
    }

//    private class LuminosityAnalyzer:ImageAnalysis.Analyzer{
//        private var lastAnalyzedTimestamp = 0L
//        /**
//         * Helper extension function used to extract a byte array from an
//         * image plane buffer
//         */
//        private fun ByteBuffer.toByteArray():ByteArray{
//            rewind() //Rewind buffer to zero
//            val data=ByteArray(remaining())
//            get(data)  // Copy buffer into byte array
//            return data // Return byte array
//        }
//        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
//            val currentTimestamp =System.currentTimeMillis()
//            // Calculate the average luma no more often than every second
//            if(currentTimestamp-lastAnalyzedTimestamp>=TimeUnit.SECONDS.toMillis(1)){
//                // Since format in ImageAnalysis is YUV, image.planes[0]
//                // contains the Y (luminance) plane
//                val buffer = image.planes[0].buffer
//                // Extract image data from callback object
//                val data = buffer.toByteArray()
//                // Convert the data into an array of pixel values
//                val pixels = data.map { it.toInt() and 0xFF }
//                // Compute average luminance for the image
//                val luma = pixels.average()
//                // Log the new luma value
//                Log.d( "CameraX Demo" , "Average luminosity: $luma")
//                // Update timestamp of last analyzed frame
//                lastAnalyzedTimestamp = currentTimestamp
//            }
//        }
//    }
}
