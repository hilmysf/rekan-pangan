package id.hilmysf.rekanpanganpi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log

@Composable
fun QuantitySelection(
    quantity: Int,
    maxQuantity: Int,
    isEnabled: Boolean,
    onChanged: (newValue: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonColor = if (isEnabled) MaterialTheme.colorScheme.primary else Color.Gray
    val textColor = if (isEnabled) Color.Black else Color.Gray

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                if (quantity > 0) {
                    onChanged(quantity - 1)
                }
            },
            enabled = isEnabled,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = buttonColor)
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Text Quantity and max
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = quantity.toString(),
                color = textColor,
                modifier = Modifier.width(24.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "/$maxQuantity",
                color = textColor,
                modifier = Modifier.padding(start = 4.dp),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(
            onClick = {
                Log.d("Increment", "Quantity: $quantity")
                if (quantity < maxQuantity) {
                    onChanged(quantity + 1)
                }
            },
            enabled = isEnabled,
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = if (isPressed) buttonColor.copy(alpha = 0.2f) else Color.Transparent,
                    shape = CircleShape
                ),
            interactionSource = interactionSource
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increase", tint = buttonColor)
        }
    }
}
