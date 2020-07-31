package com.example.mvvm_hilt.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.mvvm_hilt.GlideApp
import com.example.mvvm_hilt.IAct
import com.example.mvvm_hilt.TransformationCompat
import com.example.mvvm_hilt.databinding.ActivityDetailBinding
import com.example.mvvm_hilt.entity.Pokemon
import com.example.mvvm_hilt.entity.PokemonInfo
import com.example.mvvm_hilt.ext.gone
import com.example.mvvm_hilt.ext.observe
import com.example.mvvm_hilt.ext.toast
import com.example.mvvm_hilt.utils.PokemonTypeUtils
import com.example.mvvm_hilt.utils.SpacesItemDecoration
import com.google.android.material.imageview.ShapeableImageView
import com.skydoves.androidribbon.ribbonView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * create by silladus 2020/7/28
 * github:https://github.com/silladus
 * des:
 */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), IAct {

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setTransitionName(findViewById(android.R.id.content), "anim")

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonItem: Pokemon = requireNotNull(intent.getParcelableExtra(EXTRA_POKEMON))

        binding.apply {
            arrow.setOnClickListener { onBackPressed() }
            name.text = pokemonItem.name
            GlideApp.with(this@DetailActivity)
                    .load(pokemonItem.getImageUrl())
                    .into(image)

            GlideApp.with(this@DetailActivity)
                    .asBitmap()
                    .load(pokemonItem.getImageUrl())
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                                resource: Bitmap,
                                model: Any?,
                                target: Target<Bitmap>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                        ): Boolean {
                            lifecycleScope.launch {
                                withContext(SupervisorJob() + Dispatchers.IO) {
                                    Palette.from(resource).generate()
                                }.dominantSwatch?.apply {
                                    header.setBackgroundColor(rgb)
                                    window.statusBarColor = rgb
                                }
                            }
                            return false
                        }
                    })
                    .into(object : CustomViewTarget<ShapeableImageView, Bitmap>(header) {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {

                        }

                        override fun onResourceCleared(placeholder: Drawable?) {

                        }
                    })

            viewModel.apply {
                isLoading.observe(this@DetailActivity) {
                    progressLoading.gone(!it)
                }
                liveData.observe(this@DetailActivity) { (data, err) ->
                    if (err != null) {
                        toast { err.message }
                        return@observe
                    }

                    data.apply {
                        tvIndex.text = getIdString()
                        tvWeight.text = getWeightString()
                        tvHeight.text = getHeightString()

                        progressHp.apply {
                            labelText = getHpString()
                            max = PokemonInfo.maxHp.toFloat()
                            progress = hp.toFloat()
                        }

                        progressAttack.apply {
                            labelText = getAttackString()
                            max = PokemonInfo.maxAttack.toFloat()
                            progress = attack.toFloat()
                        }

                        progressDefense.apply {
                            labelText = getDefenseString()
                            max = PokemonInfo.maxDefense.toFloat()
                            progress = defense.toFloat()
                        }

                        progressSpeed.apply {
                            labelText = getSpeedString()
                            max = PokemonInfo.maxSpeed.toFloat()
                            progress = speed.toFloat()
                        }

                        progressExp.apply {
                            labelText = getExpString()
                            max = PokemonInfo.maxExp.toFloat()
                            progress = exp.toFloat()
                        }

                        ribbonRecyclerView.clear()
                        for (type in types) {
                            with(ribbonRecyclerView) {
                                addRibbon(
                                        ribbonView(context) {
                                            setText(type.type.name)
                                            setTextColor(Color.WHITE)
                                            setPaddingLeft(84f)
                                            setPaddingRight(84f)
                                            setPaddingTop(2f)
                                            setPaddingBottom(10f)
                                            setTextSize(16f)
                                            setRibbonRadius(120f)
                                            setTextStyle(android.graphics.Typeface.BOLD)
                                            setRibbonBackgroundColorResource(PokemonTypeUtils.getTypeColor(type.type.name))
                                        }.apply {
                                            maxLines = 1
                                            gravity = Gravity.CENTER
                                        }
                                )
                                addItemDecoration(SpacesItemDecoration())
                            }
                        }
                    }
                }

                getData(pokemonItem.name)
            }
        }
    }

    companion object {

        private const val EXTRA_POKEMON = "EXTRA_POKEMON"

        fun start(transform: View, pokemon: Pokemon) {
            TransformationCompat.startActivity(transform) {
                Intent(it, DetailActivity::class.java)
                        .putExtra(EXTRA_POKEMON, pokemon)
            }
        }
    }
}