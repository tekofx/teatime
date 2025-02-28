package dev.tekofx.teatime.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import dev.tekofx.teatime.model.Tea
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalNotificationsApi::class, ExperimentalResourceApi::class)
@Composable
fun TeaButton(
    tea: Tea,
    activeTea: Tea?,
    onClick: (Tea)->Unit,
) {


    Button(
        onClick = {
            onClick(tea)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (activeTea == tea) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primary
        )
    ) {
        Column {
            Text(tea.name)
            Text("${tea.temperature} ªC")
            Text(tea.formattedTime)
            Text(tea.quantity)
        }
    }
}