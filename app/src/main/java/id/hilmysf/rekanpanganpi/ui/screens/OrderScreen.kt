import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import id.hilmysf.rekanpanganpi.data.local.entity.OrderEntity
import id.hilmysf.rekanpanganpi.viewmodel.OrderViewModel

@Composable
fun OrderScreen(

    viewmodel: OrderViewModel = hiltViewModel(),
    onOrderClick: (OrderEntity) -> Unit = {}
) {
    val orders by viewmodel.orders.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(orders.size) { index ->
            val order = orders[index]
            OrderItem(order = order, onClick = { onOrderClick(order) })
        }
    }
}

@Composable
fun OrderItem(order: OrderEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: ${order.orderId}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Address: ${order.address}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Total: $${order.totalAmount}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
