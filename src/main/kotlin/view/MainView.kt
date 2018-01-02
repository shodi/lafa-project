package com.example.demo.view

import com.example.demo.app.Styles

import tornadofx.*

import com.example.demo.models.FormModel
import com.example.demo.models.Form
import com.example.demo.models.Order
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.text.FontWeight
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.collections.ObservableList
import javafx.collections.FXCollections

import java.time.LocalDate
import java.time.Period
import java.io.FileWriter

class MasterView: View("Projeto Lafa") {
    val model: FormModel = FormModel(Form())
    val orderList: ObservableList<Order> = FXCollections.observableArrayList(
            Order(1, "Shodi", 3.15, 9, "oie"),
            Order(2, "Pauzudo", 3.78, 2, "desc")
    )
//    orderList.add(Order(1, "Shodi", 3.15, 9, "oie"))
//    orderList.add(Order(2, "Pauzudo", 3.78, 2, "desc"))
    override val root = form {
        fieldset(title, labelPosition = Orientation.VERTICAL) {
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
            button("Adicionar produto") {
                setOnAction {
                    orderList.add(Order(1, "Click button", 5.89, 5, "botao"))
                }
            }
        }
        tableview(orderList) {
            column("Qtd.", Order::qtd)
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
                enableWhen(model.valid)
                setOnAction {
                    generatePDF(model.customerName.value, model.peopleRegistrationId.value, orderList)
                }
            }
        }
    }

    private fun generatePDF(customer: String, password: String, orderList: ObservableList<Order>) {
        println("GERANDO PDF usuário ${customer} senha ${password}")
        PDFGenerator(customer, orderList)
    }
}
