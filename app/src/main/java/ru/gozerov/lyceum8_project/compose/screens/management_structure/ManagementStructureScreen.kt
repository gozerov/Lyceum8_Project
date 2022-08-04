package ru.gozerov.lyceum8_project.compose.screens.management_structure

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.gozerov.lyceum8_project.R

@Composable
fun ManagementStructureScreen() {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_management_structure),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        val firstLine = buildAnnotatedString {
            append("Наблюдательный совет (в соответствии с ФЗ- 174), ")

            pushStringAnnotation(tag = "174", annotation = "may be url")
            withStyle(SpanStyle(color = Color.Blue)) {
                append("(Устав (изменения))")
            }

            pop()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.clickable { }) {
            ClickableText(
                text = firstLine,
                style = TextStyle(color = Color.Black, fontSize = 18.sp),
                onClick = { offset ->
                    firstLine.getStringAnnotations(tag = "174", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            Log.e("TAG", annotation.item)
                        }

                }
            )
        }
        Text(
            text = stringResource(id = R.string.main_text_management_structure),
            fontSize = 16.sp
        )
    }
}
