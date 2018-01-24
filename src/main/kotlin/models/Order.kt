package com.example.demo.models

import tornadofx.*
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAccessType

@XmlRootElement(name="order")
class Order(@XmlAttribute(name="product-id") val productId: Int,
            @XmlAttribute(name="product-name") val productName: String,
            @XmlAttribute(name="product-price") val productPrice: Double,
            @XmlAttribute var qtd: Int,
            @XmlAttribute val desc: String?) {
    var discount: Double = .0
    val totalPrice: Double
        get() {
            val totalPrice = productPrice * qtd
            return totalPrice - (totalPrice * discount / 100)
        }
}

class OrderForm {
    val productIdProperty = SimpleStringProperty()
    val productNameProperty = SimpleStringProperty()
    val productPriceProperty = SimpleStringProperty()
    val qtdProperty = SimpleStringProperty()
    val descProperty = SimpleStringProperty()
    val paymentMethodProperty = SimpleStringProperty()
}

class OrderModel(private var order: OrderForm): ViewModel() {
    val productId = bind { order.productIdProperty }
    val productName = bind { order.productNameProperty }
    val productPrice = bind { order.productPriceProperty }
    val qtd = bind { order.qtdProperty }
    val desc = bind { order.descProperty }
    val paymentMethod = bind { order.paymentMethodProperty }
}


data class OrderList(val orderList: ObservableList<Order>)