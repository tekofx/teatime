package dev.tekofx.teatime.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import dev.tekofx.teatime.model.Tea
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import teatime.composeapp.generated.resources.Res
import teatime.composeapp.generated.resources.emoji_food_beverage
import teatime.composeapp.generated.resources.timer_filled


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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(Res.drawable.emoji_food_beverage),
                contentDescription = ""
            )
            Text(tea.name)
            Text("${tea.temperature} ª ${tea.formattedTime}")
        }
    }
}