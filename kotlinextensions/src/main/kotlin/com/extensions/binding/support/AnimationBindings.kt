package com.extensions.binding.support

import android.support.annotation.AnimRes
import android.view.animation.Animation
import com.extensions.binding.optionalAnimation
import com.extensions.binding.optionalAnimations
import com.extensions.binding.requiredAnimation
import com.extensions.binding.requiredAnimations
import kotlin.properties.ReadOnlyProperty
import android.support.v4.app.Fragment as SupportFragment

/**
 * SupportFragment
 */
fun SupportFragment.animation(@AnimRes animationRes: Int): ReadOnlyProperty<SupportFragment, Animation>
        = requiredAnimation(contextProvider, animationRes)

fun SupportFragment.animationOptional(@AnimRes animationRes: Int): ReadOnlyProperty<SupportFragment, Animation?>
        = optionalAnimation(contextProvider, animationRes)

fun SupportFragment.animations(@AnimRes vararg animationRes: Int): ReadOnlyProperty<SupportFragment, List<Animation>>
        = requiredAnimations(contextProvider, animationRes)

fun SupportFragment.animationsOptional(@AnimRes vararg animationRes: Int): ReadOnlyProperty<SupportFragment, List<Animation?>>
        = optionalAnimations(contextProvider, animationRes)
