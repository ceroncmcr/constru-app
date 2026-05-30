package com.example.construapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.construapp.ui.dashboard.DashboardScreen
import com.example.construapp.ui.login.LoginScreen
import com.example.construapp.ui.productdetail.ProductDetailScreen
import com.example.construapp.ui.productform.ProductFormScreen
import com.example.construapp.ui.productlist.ProductListScreen

@Composable
fun ConstruAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onVerProductos = { navController.navigate(Screen.ProductList.route) },
                onAgregarProducto = { navController.navigate(Screen.ProductCreate.route) }
            )
        }

        composable(Screen.ProductList.route) {
            ProductListScreen(
                onBack = { navController.popBackStack() },
                onAgregar = { navController.navigate(Screen.ProductCreate.route) },
                onProductoClick = { id ->
                    navController.navigate(Screen.ProductDetail.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument(Screen.ProductDetail.ARG_PRODUCT_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong(Screen.ProductDetail.ARG_PRODUCT_ID) ?: 0L
            ProductDetailScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                onEditar = { id -> navController.navigate(Screen.ProductEdit.createRoute(id)) },
                onEliminado = {
                    navController.popBackStack(Screen.ProductList.route, inclusive = false)
                }
            )
        }

        composable(Screen.ProductCreate.route) {
            ProductFormScreen(
                productId = null,
                onBack = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ProductEdit.route,
            arguments = listOf(navArgument(Screen.ProductEdit.ARG_PRODUCT_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong(Screen.ProductEdit.ARG_PRODUCT_ID) ?: 0L
            ProductFormScreen(
                productId = productId,
                onBack = { navController.popBackStack() },
                onGuardado = { navController.popBackStack() }
            )
        }
    }
}
