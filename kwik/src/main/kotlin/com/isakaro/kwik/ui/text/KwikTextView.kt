package com.isakaro.kwik.ui.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

/**
 * `Text` component that uses the Material3 typography system.
 * */
object KwikText {

    /**
     * Base `Text` component that uses the Material3 typography system.
     *
     * @param modifier Modifier to be applied to the text
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param style Text style
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * @param onTextLayout Text layout callback
     *
     * @see [Text]
     *
     * [KwikText]'s functions like [DisplayLarge], [DisplayMedium], [BodyMedium] etc... rely on it to display text.
     * */
    @Composable
    fun RenderText(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        textDecoration: TextDecoration = TextDecoration.None,
        style: TextStyle = MaterialTheme.typography.bodyLarge,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
        onTextLayout: (TextLayoutResult) -> Unit = {},
    ) {
        when (text) {
            is String -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow,
                onTextLayout = {
                    onTextLayout(it)
                }
            )
            is Int -> Text(
                text = text.toString(),
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow,
                onTextLayout = {
                    onTextLayout(it)
                }
            )
            is AnnotatedString -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow,
                onTextLayout = {
                    onTextLayout(it)
                }
            )
        }
    }

    /**
     * Display Large Text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun DisplayLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        fontStyle: FontStyle? = null,
        textAlign: TextAlign = TextAlign.Start,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.displayLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Medium Text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun DisplayMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.displayMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Small Text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun DisplaySmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.displaySmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Headline large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun HeadlineLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Headline medium
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun HeadlineMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Headline small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun HeadlineSmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Title large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun TitleLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.titleLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Title medium text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun TitleMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.titleMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Title small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun TitleSmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.titleSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Body large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun BodyLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Body medium text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun BodyMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Body small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun BodySmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.bodySmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Label large text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun LabelLarge(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.labelLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Label medium text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun LabelMedium(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.labelMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Label small text
     *
     * @param modifier
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun LabelSmall(
        modifier: Modifier = Modifier,
        text: Any,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            fontStyle = fontStyle,
            style = MaterialTheme.typography.labelSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * A quote text component for displaying quotes.
     *
     * @param modifier Modifier to be applied to the text
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param fontStyle Font style
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
     * */
    @Composable
    fun Quote(
        modifier: Modifier = Modifier,
        text: Any,
        author: String? = null,
        color: Color = MaterialTheme.colorScheme.onSurface,
        fontWeight: FontWeight? = null,
        textAlign: TextAlign = TextAlign.Start,
        fontStyle: FontStyle = FontStyle.Italic,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        Row(
            modifier = modifier.fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RenderText(
                modifier = modifier,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.displaySmall.fontSize)) {
                        append("“")
                        append(" ")
                    }
                    append(text.toString())
                    if(author != null) {
                        append(" ")
                        append("-")
                        append(" ")
                        append(author)
                    }
                },
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                fontStyle = fontStyle,
                style = MaterialTheme.typography.titleSmall,
                maxLines = maxLines,
                overflow = overflow
            )
        }
    }

}