package com.app.androidarcore

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private lateinit var renderableSource: RenderableSource
    private lateinit var renderable: ModelRenderable
    private lateinit var fragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the 3D Model Resources
        renderableSource = RenderableSource.builder()
            .setSource(
                this,
                Uri.parse("assets/model.glb"),
                RenderableSource.SourceType.GLB
            )
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        ModelRenderable.builder()
            .setSource(this, renderableSource)
            .build()
            .thenAccept { modelRenderable ->
                renderable = modelRenderable

                fragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment

                fragment.setOnTapArPlaneListener { hitResult, _, _ ->
                    val anchorNode = AnchorNode(hitResult.createAnchor())
                    anchorNode.setParent(fragment.arSceneView.scene)
                    val transformableNode = TransformableNode(fragment.transformationSystem)
                    transformableNode.renderable = renderable
                    transformableNode.setParent(anchorNode)
                    transformableNode.select()
                }
            }
            .exceptionally { throwable ->
                return@exceptionally null
            }
    }
}
