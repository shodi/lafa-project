package com.example.demo.models

import tornadofx.*
import javafx.beans.property.*

data class Product(val productId: Int, val productName: String, val price: Double)

class ProductForm {
    val productNameProperty = SimpleStringProperty()
    val qtdProperty = SimpleIntegerProperty()
    val priceProperty = SimpleDoubleProperty()
    val totalPriceProperty = SimpleDoubleProperty(priceProperty.value * qtdProperty.value)
    val selectedItemProperty = SimpleObjectProperty<Product>()

}

class ProductModel(private var product: ProductForm): ViewModel() {
    val productName = bind { product.productNameProperty }
    val qtd = bind { product.qtdProperty }
    val price = bind { product.priceProperty }
    val totalPrice = bind { product.totalPriceProperty }
    val selectedItem = bind { product.selectedItemProperty }
}