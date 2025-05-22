@file:OptIn(ExperimentalMaterial3Api::class)

package id.hilmysf.rekanpanganpi.ui.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.hilmysf.rekanpanganpi.data.local.entity.CartEntity
import id.hilmysf.rekanpanganpi.data.model.Product
import id.hilmysf.rekanpanganpi.ui.components.QuantitySelection
import id.hilmysf.rekanpanganpi.viewmodel.DashboardViewModel
import id.hilmysf.rekanpanganpi.viewmodel.UiState

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onCartClick: () -> Unit,
    onLogout: () -> Unit
) {
    val productState = viewModel.productsState
    val cartState: State<List<CartEntity>> = viewModel.getCartItems().collectAsStateWithLifecycle(
        initialValue = emptyList(),
        lifecycle = LocalLifecycleOwner.current.lifecycle,
    )


    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Rekan Pangan")
                },
                actions = {
                    Row {
                        IconButton(onClick = {
                            onCartClick()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart"
                            )
                        }
                        IconButton(onClick = {
                            viewModel.logout()
                            onLogout()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = "Logout"
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (productState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is UiState.Success -> {
                    val products = productState.data
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = innerPadding.calculateBottomPadding() + 16.dp
                        ), verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(products.size) { index ->
                            GridItemCard(products[index], cartState.value, viewModel)
                        }
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = "Terjadi kesalahan: ${productState.message}",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun GridItemCard(product: Product, cartState: List<CartEntity>, viewModel: DashboardViewModel) {
    val isCartContainProduct = cartState.any {
        it.productId == product.id
    }

    var productCart = cartState.find {
        it.productId == product.id
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .height(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image from internet",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = product.title!!,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall,
            )
//            Text(
//                text = "quantity ${product.stock}",
//                color = Color.Black.copy(alpha = 0.7f),
//                style = MaterialTheme.typography.bodySmall
//            )
//            Spacer(Modifier.height(4.dp))
            Row {
                Text(
                    text = "$${product.price}",
                    color = Color.Black.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.weight(1f))
                if (isCartContainProduct) {
                    QuantitySelection(
                        quantity = productCart?.quantity ?: 1,
                        maxQuantity = 10,
                        isEnabled = true,
                        onChanged = { value ->
                            Log.d(TAG, "onChanged: ${value}")
                            if (value == 0) {
                                viewModel.removeFromCart(productCart!!.cartId)
                            }
                            if (productCart?.cartId != null) {
                                viewModel.updateQuantity(
                                    cartId = productCart.cartId,
                                    quantity = value
                                )

                            }
                            Log.d(TAG, "productCart.quantity: ${productCart?.quantity}")
                        }
                    )
                } else {
                    TextButton(onClick = {
                        viewModel.addToCart(product)
                    }) {
                        Text("Add to cart")
                    }
                }


            }
        }
    }
}
