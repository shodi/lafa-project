package com.example.demo.view

import com.example.demo.app.Styles

import tornadofx.*

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.control.Label
import javafx.scene.text.FontWeight
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color

import org.apache.fop.apps.FOPException
import org.apache.fop.apps.FOUserAgent
import org.apache.fop.apps.Fop
import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.MimeConstants

import org.exolab.castor.xml.Marshaller
import java.io.FileWriter

class MasterView: View("Projeto Lafa") {
    val model: ViewModel = ViewModel()
    val customer = model.bind { SimpleStringProperty("") }
    val password = model.bind { SimpleStringProperty("") }
    override val root = form {
        fieldset(title, labelPosition = Orientation.VERTICAL) {
            field("Cliente") {
                textfield(customer).required()
            }
            field("Password") {
                passwordfield(password).required()
            }
            button("Generate") {
                style {
                    fontWeight = FontWeight.EXTRA_BOLD 
                    textFill = Color.RED
                }
                enableWhen(model.valid)
                action {
                    generatePDF(customer.value, password.value)
                }
            }
        }
    }

    private fun generatePDF(customer: String, password: String) {
        println("GERANDO PDF usuário ${customer} senha ${password}")
        var pdfGenerator: PDFGenerator = PDFGenerator(customer)
        var writer: FileWriter = FileWriter(pdfGenerator.fileName)
        Marshaller.marshal(pdfGenerator, writer)
    }
}

class TopView: View() {
    override val root = Label("TOP VIEW")
}

class BottomView: View() {
    override val root = Label("BOTTOM VIEW")
}