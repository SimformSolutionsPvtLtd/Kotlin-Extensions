package com.extensions.spanner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.MaskFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.FontRes
import android.support.v4.content.res.ResourcesCompat
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.AlignmentSpan
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.ClickableSpan
import android.text.style.DrawableMarginSpan
import android.text.style.DynamicDrawableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.IconMarginSpan
import android.text.style.ImageSpan
import android.text.style.LeadingMarginSpan
import android.text.style.MaskFilterSpan
import android.text.style.QuoteSpan
import android.text.style.RelativeSizeSpan
import android.text.style.ScaleXSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.TextAppearanceSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.view.View
import java.util.*

@Suppress("unused")
open class SpanBuilder {

    private val spans = ArrayList<SpanBuilder>()

    open fun build(builder: SpannableStringBuilder = SpannableStringBuilder()): Spannable {
        spans.forEach { span -> span.build(builder) }
        return builder
    }

    fun span(what: Any, init: Node.() -> Unit): SpanBuilder {
        val child = Node(what)
        child.init()
        spans.add(child)
        return this
    }

    fun text(content: String): SpanBuilder {
        spans.add(Leaf(content))
        return this
    }

    operator fun String.unaryPlus() = text(this)

    fun style(style: Int, span: StyleSpan = StyleSpan(style), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun normal(span: StyleSpan = StyleSpan(Typeface.NORMAL), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun bold(span: StyleSpan = StyleSpan(Typeface.BOLD), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun italic(span: StyleSpan = StyleSpan(Typeface.ITALIC), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun boldItalic(span: StyleSpan = StyleSpan(Typeface.BOLD_ITALIC), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun underline(span: UnderlineSpan = UnderlineSpan(), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun fontface(context :Context, @FontRes family: Int, span: CustomTypefaceSpan = CustomTypefaceSpan(ResourcesCompat.getFont(context, family)!!), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun typeface(family: String, span: TypefaceSpan = TypefaceSpan(family), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun sansSerif(span: TypefaceSpan = TypefaceSpan("sans-serif"), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun serif(span: TypefaceSpan = TypefaceSpan("serif"), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun monospace(span: TypefaceSpan = TypefaceSpan("monospace"), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun appearance(context: Context, appearance: Int, span: TextAppearanceSpan = TextAppearanceSpan(context, appearance), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun superscript(span: SuperscriptSpan = SuperscriptSpan(), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun subscript(span: SubscriptSpan = SubscriptSpan(), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun strikethrough(span: StrikethroughSpan = StrikethroughSpan(), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun scaleX(proportion: Float, span: ScaleXSpan = ScaleXSpan(proportion), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun relativeSize(proportion: Float, span: RelativeSizeSpan = RelativeSizeSpan(proportion), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun absoluteSize(size: Int, dip: Boolean = false, span: AbsoluteSizeSpan = AbsoluteSizeSpan(size, dip), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun quote(color: Int = Color.BLACK, span: QuoteSpan = QuoteSpan(color), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun mask(filter: MaskFilter, span: MaskFilterSpan = MaskFilterSpan(filter), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun leadingMargin(every: Int = 0, span: LeadingMarginSpan = LeadingMarginSpan.Standard(every), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun leadingMargin(first: Int = 0, rest: Int = 0, span: LeadingMarginSpan = LeadingMarginSpan.Standard(first, rest), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun foregroundColor(color: Int = Color.BLACK, span: ForegroundColorSpan = ForegroundColorSpan(color), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun backgroundColor(color: Int = Color.BLACK, span: BackgroundColorSpan = BackgroundColorSpan(color), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun bullet(gapWidth: Int = 0, color: Int = Color.BLACK, span: BulletSpan = BulletSpan(gapWidth, color), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun align(align: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL, span: AlignmentSpan.Standard = AlignmentSpan.Standard(align), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun drawableMargin(drawable: Drawable, padding: Int = 0, span: DrawableMarginSpan = DrawableMarginSpan(drawable, padding), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun iconMargin(bitmap: Bitmap, padding: Int = 0, span: IconMarginSpan = IconMarginSpan(bitmap, padding), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun image(context: Context, bitmap: Bitmap, verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM, span: ImageSpan = ImageSpan(context, bitmap, verticalAlignment), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun image(drawable: Drawable, verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM, span: ImageSpan = ImageSpan(drawable, verticalAlignment), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun image(context: Context, uri: Uri, verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM, span: ImageSpan = ImageSpan(context, uri, verticalAlignment), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)
    fun image(context: Context, resourceId: Int, verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BOTTOM, span: ImageSpan = ImageSpan(context, resourceId, verticalAlignment), init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun clickable(onClick: (ClickableSpan) -> Unit, span: ClickableSpan = object : ClickableSpan() {
        override fun onClick(view: View?) {
            onClick(this)
        }
    }, init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    fun url(url: String, onClick: (URLSpan) -> Boolean, span: URLSpan = object : URLSpan(url) {
        override fun onClick(widget: View?) {
            if (onClick(this)) {
                super.onClick(widget)
            }
        }
    }, init: SpanBuilder.() -> Unit): SpanBuilder = span(span, init)

    class Node(val span: Any) : SpanBuilder() {
        override fun build(builder: SpannableStringBuilder): Spannable {
            val start = builder.length
            super.build(builder)
            builder.setSpan(span, start, builder.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            return builder
        }
    }

    class Leaf(val content: Any) : SpanBuilder() {
        override fun build(builder: SpannableStringBuilder): Spannable {
            builder.append(content.toString())
            return builder
        }
    }

}

fun span(init: SpanBuilder.() -> Unit): SpanBuilder {
    val spanWithChildren = SpanBuilder()
    spanWithChildren.init()
    return spanWithChildren
}