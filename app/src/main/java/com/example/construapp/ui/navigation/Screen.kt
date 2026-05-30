package com.example.construapp.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    data object ProductList : Screen("products")

    data object ProductDetail : Screen("products/{productId}") {
        const val ARG_PRODUCT_ID = "productId"
        fun createRoute(productId: Long): String = "products/$productId"
    }

    data object ProductCreate : Screen("products/new")

    data object ProductEdit : Screen("products/{productId}/edit") {
        const val ARG_PRODUCT_ID = "productId"
        fun createRoute(productId: Long): String = "products/$productId/edit"
    }
}
