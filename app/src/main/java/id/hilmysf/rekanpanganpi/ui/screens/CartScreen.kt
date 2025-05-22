package id.hilmysf.rekanpanganpi.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel(),
    onCheckoutClick: () -> Unit = {},
    onOpenMapClick: (onLocationSelected: (address: String, lat: Double, lng: Double) -> Unit) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle(initialValue = emptyList())
    var selectedAddress by remember { mutableStateOf<String?>(null) }
    var selectedLatLng by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val address = savedStateHandle?.get<String>("selected_address")
    val lat = savedStateHandle?.get<Double>("selected_lat")
    val lng = savedStateHandle?.get<Double>("selected_lng")

    LaunchedEffect(Unit) {
        if (address != null && lat != null && lng != null) {
            println("Alamat: $address, Lat: $lat, Lng: $lng")
            selectedAddress = address
            selectedLatLng = lat to lng
        }
    }
    Scaffold(
        topBar = {
            if (cartItems.isNotEmpty()) {
                BottomAppBar(
                    modifier = Modifier
                        .padding(WindowInsets.statusBars.asPaddingValues()),
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                onOpenMapClick { address, lat, lng ->
                                    selectedAddress = address
                                    selectedLatLng = lat to lng
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (selectedAddress != null && selectedLatLng != null) {
                                Text(
                                    text = "$selectedAddress",
                                    maxLines = 2,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else {
                                Text("Pilih Lokasi di Map")
                            }
                        }

//                        if (selectedAddress != null && selectedLatLng != null) {
//                            Text(
//                                text = "📍 $selectedAddress\n(${selectedLatLng!!.first}, ${selectedLatLng!!.second})",
//                                modifier = Modifier
//                                    .padding(horizontal = 16.dp)
//                                    .fillMaxWidth(),
//                                style = MaterialTheme.typography.bodySmall
//                            )
//                        }


                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total: $${cartItems.sumOf { it.price!! * it.quantity }}",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Button(
                        onClick = {
                            if (selectedLatLng == null) {
                                Toast.makeText(
                                    context,
                                    "Pilih lokasi terlebih dahulu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                savedStateHandle?.remove<String>("selected_address")
                                savedStateHandle?.remove<Double>("selected_lat")
                                savedStateHandle?.remove<Double>("selected_lng")
                                coroutineScope.launch {
                                    viewModel.createOrder(
                                        totalAmount = cartItems.sumOf { it.price!! * it.quantity },
                                        products = cartItems,
                                        address = selectedAddress!!,
                                        latitude = selectedLatLng!!.first,
                                        longitude = selectedLatLng!!.second,
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    "Pesanan berhasil dibuat",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onCheckoutClick()
                            }
                        }
                    ) {
                        Text("Checkout")
                    }
                }
            }
        }
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Keranjang kosong")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { item ->
                    CartItemCard(item)
                }
            }
        }
    }
}

@Composable
fun CartItemCard(cartItem: CartEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartItem.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image from internet",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = cartItem.title ?: "Unknown Product",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Harga: $${cartItem.price} x ${cartItem.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Subtotal: $${cartItem.price!! * cartItem.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
