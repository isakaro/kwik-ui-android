package com.isakaro.kwik.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow

/**
 * This a text component that uses the Material3 typography system.
 * */
object KwikText {

    /**
     * Base text component that uses the Material3 typography system.
     *
     * @param modifier Modifier to be applied to the text
     * @param text Text to display. Can be String, Int or AnnotatedString
     * @param color Text color
     * @param fontWeight Font weight
     * @param textAlign Text alignment
     * @param style Text style
     * @param maxLines Maximum number of lines to display
     * @param overflow Text overflow behavior
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
        textDecoration: TextDecoration = TextDecoration.None,
        style: TextStyle = MaterialTheme.typography.bodyLarge,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        when (text) {
            is String -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow
            )
            is Int -> Text(
                text = text.toString(),
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow
            )
            is AnnotatedString -> Text(
                text = text,
                style = style,
                color = color,
                fontWeight = fontWeight,
                textAlign = textAlign,
                modifier = modifier,
                textDecoration = textDecoration,
                maxLines = maxLines,
                overflow = overflow
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.displaySmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Headline large text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Headline medium
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Headline small text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Title large text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.titleLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display Title medium text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.titleMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display title small text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.titleSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display body large text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display body medium text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display body small text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.bodySmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display label large text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.labelLarge,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display label medium text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.labelMedium,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    /**
     * Display label small text
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
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Ellipsis,
    ) {
        RenderText(
            modifier = modifier,
            text = text,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            style = MaterialTheme.typography.labelSmall,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}