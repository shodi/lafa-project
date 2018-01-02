package com.example.demo.view

import com.example.demo.app.Styles

import tornadofx.*

import com.example.demo.models.FormModel
import com.example.demo.models.ProductModel
import com.example.demo.models.ProductForm
import com.example.demo.models.Product
import com.example.demo.models.Form
import com.example.demo.models.Order
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.text.FontWeight
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.layout.GridPane
import javafx.collections.ObservableList
import javafx.collections.FXCollections
import javafx.beans.value.ChangeListener

import javafx.collections.ListChangeListener
import rx.javafx.sources.ListChange

import java.time.LocalDate
import java.time.Period
import java.io.FileWriter
import rx.javafx.sources.Change



class MasterView: View() {
    val model: FormModel = FormModel(Form())
    val productModel: ProductModel = ProductModel(ProductForm())
    val orderList: ObservableList<Order> = FXCollections.observableArrayList(
            Order(1, "Shodi", 3.15, 9, "oie"),
            Order(2, "fawfowia", 3.78, 2, "desc")
    )

    override val root = GridPane()
    val itens: ObservableList<Product> = FXCollections.observableArrayList(
            Product(1, "Radio", 3.8),
            Product(2, "Televisão", 9.56),
            Product(3, "Vassoura", 15.65)
    )

    init {
        with(root) {
            form {
                fieldset("Informações Cliente", labelPosition = Orientation.VERTICAL) {
                    field("Cliente") {
                        textfield(model.customerName){
                            promptText = "Nome cliente"
                        }.required()
                    }
                    field("CPF / CNPJ") {
                        textfield(model.peopleRegistrationId){
                            promptText = "CPF ou CNPJ do cliente"
                        }.required()
                    }
                    field("Data") {
                        datepicker()
                    }
                }
                fieldset("Adicionar Produto", labelPosition = Orientation.VERTICAL) {
                    combobox<Product>(productModel.selectedItem, itens) {
                        cellFormat {
                            text = it.productName
                        }
                        setOnAction {
                            val selectedItem: Product = productModel.selectedItem.value
                            productModel.price.setValue(selectedItem.price)
                        }
                    }.required()
                    field("Qtd.") {
                        textfield(productModel.qtd) {
                            textProperty().addListener { _, _, new ->
                                var value: Double = .0
                                if (new != null && (new as String).isEmpty().not()) {
                                    var newValue: Double = new.toDouble()
                                    var price: Double = productModel.price.value.toDouble()
                                    value = price * newValue
                                }
                                productModel.totalPrice.setValue(value)
                            }
                        }.required(message="Este campo apenas assume valores maiores do que 0")
                    }
                    field("Vl. Unitário") {
                        textfield(productModel.price) {
                            setEditable(false)
                            setMouseTransparent(true)
                            setFocusTraversable(false)
                        }

                    }
                    field("Vl. Total") {
                        textfield(productModel.totalPrice) {
                            setEditable(false)
                            setMouseTransparent(true)
                            setFocusTraversable(false)
                        }
                    }
                    button("Adicionar produto") {
                        enableWhen(productModel.valid)
                        setOnAction {
                            orderList.add(Order(1, "shodi", 5.5, 2, "oi"))
                        }
                    }
                }
                tableview(orderList) {
                    column("Qtd.", Order::qtd).makeEditable()
                            .setOnEditCommit {
                                event ->
                                val changedOrder: Order = event.getRowValue()
                                changedOrder.qtd = event.getNewValue()
                                this.refresh()
                            }
                    column("Cód.", Order::productId)
                    column("Discriminação", Order::desc)
                    column("Vl. Unitário", Order::productPrice)
                    column("Vl. Total", Order::totalPrice)
                }
                fieldset() {
                    button("Gerar PDF") {
                        style {
                            fontWeight = FontWeight.EXTRA_BOLD
                        }
                        setOnAction {
//                            generatePDF(model.customerName.value, model.peopleRegistrationId.value, orderList)
                            orderList.forEach {
                                println(it.qtd)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun generatePDF(customer: String, password: String, orderList: ObservableList<Order>) {
        println("GERANDO PDF usuário ${customer} senha ${password}")
        PDFGenerator(customer, orderList)
    }
}
