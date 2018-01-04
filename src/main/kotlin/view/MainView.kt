package com.example.demo.view

import com.example.demo.app.Styles

import tornadofx.*

import com.example.demo.models.FormModel
import com.example.demo.models.ProductModel
import com.example.demo.models.ProductForm
import com.example.demo.models.Product
import com.example.demo.models.Form
import com.example.demo.models.Order
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.text.FontWeight
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.layout.GridPane
import javafx.collections.ObservableList
import javafx.collections.FXCollections
import javafx.beans.value.ChangeListener
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert

import javafx.collections.ListChangeListener
import rx.javafx.sources.ListChange

import java.time.LocalDate
import java.time.Period
import java.io.FileWriter
import rx.javafx.sources.Change

class MasterView: View() {

    private val model: FormModel = FormModel(Form())
    private val productModel: ProductModel = ProductModel(ProductForm())
    private val orderList: ObservableList<Order> = FXCollections.observableArrayList()
    private val itens: ObservableList<Product> = FXCollections.observableArrayList(
            Product(1, "Radio", 3.8),
            Product(2, "Televisão", 9.56),
            Product(3, "Vassoura", 15.65)
    )

    override val root = GridPane()

    init {
        with(root) {
            setPrefSize(800.0, 500.0)
            form {
                fieldset("Informações Cliente", labelPosition = Orientation.VERTICAL) {
                    gridpane {
                        row {
                            field("Cliente") {
                                textfield(model.customerName){
                                    promptText = "Nome cliente"
                                }.required()
                                gridpaneConstraints {
                                    marginRight = 15.0
                                }
                            }
                            field("CPF / CNPJ", Orientation.HORIZONTAL) {
                                hbox {
                                    textfield(model.peopleRegistrationId){
                                        promptText = "CPF ou CNPJ do cliente"
                                    }.required()
                                    button("")
                                }
                                gridpaneConstraints {
                                    marginRight = 15.0
                                }
                            }
                            field("Data") {
                                datepicker()
                            }
                        }
                    }
                }
                separator()
                fieldset("Adicionar Produto", labelPosition = Orientation.VERTICAL) {
                    gridpane {
                        row {
                            field(null, Orientation.HORIZONTAL) {
                                hbox {
                                    vbox {
                                        label("Item")
                                        combobox<Product>(productModel.selectedItem, itens) {
                                            promptText = "Selecione"
                                            cellFormat {
                                                text = it.productName
                                            }
                                            setOnAction {
                                                val selectedItem: Product = productModel.selectedItem.value
                                                productModel.price.setValue(selectedItem.price)
                                                val price: Double = productModel.price.value.toDouble()
                                                val qtd: Double = productModel.qtd.value?.toDouble() ?: .0
                                                productModel.totalPrice.setValue(price * qtd)
                                            }
                                        }.required()
                                        hboxConstraints {
                                            marginRight = 15.0
                                        }
                                    }
                                    vbox {
                                        label("Qtd.")
                                        textfield(productModel.qtd) {
                                            setOnKeyPressed {
                                                event ->
                                                println("CHAMOU SET ON ACTION  $event")
                                            }
                                            textProperty().addListener { _, _, new ->
                                                var value: Double = .0
                                                if (new != null && (new as String).isEmpty().not()) {
                                                    val newValue: Double = new.toDouble()
                                                    val price: Double = productModel.price.value.toDouble()
                                                    value = price * newValue
                                                }
                                                productModel.totalPrice.setValue(value)
                                            }
                                        }.required(message="Este campo apenas assume valores maiores do que 0")
                                    }
                                    hboxConstraints {
                                        marginRight = 15.0
                                    }
                                }
                            }
                            field("Vl. Unitário") {
                                textfield(productModel.price) {
                                    setEditable(false)
                                    setMouseTransparent(true)
                                    setFocusTraversable(false)
                                }
                                gridpaneConstraints {
                                    marginRight = 15.0
                                }
                            }
                            field("Vl. Total", Orientation.HORIZONTAL) {
                                textfield(productModel.totalPrice) {
                                    setEditable(false)
                                    setMouseTransparent(true)
                                    setFocusTraversable(false)
                                }
                                button("+") {
                                    enableWhen(productModel.valid)
                                    setOnAction actionLabel@{
                                        val qtd: Int = productModel.qtd.value.toInt()
                                        val selectedItem: Product = productModel.selectedItem.value
                                        val alert = Alert(AlertType.INFORMATION)
                                        alert.setTitle("Aviso")
                                        alert.setHeaderText(null)
                                        if(qtd >= 0) {
                                            alert.setContentText(
                                                    """
                                                        >Não é possivel solicitar a locação
                                                        >de um produto cuja quantidade seja menor ou
                                                        >igual a 0.
                                                    """.trimMargin(">")
                                            )
                                            alert.showAndWait()
                                            return@actionLabel
                                        }
                                        orderList.forEach {
                                            if(it.productId == selectedItem.productId) {
                                                alert.setContentText(
                                                        """
                                                            >Você já possui o produto '${it.productName}' na
                                                            >lista, caso queira ajustar a quantidade dê um duplo
                                                            >click em cima da célula da tabela.
                                                        """.trimMargin(">")
                                                )
                                                alert.showAndWait()
                                                return@actionLabel
                                            }
                                        }
                                        orderList.add(Order(selectedItem.productId, selectedItem.productName, selectedItem.price, qtd, null))
                                        productModel.qtd.setValue(0)
                                    }
                                }
                            }
                        }
                    }
                }
                separator()
                tableview(orderList) {
                    useMaxWidth = true
                    column("Qtd.", Order::qtd).makeEditable()
                            .setOnEditCommit {
                                event ->
                                val changedOrder: Order = event.getRowValue()
                                changedOrder.qtd = event.getNewValue()
                                this.refresh()
                            }
                    column("Cód.", Order::productId)
                    column("Produto", Order::productName)
                    column("Discriminação", Order::desc)
                    column("Vl. Unitário", Order::productPrice)
                    column("Disconto (%)", Order::discount).makeEditable()
                            .setOnEditCommit {
                                event ->
                                val changedOrder: Order = event.getRowValue()
                                changedOrder.discount = event.getNewValue()
                                this.refresh()
                            }
                    column("Vl. Total", Order::totalPrice)
                }
                fieldset {
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
